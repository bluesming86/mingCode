package com.ming.learning.design.adapter.phone.adpter;

import com.ming.learning.design.adapter.phone.Phone;
import com.ming.learning.design.adapter.phone.Vga;

/**
 *  实现一个Type-c转VGA适配器，
 *      适配器实现方式有三种，这是第一种实现方式。
 *      　原理：通过继承特性来实现适配器功能。
 * @Author ming
 * @time 2020/9/12 11:31
 */
public class Typec2Vga1 extends Phone implements Vga {

    @Override
    public void vgaInterface() {
        typecPhone();
        System.out.println("接收到Type-c口信息，信息转换成VGA接口中。。。");
        System.out.println("信息已转换成Vga接口，显示屏可以对接");
    }
}
