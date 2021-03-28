package com.lyc.tank;

import com.lyc.tank.cor.ColliderChain;
import com.lyc.tank.strategy.FourDirFireStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    private Tank myTank;
    private final List<GameObject> objects = new ArrayList<>();
    private final ColliderChain chain = new ColliderChain();

    public static GameModel getInstance() {
        return INSTANCE;
    }

    private GameModel() {

    }

    private void init() {
        // 初始化主战坦克
        myTank = new Tank(600, 600, Dir.DOWN, Group.GOOD);

        // 初始化地方坦克
        int initTankCount = PropertyMgr.getInt("initTankCount");
        for (int i = 0; i < initTankCount; i++) {
            objects.add(new Tank(i * 80, 0, Dir.DOWN, Group.BAD));
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
        if (go == myTank) {
            myTank = null;
            return;
        }
        this.objects.remove(go);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
//        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
//        g.drawString("敌人的数量：" + tanks.size(), 10, 80);
//        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        // 如果myTank已经被杀死后，打印出游戏结束
        if (myTank == null) {
            Color c2 = g.getColor();
            Font f = g.getFont();

            g.setColor(Color.RED);
            g.setFont(new Font("feifeiFont", 0, 100));
            g.drawString("GAME OVER", 260, 400);

            g.setColor(c2);
            g.setFont(f);
            return;
        }

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

    public void mainTankFire(){
        if(myTank == null){
            return;
        }
        myTank.fire(FourDirFireStrategy.getInstance());
    }

    public Tank getMainTank() {
        return myTank;
    }
}
