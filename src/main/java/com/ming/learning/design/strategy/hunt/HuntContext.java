package com.ming.learning.design.strategy.hunt;

/**
 * 定义环境类
 *  打猎
 * @Author ming
 * @time 2020/9/12 15:27
 */
public class HuntContext {
    private IHuntStrategy iHuntStrategy;// 打猎要带的工具

    public HuntContext(IHuntStrategy iHuntStrategy) {
        this.iHuntStrategy = iHuntStrategy;
    }

    public void huting(){
        this.iHuntStrategy.hunt();
    }
}
