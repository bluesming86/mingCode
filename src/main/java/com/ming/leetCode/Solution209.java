package com.ming.leetCode;

/**
 * 长度最小的子数组
 *  给定一个含有n 个正整数的数组和一个正整数s，找出该数组中满足其和>=s的长度
 *  最小的连续子数组。如果不存在符合条件的连续子数组，返回0.
 *
 *  示例：
 *      输入s = 7, nums = [2,3,1,2,4,3]
 *      输出 2
 *      解释： 子数组【4,3】是该条件下的长度最小的连续子数组。
 *
 *  进阶 如果已经完成了0（n） 时间复杂度的解法，请尝试O(n log n)时间复杂度的解法。
 * @Author ming
 * @time 2020/2/24 14:59
 */
public class Solution209 {


    /**
     * 解法一 暴力破解
     *      从第0 个数字开始，依次添加数字，记录当总和大于等于 s的长度。
     *      从第1 个数字开始，依次添加数字，记录当总和大于等于 s的长度。
     *      从第2 个数字开始，依次添加数字，记录当总和大于等于 s的长度。
     *      。。。
     *      从最后个数字开始，依次添加数字
     *
     *   时间复杂度 O(n2)
     * @param s
     * @param nums
     * @return
     */
    public int minSubArrayLen(int s, int[] nums){

        int min = Integer.MAX_VALUE;
        int n = nums.length;
        for (int i = 0; i< n; i ++){
            int start = i;
            int sum = 0;
            while(start < n){
                sum += nums[start];
                start++;
                //当前和大于等于s的时候结束
                if (sum >= s){
                    min = Math.min(min, start - i);
                    break;
                }
            }
        }
        //min 是否更新，如果没有更新说明数组所有的数字和小于s, 没有满足条件的解，返回 0

        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * 解法二 双指针（滑动窗口）
     *    用双指针 left 和 right 表示一个窗口
     *      1.right 向右移增大窗口，知道窗口内的数字和大于等于了s,  进行第二步
     *      2.记录此时的长度， left 向右移动，开始减少长度，每减少一次，就更新最小长度。直到当前窗口内的数字和小于了 s,回到第一步
     *
     * @param s
     * @param nums
     * @return
     */
    public int minSubArrayLen2(int s, int[] nums){

        return 0;
    }
}
