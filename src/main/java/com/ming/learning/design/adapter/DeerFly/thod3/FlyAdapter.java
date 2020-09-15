package com.ming.learning.design.adapter.DeerFly.thod3;

import com.ming.learning.design.adapter.DeerFly.thod3.AbstractDeerAdapter;

/**
 * @Author ming
 * @time 2020/9/12 16:48
 */
public class FlyAdapter extends AbstractDeerAdapter {
    @Override
    public void run() {
        System.out.println("小鹿 跑跑跑");
    }

    @Override
    public void fly() {
        System.out.println("小鹿 飞飞飞");
    }
}
