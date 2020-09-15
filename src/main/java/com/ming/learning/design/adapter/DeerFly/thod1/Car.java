package com.ming.learning.design.adapter.DeerFly.thod1;

import com.ming.learning.design.adapter.DeerFly.IDeerFlyTarget;
import org.junit.Test;

/**
 * 车
 * @Author ming
 * @time 2020/9/12 16:02
 */
public class Car {
    @Test
    public void main(){
        IDeerFlyTarget flyDeer = new ClassAdapter();
        flyDeer.run();
        flyDeer.fly();
    }
}
