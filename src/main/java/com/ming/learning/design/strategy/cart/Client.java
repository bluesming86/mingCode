package com.ming.learning.design.strategy.cart;

import org.junit.Test;

/**
 * 客户端测试类
 * @Author ming
 * @time 2020/9/7 16:01
 */
public class Client {


    @Test
    public void test(){

        double bookPrice = 100;

        Price primaryMember = new Price(new PrimaryMemberStrategy());
        Price interMediateMember = new Price(new IntermediateMemberStrategy());
        Price advancedMember = new Price(new AdvancedMemberStrategy());

        double primaryPrice = primaryMember.quote(bookPrice);
        System.out.println("初级会员价格： " + primaryPrice);

        double interMediatePrice = interMediateMember.quote(bookPrice);
        System.out.println("中级会员价格： " + interMediatePrice);

        double advancedPrice = advancedMember.quote(bookPrice);
        System.out.println("高级会员价格： " + advancedPrice);
    }
}
