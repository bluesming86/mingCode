package com.ming.learning.spring;

/**
 * 静态代理
 * @Author ming
 * @time 2020/8/18 15:47
 */
public class StaticProxy {
    public static void main(String[] args) {
        Singer singer = new Agent(new Star());
        singer.sing();
    }
}

/**
 * 最顶层接口 歌手
 */
interface Singer{
    void sing();
}

/**
 * 歌星 ，真实实现
 */
class Star implements Singer{
    @Override
    public void sing() {
        System.out.println("Star Singing~~~");
    }
}

/**
 * 代理实现， 代理了歌星，
 *      在歌星唱歌之前收钱，然后再唱歌
 */
class Agent implements Singer{

    Star star;

    public Agent(Star star) {
        this.star = star;
    }

    @Override
    public void sing() {
        System.out.println("再歌手唱歌之前收钱");
        star.sing();
    }
}
