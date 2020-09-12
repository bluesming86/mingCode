package com.ming.learning.design.strategy.gameV2;

/**
 * @Author ming
 * @time 2020/9/7 19:08
 */
public abstract class AbstractGameDetailController {

    /**
     *  抽象方法，获取前端页面路径
     *  需要在子类中取实现
     */
    public abstract String getVewPath();

    /**
     * 获取对局详情
     * @return
     */
    public String gameVsDetail(){
        return getVewPath();
    }
}
