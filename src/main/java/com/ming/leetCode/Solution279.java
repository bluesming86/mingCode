package com.ming.leetCode;

/**
 * 279 完全平方数
 *  给定正整数n, 找到若干个完全平方数， （比如 1,4,9,16，。。。）
 *  使得它们的和等于n. 你需要让组成和的完全平方数的个数最少。
 *
 *   示例 1  输入 n = 12 输出 3
 *        解释 12 = 4+4+4
 *   示例2  输入 n = 13 输出 2
 *          解释 13 = 4+9
 * @Author ming
 * @time 2020/3/20 17:32
 */
public class Solution279 {
    /**
     * 动态规划
     * @param n
     * @return
     */
    public int numSquares(int n) {

        int[] dp  = new int[n+1];//默认初始化值都为 0
        for (int i = 1;i<= n;i++){
            dp[i] = i;//最坏的情况就是每次+1

            for (int j=1; i - j *j >=0; j++){
                dp[i] = Math.min(dp[i],dp[i-j*j] + 1);//动态转移方程
            }
        }

        return dp[n];
    }
}
