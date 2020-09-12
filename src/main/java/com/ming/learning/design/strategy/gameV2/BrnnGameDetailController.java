package com.ming.learning.design.strategy.gameV2;

/**
 * @Author ming
 * @time 2020/9/7 19:11
 */
public class BrnnGameDetailController extends AbstractGameDetailController {
    @Override
    public String getVewPath() {
        return "/brnnGameDetail.jsp";
    }

    @Override
    public String gameVsDetail() {

        System.out.println("brnn 处理过程==");

        return super.gameVsDetail();
    }
}
