package com.ming.learning.design.strategy.hunt;

/**
 * 具体策略类
 *      使用弓箭打猎
 * @Author ming
 * @time 2020/9/12 15:23
 */
public class BowHuntStrategy implements IHuntStrategy{

    @Override
    public void hunt() {
        System.out.println("使用弓箭打猎！！");
    }
}
