package com.ming.learning.design.strategy.cart;

/**
 * 中级会员
 * @Author ming
 * @time 2020/9/7 15:58
 */
public class IntermediateMemberStrategy implements MemberStrategy{
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("中级会员 9 折优惠");
        return bookPrice * 0.9;
    }
}
