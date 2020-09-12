package com.ming.learning.design.strategy.gameV2;

/**
 *  游戏对局详情 适配器
 * @Author ming
 * @time 2020/9/7 19:14
 */
public class GameDetailAdapter {

    private AbstractGameDetailController gameDetailController;

    public GameDetailAdapter(AbstractGameDetailController gameDetailController) {
        this.gameDetailController = gameDetailController;
    }

    public String getGameVsDetail(){
        return this.gameDetailController.gameVsDetail();
    }
}
