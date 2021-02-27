package com.lyc.tank;

import com.lyc.tank.abstractfactory.GameFactory;
import com.lyc.tank.abstractfactory.RectFactory;

public class T {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        int initTankCount = PropertyMgr.getInt("initTankCount");

        // 初始化地方坦克
        for (int i = 0; i < initTankCount; i++) {
            tf.tanks.add(tf.gf.createTank(50 + i * 80, 200, Dir.DOWN, Group.BAD, tf));
        }

//        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while (true) {
            Thread.sleep(50L);
            tf.repaint();
        }
    }
}
