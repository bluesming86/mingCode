package com.ming.leetCode;

/**
 * 263丑数
 *  编写一个程序判断给定的数是否为丑数
 *   丑数就是只包含质因数， 2,3,5的正整数
 *
 *   示例 1：
 *     输入 6  输出 true  解释 6 = 2 x3
 *     输入 8  输出 true  解释 8 = 2 x 2x2
 *     输入14  输出 false 解释 14 = 2 x 7   7不是质因数，因此 返回 false
 * @Author ming
 * @time 2020/3/19 20:46
 */
public class Solution263 {

    /**
     * 自己写的
     * @param num
     * @return
     */
    public boolean isUgly(int num) {

        if (num < 2) return false;
        while (num % 2 == 0){
            num = num /2;
        }
        while (num % 3 ==0){
            num = num /3;
        }
        while (num % 5 == 0){
            num = num /5;
        }

        return num == 1;
    }
}
