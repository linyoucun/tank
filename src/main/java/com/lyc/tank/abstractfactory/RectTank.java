package com.lyc.tank.abstractfactory;

import com.lyc.tank.Audio;
import com.lyc.tank.Bullet;
import com.lyc.tank.Dir;
import com.lyc.tank.Group;
import com.lyc.tank.PropertyMgr;
import com.lyc.tank.ResourceMgr;
import com.lyc.tank.TankFrame;

import java.awt.*;
import java.util.Random;

public class RectTank extends BaseTank {
    int x, y;
    Dir dir = Dir.DOWN;
    private static final int SPEED = PropertyMgr.getInt("tankSpeed");

    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private Random random = new Random();

    private boolean isMoving = true;
    TankFrame tf = null;
    private boolean living = true;
    Group group = Group.BAD;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public RectTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
            return;
        }

        Color c = g.getColor();

        if (this.group == Group.GOOD) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLUE);
        }

        g.fillRect(x, y, 66, 66);
        g.setColor(c);

        move();
    }

    private void move() {
        if (!isMoving) {
            return;
        }
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }

        if (this.group == Group.BAD) {
            if (random.nextInt(100) > 95) {
                this.fire();
            }
            if (random.nextInt(100) > 95) {
                randomDir();
            }
        }

        boundsCheck();

        // update rect
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        if (this.x < 0) {
            x = 0;
        }
        if (this.y < 30) {
            y = 30;
        }
        if (this.x > TankFrame.GAME_WIDTH - RectTank.WIDTH - 2) {
            x = TankFrame.GAME_WIDTH - RectTank.WIDTH - 2;
        }
        if (this.y > TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 2) {
            y = TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 2;
        }
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        int bX = this.x + RectTank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.y + RectTank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        if (this.group == Group.GOOD) {
            for (Dir dir : Dir.values()) {
                this.tf.gf.createBullet(bX, bY, dir, this.group, this.tf);
            }
        } else {
            this.tf.gf.createBullet(bX, bY, dir, this.group, this.tf);
        }

        if (this.group == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }

    public void die() {
        this.living = false;
    }
}
