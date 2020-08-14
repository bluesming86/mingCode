package com.ming.leetCode;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 268缺失数字
 *  给定一个包含 0,1,2,3,。。。。，n 中n个数的序列，
 *  找出序列中没有出现的那个数
 *
 *  示例1：
 *      输入[3,0,1]
 *      输出 2
 *  示例 2：
 *      输入[9,6,4,2,3,5,7,0,1]
 *      输出 8
 * @Author ming
 * @time 2020/3/20 10:10
 */
public class Solution268 {

    /**
     * 排序法，先排序 然后按顺序找
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0;i < nums.length; i++){
            if (nums[i] != i){
                return i;
            }
        }
        return nums.length;//没找到。说明 缺少最后一个
    }

    /**
     * 哈希表法
     * @param nums
     * @return
     */
    public int missingNumber2(int[] nums) {
        HashSet<Integer> numSet = new HashSet<>();
        for (int n: nums){
            numSet.add(n);
        }
        for (int i=0;i<=nums.length;i++ ){
            if (!numSet.contains(i)){
                return i;
            }
        }
        return -1;
    }

    /**
     * 数学法
     *   直接给 0 - n 求和
     *
     *   n * (n -1) / 2
     *
     * @param nums
     * @return
     */
    public int missingNumber3(int[] nums) {
        int expectedSum = nums.length*(nums.length + 1)/2;
        int actualSum = 0;
        for (int num : nums){
            actualSum += num;
        }
        return expectedSum - actualSum;
    }

    /**
     * 位运算
     */
    public int missingNumber4(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) {
            missing ^= i ^ nums[i];
        }
        return missing;
    }
}
