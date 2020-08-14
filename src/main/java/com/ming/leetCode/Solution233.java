package com.ming.leetCode;

/**
 * 233.数字1的个数
 *  给定一个整数n，计算所有小于等于n的非负整数中数字1出现的个数
 *
 *  示例1：
 *      输入：13
 *      输出：6
 *      解释： 数字出现1 的数字有，1,10,11,12,13.
 * @Author ming
 * @time 2020/3/18 14:22
 */
public class Solution233 {
    public int countDigitOne(int n) {
        int count = 0;
        for (int i = 1; i<=n; i*=10){
            int divider = i * 10;
            count += (n/divider) * i + Math.min(Math.max(n%divider - i +1,0),i);
        }

        return count;
    }

    /**
     * ① 501在十位上：
     * 501十位 0 ，所以高位 5只能取0~4 ，此时十位才能取1，个位任意取0~9，共50种
     * ② 511在十位上：
     * 511 十位1，所以高位 5只能取0~4 ，此时十位才能取1，个位任意取0~9，共50种，另外当十位取1的时候，低位为1因此可以取到一个数字511，再加上原本的510，共52种。
     * ③ 521在十位上：
     * 521十位2，所以高位5可以取0~5，此时十位取1，个位任意取，共60种
     *
     * @param n
     * @return
     */
    public int countDigitOne2(int n) {
        //求每个位的数字所用
        int index = 1;
        //记录1的个数
        int count = 0;
        int high = n,cur = 0,low = 0;
        //由于high = n /(index*10) 中index *10 很容易越位
        //特修改如下
        while(high > 0){
            high /= 10;
            cur = (n / index) % 10;
            low = n - (n / index) * index;
            //以下是计算的公式
            if(cur == 0) count += high * index;
            if(cur == 1) count += high * index + low + 1;
            if(cur > 1) count += (high+1) * index;
            index *= 10;
        }
        return count;
    }

}
