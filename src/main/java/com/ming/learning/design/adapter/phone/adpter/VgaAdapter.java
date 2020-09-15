package com.ming.learning.design.adapter.phone.adpter;

/**
 *
 * 实现一个 Vga适配器，
 *  适配器实现方式有三种，这个是用 第三种方式实现
 * @Author ming
 * @time 2020/9/12 11:38
 */
public class VgaAdapter extends Adapter {

    public void typec() {
        System.out.println("信息从Typec口的手机输出。");
    }

    @Override
    public void typec2vga() {
        System.out.println("接收到Type-c口信息，信息转换成VGA接口中...");
        System.out.println("信息已转换成VGA接口，显示屏可以对接。");
    }
}
