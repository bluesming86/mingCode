package com.ming.learning.design.strategy.gameV2;

import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ming
 * @time 2020/9/7 19:17
 */
public class GameVsDetailController {

    @Test
    public void main(){
        System.out.println(test(1));
        System.out.println(test(2));
        System.out.println(test(3));
    }

    public String test(Integer gameId){
        //适配器集合
        Map<Integer,GameDetailAdapter> adapterMap = new HashMap<>();
        adapterMap.put(1, new GameDetailAdapter(new DiceGameDetailController()));
        adapterMap.put(2, new GameDetailAdapter(new BrnnGameDetailController()));
        adapterMap.put(3, new GameDetailAdapter(new HhdzGameDetailController()));

        //根据游戏id 从适配器集合中获取对应游戏的适配器

        return adapterMap.get(gameId).getGameVsDetail();
    }
}
