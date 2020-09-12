package com.ming.learning.design.strategy.cart;

/**
 * 抽象折扣 接口
 */
public interface MemberStrategy {

    /**
     * 计算图书的价格
     * @param bookPrice 图书原始价格
     * @return 折扣后的价格
     */
    public double calcPrice(double bookPrice);
}
