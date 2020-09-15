package com.ming.learning.design.adapter.DeerFly.thod1;

import com.ming.learning.design.adapter.DeerFly.Deer;
import com.ming.learning.design.adapter.DeerFly.IDeerFlyTarget;

/**
 * 鹿飞翔的 适配器
 *      继承 鹿， 并实现目标  飞翔的功能
 * @Author ming
 * @time 2020/9/12 15:59
 */
public class ClassAdapter extends Deer implements IDeerFlyTarget {
    @Override
    public void fly() {
        System.out.println("哇咔咔，我可以飞了！！");
    }
}
