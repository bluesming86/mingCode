package com.ming.leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * 217 存在重复元素
 *   给定一个整数数组，判断是否存在重复元素
 *
 *   如果任何值在数组中出现至少两次，函数返回true。如果数组中每个元素都不相同，
 *   则返回false
 *
 *   示例1：
 *      输入：【1,2,3,1】
 *      输出： true
 *   示例2：
 *      输入：【1，2,3,4,】
 *      输出： false
 *   示例3：
 *      输入：【1，1,1,1,3,3,4,3,2,4,2】
 *      输出： true
 * @Author ming
 * @time 2020/2/26 14:32
 */
public class Solution217 {

    public static boolean containsDuplicate(int[] nums) {

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)){
                return true;
            }
            set.add(num);
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,1};
        System.out.println(containsDuplicate(nums));
    }
}
