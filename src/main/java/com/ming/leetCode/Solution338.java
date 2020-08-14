package com.ming.leetCode;

/**
 *
 * 338. 比特位计数
 *      给定一个非负整数num.对于0 <=i<=num 范围中的每个数字i,
 *      计算其二进制数中的1的数目并将它们作为数组返回。
 *
 *    示例1
 *      输入 2  输出 【0,1,1】
 *      输入 5  输出 【0,1,1,2,1,2】
 * @Author ming
 * @time 2020/3/31 11:06
 */
public class Solution338 {

    /**
     * 本问题可以看做 位 1 的个数 的后续。它计数一个无符号整数的位。结果称为 pop count，或 汉明权重。可以参看 位 1 的个数 的题解以获得更详细介绍。
     *
     * 现在，我们先默认这个概念。假设我们有函数int popcount(int x) ，可以返回一个给定非负整数的位计数。我们只需要在 [0, num] 范围内循环并将结果存到一个列表中。
     *
     * @param num
     * @return
     */
    public int[] countBits(int num) {
        int[] ans = new int[num + 1];
        for (int i = 0; i <= num; ++i)
            ans[i] = popcount(i);
        return ans;
    }
    private int popcount(int x) {
        int count;
        for (count = 0; x != 0; ++count)
            x &= x - 1; //zeroing out the least significant nonzero bit
        return count;
    }


    /**
     * 动态规划+ 最高有效位
     * @param num
     * @return
     */
    public int[] countBits2(int num) {
        int[] ans = new int[num + 1];
        int i = 0, b = 1;
        // [0, b) is calculated
        while (b <= num) {
            // generate [b, 2b) or [b, num) from [0, b)
            while(i < b && i + b <= num){
                ans[i + b] = ans[i] + 1;
                ++i;
            }
            i = 0;   // reset i
            b <<= 1; // b = 2b
        }
        return ans;
    }

    /**
     * 动态规划+ 最低有效位
     * @param num
     * @return
     */
    public int[] countBits3(int num) {
        int[] ans = new int[num + 1];
        for (int i = 1; i <= num; ++i)
            ans[i] = ans[i >> 1] + (i & 1); // x / 2 is x >> 1 and x % 2 is x & 1
        return ans;
    }

    public int[] countBits4(int num) {
        int[] ans = new int[num + 1];
        for (int i = 1; i <= num; ++i)
            ans[i] = ans[i & (i - 1)] + 1;
        return ans;
    }
}
