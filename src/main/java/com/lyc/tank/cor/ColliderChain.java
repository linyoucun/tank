package com.lyc.tank.cor;

import com.lyc.tank.GameObject;
import com.lyc.tank.PropertyMgr;

import java.util.LinkedList;
import java.util.List;

/**
 * ColliderChain包含一堆Collier的实现类组成的集合
 * ColliderChain自己又实现了Collier，那么可以把多个chain拼接成一起，如：chain1.add(chain2)
 */
public class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        String colliderClassNames = PropertyMgr.getString("colliders");
        if (colliderClassNames == null && colliderClassNames.length() == 0) {
            return;
        }
        String[] classNameArray = colliderClassNames.split(",");
        for (String className : classNameArray) {
            try {
                colliders.add((Collider)Class.forName(className).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Collider c) {
        colliders.add(c);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            boolean result = colliders.get(i).collide(o1, o2);
            if (result == false) {
                return false;
            }
        }

        return true;
    }
}
