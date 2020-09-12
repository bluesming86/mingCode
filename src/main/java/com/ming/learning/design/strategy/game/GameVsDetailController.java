package com.ming.learning.design.strategy.game;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author ming
 * @time 2020/9/7 16:28
 */
public class GameVsDetailController {

    @Test
    public void test(){

        Integer gameId = 1;

        getGameDetailByGameId(gameId);
    }

    /**
     * 根据游戏Id 获取游戏对局详情对局详情
     * @param gameId
     * @return
     */
    private String getGameDetailByGameId(Integer gameId) {
        GameDetailEnum gameDetailEnum = GameDetailEnum.getGameDetailEnumByGameId(gameId);
        if (gameDetailEnum == null){
            System.out.println("没找到 对应的游戏");
        }
        try {
            System.out.println(gameDetailEnum.getClazz());
            Class clazz = Class.forName(gameDetailEnum.getClazz());

            System.out.println(gameDetailEnum.getMethod());
            Method method = clazz.getMethod(gameDetailEnum.getMethod());

            return (String) method.invoke(clazz.newInstance());
        } catch (ClassNotFoundException e) {
            System.out.println("没找到对应的 类");
        } catch (NoSuchMethodException e) {
            System.out.println("没找到对应的方法");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
