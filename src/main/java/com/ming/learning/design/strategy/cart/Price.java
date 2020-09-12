package com.ming.learning.design.strategy.cart;

/**
 * 价格类 --- 环境类（Context）
 * @Author ming
 * @time 2020/9/7 16:04
 */
public class Price {

    //持有一个具体的策略对象
    private MemberStrategy strategy;

    /**
     * 构造函数，传入一个具体的策略对象
     * @param strategy
     */
    public Price(MemberStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 计算图书的价格
     * @param bookPrice 图书的价格
     * @return 计算出打折后的价格
     */
    public  double quote(double bookPrice){
        return this.strategy.calcPrice(bookPrice);
    }
}
