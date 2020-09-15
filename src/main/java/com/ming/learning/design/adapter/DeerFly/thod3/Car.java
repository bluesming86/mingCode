package com.ming.learning.design.adapter.DeerFly.thod3;

import org.junit.Test;

/**
 * @Author ming
 * @time 2020/9/12 16:54
 */
public class Car {

    @Test
    public void main(){

        FlyAdapter flyAdapter = new FlyAdapter();
        flyAdapter.run();
        flyAdapter.fly();
    }
}
