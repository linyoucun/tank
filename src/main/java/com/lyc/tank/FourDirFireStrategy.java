package com.lyc.tank;

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
            new Bullet(bX, bY, dir, t.group, t.gm);
        }

        if (t.group == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
