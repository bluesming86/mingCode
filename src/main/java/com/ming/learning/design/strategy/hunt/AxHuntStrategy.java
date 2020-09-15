package com.ming.learning.design.strategy.hunt;

/**
 * 具体实现策略类
 *  使用斧头打猎
 * @Author ming
 * @time 2020/9/12 15:26
 */
public class AxHuntStrategy implements IHuntStrategy{
    @Override
    public void hunt() {
        System.out.println("使用斧头去打猎！！！");
    }
}
