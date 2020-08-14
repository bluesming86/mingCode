package com.ming.leetCode;

/**
 * 寻找重复数
 *   给定一个 包含 n+1的整数的数组 nums,
 *   其数字都在 1 到 n 之间（包含1 和 n）,可知 至少存在一个重复的整数。
 *   假设只有一个重复的整数，找出这个重复的数
 *
 *   示例 1：
 *      输入【1,3,4,2，2】 输出 2
 *
 *    示例2 ：
 *      输入 【3,1,3,4,2】 输出 3
 *
 *     说明 ：
 *       1. 不能 更改原来数组（假设 数组是只读的）
 *       2. 只能使用 额外的o(1)的空间
 *       3.时间复杂度 小于O(n^2)
 *       4.数组中只有一个重复的数字。但它可能不止重复出现一次
 * @Author ming
 * @time 2020/3/20 19:40
 */
public class Solution287 {


    public int findDuplicate(int[] nums) {
        int tortoise = nums[0];
        int hare = nums[0];

        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (tortoise != hare);

        int ptr1 = nums[0];
        int ptr2 = tortoise;
        while (ptr1 != ptr2){
            ptr1 = nums[ptr1];
            ptr2 = nums[ptr2];
        }
        return  ptr1;
    }
}
