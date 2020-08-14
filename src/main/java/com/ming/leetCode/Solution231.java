package com.ming.leetCode;

/**
 * 231  2的幂
 *   给定一个整数， 编写一个函数来判断 它是否是2的幂次方
 *     示例1：
 *          输入 1 输出true
 *          输入16 输出 true
 *          输入218 输出 false
 *
 *
 *          数 除以2 能否除的尽
 * @Author ming
 * @time 2020/3/18 10:42
 */
public class Solution231 {

    /**
     * 自己写的
     *   超时
     * @param n
     * @return
     */
    public boolean isPowerOfTwo(int n) {
        if (n == 1) return true;
        while(n % 2 == 0){
            if (n == 1){
                return true;
            }
            n = n /2;

        }
        return false;
    }

    /**
     *  根据位运算
     * @param n
     * @return
     */
    public boolean isPowerOfTwo2(int n) {
        if (n == 0) return false;
        long x = (long)n;
        return (x & (-x)) == x;
    }
    /**
     *  根据位运算
     *   解题思路：
     *   若 n = 2^x 且x为自然数（即n为2的幂），则一定满足以下条件：
     *      1.恒有 n & (n -1) == 0这是因为
     *          n 二进制最高位为1， 其余所有位为0、
     *          n -1 二进制最高位为0，其余所有位为1
     *      2.n 一定满足n > 0
     *   因此 ，通过 n>0 且 n& (n-1) == 0 即可判定是否满足n = 2^x
     *
     *   2^n 举例
     *   2^1 = 0000 0010
     *   2^2 = 0000 0100
     *   2^3 = 0000 1000
     *   2^4 = 0001 0000
     *   2^5 = 0010 0000
     * @param n
     * @return
     */
    public boolean isPowerOfTwo3(int n) {
        if (n == 0) return false;
        long x = (long)n;
        return (x & (x - 1)) == 0;
    }
}
