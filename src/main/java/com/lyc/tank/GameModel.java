package com.lyc.tank;

import com.lyc.tank.cor.BulletTankCollider;
import com.lyc.tank.cor.Collider;
import com.lyc.tank.cor.ColliderChain;
import com.lyc.tank.cor.TankTankCollider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    Tank myTank = new Tank(600, 600, Dir.DOWN, Group.GOOD, this);
    private List<GameObject> objects = new ArrayList<>();

    ColliderChain chain = new ColliderChain();

    public GameModel() {
        int initTankCount = PropertyMgr.getInt("initTankCount");

        // 初始化地方坦克
        for (int i = 0; i < initTankCount; i++) {
            objects.add(new Tank(i * 80, 0, Dir.DOWN, Group.BAD, this));
        }

        // initialize wall
        add(new Wall(100, 100, 200, 400));
        add(new Wall(450, 100, 200, 400));
        add(new Wall(800, 100, 200, 400));
    }

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public void remove(GameObject go) {
        this.objects.remove(go);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
//        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
//        g.drawString("敌人的数量：" + tanks.size(), 10, 80);
//        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }

        // 互相碰撞
        for (int i = 0; i < objects.size(); i++) {
            GameObject o1 = objects.get(i);
            chain.collide(o1, myTank);
        }

        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                chain.collide(o1, o2);
            }
        }

//        for (int i = 0; i < bullets.size(); i++) {
//            for (int j = 0; j < tanks.size(); j++) {
//                bullets.get(i).collideWith(tanks.get(j));
//            }
//        }
    }

    public Tank getMainTank() {
        return myTank;
    }
}
