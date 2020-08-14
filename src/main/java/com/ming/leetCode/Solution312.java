package com.ming.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 312 戳气球
 *      有 n 个气球，编号为 0 到 n - 1, 每个气球上都标有一个数字，这些数字存在数组nums中。
 *
 *      现在要求你戳破所有的气球。每当你戳破一个气球i 时，
 *      你可以获得nums[left] * nums[i] * nums[right]个硬币。
 *
 *      这里的left 和 right 代表和i 相邻的两个气球的序号。注意当你戳破了
 *      气球i 后，气球left 和气球 right 就变成相邻的气球。
 *
 *      求所能获得硬币的最大数量
 *
 *      说明：
 *          你可以假设 nums[-1] = nums[n] = 1, 但注意它们不是真实存在的所以并不能被戳破
 *          。
 *          0<=n<=500, 0<=nums[i] <= 100
 *
 *
 *  示例:
 *
 * 输入: [3,1,5,8]
 * 输出: 167
 * 解释: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 *      coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 * @Author ming
 * @time 2020/3/21 16:50
 */
public class Solution312 {
    public int maxCoins(int[] nums){
        int dp[][] = new int[nums.length][nums.length];
        if(nums.length == 0){  //沙雕测试用例[]
            return 0;
        }
        for(int i = 0; i<nums.length; i++){
            for(int j = 0;j<nums.length-i;j++){
                fill(dp,nums,j,j+i);
            }
        }
        return dp[0][nums.length-1];
    }
    void fill(int[][] dp,int nums[],int start,int end){
        int max = 0;
        for(int i=start; i<=end;i++){
            max = Math.max(max,(start-1<0 ? 1 : nums[start-1])*nums[i]*(end+1>nums.length-1 ? 1 : nums[end+1]) + (start>i-1 ? 0 : dp[start][i-1]) + (end < i+1 ? 0 : dp[i+1][end]));
        }
        dp[start][end] = max;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(10);

        System.out.println(list);
        list.add(9,10);
        System.out.println(list);
    }
}
