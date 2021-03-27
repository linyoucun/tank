package com.lyc.tank;

import com.lyc.tank.strategy.DefaultFireStrategy;
import com.lyc.tank.strategy.FireStrategy;

import java.awt.*;
import java.util.Random;

public class Tank extends GameObject {
    public int orgX, orgY;
    public int x, y;
    public Dir dir = Dir.DOWN;
    private static final int SPEED = PropertyMgr.getInt("tankSpeed");
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();

    public Rectangle rect = new Rectangle();

    private Random random = new Random();

    private boolean isMoving = true;
    private boolean living = true;
    public Group group = Group.BAD;
    public GameModel gm;

    public void stop(){
        isMoving = false;
    }

    public int getOrgX() {
        return orgX;
    }

    public void setOrgX(int orgX) {
        this.orgX = orgX;
    }

    public int getOrgY() {
        return orgY;
    }

    public void setOrgY(int orgY) {
        this.orgY = orgY;
    }

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

    public Tank(int x, int y, Dir dir, Group group, GameModel gm) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.gm = gm;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void paint(Graphics g) {
        if (!living) {
            gm.remove(this);
            return;
        }

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            default:
                break;
        }

        move();
    }

    private void move() {
        this.orgX = this.x;
        this.orgY = this.y;

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
                this.fire(DefaultFireStrategy.getInstance());
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

    public void backOrg(){
        this.x = orgX;
        this.y = orgY;
    }

    private void boundsCheck() {
        if (this.x < 0) {
            x = 0;
        }
        if (this.y < 30) {
            y = 30;
        }
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) {
            x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        }
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) {
            y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
        }
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire(FireStrategy fireStrategy) {
        if(this.living){
            fireStrategy.fire(this);
        }
    }

    public void die() {
        this.living = false;
    }
}
