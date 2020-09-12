package com.ming.learning.design.strategy.cart;

/**
 * 初级会员折扣类 --- 具体策略类（ConcreteStrategy）
 * @Author ming
 * @time 2020/9/7 15:57
 */
public class PrimaryMemberStrategy  implements MemberStrategy{
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("对初级会员没有折扣");
        return bookPrice;
    }
}
