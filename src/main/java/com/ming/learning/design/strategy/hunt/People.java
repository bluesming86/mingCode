package com.ming.learning.design.strategy.hunt;

import org.junit.Test;

/**
 * 客户端
 * 人
 * @Author ming
 * @time 2020/9/12 15:29
 */
public class People {

    @Test
    public void toHunt(){

        HuntContext hunt = new HuntContext(new KnifeHuntStrategy());
        hunt.huting();

        HuntContext hunt2 = new HuntContext(new BowHuntStrategy());
        hunt2.huting();

        HuntContext hunt3 = new HuntContext(new AxHuntStrategy());
        hunt3.huting();
    }
}
