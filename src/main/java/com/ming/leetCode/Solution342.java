package com.ming.leetCode;

/**
 * 342 .4的幂
 *  给定一个整数（32位 有符号整数）。请编写一个函数 来判断 它是否是4的幂次方
 *
 *      示例 1：
 *          输入 16 输出 true
 *          输入 5 输出 false
 * @Author ming
 * @time 2020/3/31 14:24
 */
public class Solution342 {

    //暴力破解
    public boolean isPowerOfFour1(int num) {
        if (num <= 0) return false;

        while (num % 4 ==0){
            num = num / 4;
        }
        return num == 1;
    }

    /**
     * 位运算
     * 4  = 0000 0100
     * 16 = 0001 0000
     * 64 = 0100 0000
     * @param num
     * @return
     */
    public boolean isPowerOfFour2(int num) {
        return (num > 0) && ((num & (num - 1)) == 0) && ((num & 0xaaaaaaaa) == 0);
    }

    public boolean isPowerOfFour3(int num) {
        return (num > 0) && ((num & (num - 1)) == 0) && (num % 3 == 1);
    }
}
