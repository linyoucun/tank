package com.lyc.tank.cor;

import com.lyc.tank.GameObject;

public interface Collider {
    /**
     *
     * @param o1
     * @param o2
     * @return false-停止、true-继续执行
     */
    boolean collide(GameObject o1, GameObject o2);
}
