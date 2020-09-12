package com.ming.learning.design.strategy.cart;

/**
 * 高级会员  -- 具体策略类（ConcreteStrategy）
 * @Author ming
 * @time 2020/9/7 15:59
 */
public class AdvancedMemberStrategy implements MemberStrategy {
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("高级会员 8 折优惠");
        return bookPrice * 0.8;
    }
}
