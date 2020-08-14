package com.ming.leetCode;

/**
 * 309 最佳买卖股票时机含冷冻期
 *
 * 给定一个整数数组，其中第i个元素代表了第i天的股票价格
 *
 * 设计一个算法 计算出最大利润。在满足以下约束条件下，你可以尽可能地完成
 *  更多的交易（多次买卖一支股票）
 *    你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *    卖出股票后，你无法再第二天买入股票（即冷冻期为1天）
 *
 *  示例：
 *      输入 【1,2,3,0,2】
 *      输出 3
 *      解析 对应的交易状态为：【买入，卖出，冷冻期，买入，卖出】
 * @Author ming
 * @time 2020/3/21 16:19
 */
public class Solution309 {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        // 特判
        if (len < 2) {
            return 0;
        }

        int[] dp = new int[3];

        // 初始化
        dp[0] = 0;
        dp[1] = -prices[0];
        dp[2] = 0;

        int preCash = dp[0];
        int preStock = dp[1];

        for (int i = 1; i < len; i++) {
            dp[0] = Math.max(preCash, preStock + prices[i]);
            dp[1] = Math.max(preStock, dp[2] - prices[i]);
            dp[2] = preCash;

            preCash= dp[0];
            preStock = dp[1];
        }
        return Math.max(dp[0], dp[2]);
    }
}
