package com.ming.learning.design.adapter.phone.adpter;

/**
 *
 *      定义一个接口 ----- （ 接口的适配器模式）
 * @Author ming
 * @time 2020/9/12 11:32
 */
public interface Target {
    void typec();//typec接口
    void typec2vga(); //typec转vga
    void typec2hdmi();//typec 转hdmi
}
