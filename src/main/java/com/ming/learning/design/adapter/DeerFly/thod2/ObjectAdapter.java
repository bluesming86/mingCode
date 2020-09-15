package com.ming.learning.design.adapter.DeerFly.thod2;

import com.ming.learning.design.adapter.DeerFly.Deer;
import com.ming.learning.design.adapter.DeerFly.IDeerFlyTarget;

/**
 * @Author ming
 * @time 2020/9/12 16:57
 */
public class ObjectAdapter implements IDeerFlyTarget {

    private Deer deer;

    public ObjectAdapter(Deer deer) {
        this.deer = deer;
    }

    @Override
    public void run() {
        deer.run();
    }

    @Override
    public void fly() {
        System.out.println("小鹿可以飞了");
    }
}
