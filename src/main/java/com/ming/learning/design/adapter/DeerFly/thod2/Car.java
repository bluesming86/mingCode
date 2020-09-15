package com.ming.learning.design.adapter.DeerFly.thod2;

import com.ming.learning.design.adapter.DeerFly.Deer;
import org.junit.Test;

/**
 * @Author ming
 * @time 2020/9/12 16:58
 */
public class Car {

    @Test
    public void main(){

        ObjectAdapter adapter = new ObjectAdapter(new Deer());
        adapter.run();
        adapter.fly();
    }
}
