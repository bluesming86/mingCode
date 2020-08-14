package com.ming.leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * 219存在重复元素II
 *      给定一个整数数组合一个整数k，判断数组中是否存在两个
 *      不同的索引i和j，使得nums[i] = nums[j],并且i和j的差的绝对值最大为k
 * @Author ming
 * @time 2020/2/26 14:59
 */
public class Solution219 {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int length = nums.length;
        if (k < 1 || length<=0){
            return false;
        }
        for (int i = 0; i < length ; i++){
            for(int j=i+1;j<=i+k&& j< length;j++){
                if (nums[i] == nums[j]){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i< nums.length; i++){
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
            if (set.size() >k){
                set.remove(nums[i - k]);//如果当前散列表的大小超过了 k， 删除散列表中最旧的元素
            }
        }

        return false;
    }
}
