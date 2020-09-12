package com.ming.learning.design.strategy.gameV2;

/**
 * @Author ming
 * @time 2020/9/7 19:11
 */
public class DiceGameDetailController extends AbstractGameDetailController {
    @Override
    public String getVewPath() {
        return "/diceGameDetail.jsp";
    }

    @Override
    public String gameVsDetail() {

        System.out.println("dice 处理过程==");

        return super.gameVsDetail();
    }
}
