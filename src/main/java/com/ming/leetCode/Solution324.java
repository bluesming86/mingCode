package com.ming.leetCode;

import java.util.Arrays;

/**
 * 给定一个无序的数组 nums,
 *  将它重新排列成 nums[0] < nums[1]
 *  > nums[2] < nums[3]....的顺序
 *
 *  示例1： 输入： nums = [1,5,1,1,6,4]
 *          输出：一个可能的答案是[1,4,1,5,1,6]
 *  示例 2:
 *          输入: nums = [1, 3, 2, 2, 3, 1]
 *          输出: 一个可能的答案是 [2, 3, 1, 3, 1, 2]
 *  说明:
 *      你可以假设所有输入都会得到有效的结果。
 *   进阶:
*          你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？
 * @Author ming
 * @time 2020/3/26 17:37
 */
public class Solution324 {

    /**
     * 先排序再穿插
     *  O(nlogn)+O(n)=O(nlogn)
     * @param nums
     */
    public void wiggleSort(int[] nums) {
        //排序
        Arrays.sort(nums);
        int len = nums.length, i = 0;
        int[] smaller=new int[len%2==0?len/2:(len/2+1)],bigger=new int[len/2];
        //复制
        System.arraycopy(nums,0,smaller,0,smaller.length);
        System.arraycopy(nums,smaller.length,bigger,0,len/2);
        //穿插
        for (; i < len / 2; i++) {
            nums[2*i]=smaller[smaller.length-1-i];
            nums[2*i+1]=bigger[len/2-1-i];
        }
        if (len%2!=0) nums[2*i]=smaller[smaller.length-1-i];
    }
}
