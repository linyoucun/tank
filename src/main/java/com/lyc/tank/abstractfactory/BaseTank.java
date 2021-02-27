package com.lyc.tank.abstractfactory;

import com.lyc.tank.Group;

import java.awt.*;

public abstract class BaseTank {
    public Rectangle rect = new Rectangle();

    public Group group = Group.BAD;

    public abstract void paint(Graphics g);

    public Group getGroup(){
        return this.group;
    }

    public abstract void die();

    public abstract int getX();

    public abstract int getY();
}
