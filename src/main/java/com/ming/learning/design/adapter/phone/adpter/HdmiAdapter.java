package com.ming.learning.design.adapter.phone.adpter;

/**
 * @Author ming
 * @time 2020/9/12 11:41
 */
public class HdmiAdapter extends Adapter {

    @Override
    public void typec2hdmi() {
        System.out.println("接收到Type-c口信息，信息转换成HDMI接口中...");
        System.out.println("信息已转换成VGA接口，显示屏可以对接。");
    }
}
