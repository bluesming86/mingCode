package com.ming.learning.design.strategy.gameV2;

/**
 * @Author ming
 * @time 2020/9/7 19:11
 */
public class HhdzGameDetailController extends AbstractGameDetailController {
    @Override
    public String getVewPath() {
        return "/hhdzGameDetail.jsp";
    }

    @Override
    public String gameVsDetail() {

        System.out.println("hhdz 处理过程==");

        return super.gameVsDetail();
    }
}
