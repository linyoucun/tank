package com.lyc.tank.abstractfactory;

import com.lyc.tank.Bullet;
import com.lyc.tank.Dir;
import com.lyc.tank.Explode;
import com.lyc.tank.Group;
import com.lyc.tank.Tank;
import com.lyc.tank.TankFrame;

public class DefaultFactory extends GameFactory {
    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Tank(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new Explode(x, y, tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Bullet(x, y, dir, group, tf);
    }
}
