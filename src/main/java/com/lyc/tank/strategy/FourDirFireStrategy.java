package com.lyc.tank.strategy;

import com.lyc.tank.Audio;
import com.lyc.tank.Bullet;
import com.lyc.tank.Dir;
import com.lyc.tank.Group;
import com.lyc.tank.Tank;

public class FourDirFireStrategy implements FireStrategy {

    private final static FourDirFireStrategy INSTANCE = new FourDirFireStrategy();

    private FourDirFireStrategy() {
    }

    public static FourDirFireStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public void fire(Tank t) {
        int bX = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        for (Dir dir : Dir.values()) {
            new Bullet(bX, bY, dir, t.group);
        }

        if (t.group == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
