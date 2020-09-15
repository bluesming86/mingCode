package com.ming.learning.design.strategy.hunt;

/**
 * 具体实现策略类
 *     使用小刀 去打猎
 * @Author ming
 * @time 2020/9/12 15:25
 */
public class KnifeHuntStrategy implements IHuntStrategy {
    @Override
    public void hunt() {
        System.out.println("使用小刀去打猎");
    }
}
