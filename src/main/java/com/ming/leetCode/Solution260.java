package com.ming.leetCode;

import java.util.*;

/**
 * 260只出现一次的数字 III
 *  给定一个整数数组 nums ,找出只出现一次的元素
 *    示例
 *      输入 【1,2,1,3,2,5】
 *      输出 【3,5】
 * @Author ming
 * @time 2020/3/19 20:17
 */
public class Solution260 {

    /**
     * 方法一 哈希表
     *  建立一个值到频率的映射关系的哈希表，返回频率为1的数字
     * @param nums
     * @return
     */
    public static int[] singleNumber(int[] nums){
        Map<Integer, Integer> map = new HashMap<>();
        int haveSize = 0;
        for (int num : nums) {
            if (map.containsKey(num)){
                if (map.get(num) == 1){
                    haveSize += 2;
                }else {
                    haveSize ++;
                }
                map.put(num, map.get(num) + 1);

            } else {
                map.put(num, 1);
            }
        }
        int[] output = new int[nums.length - haveSize];
        int idx = 0;
        for (Integer num : map.keySet()) {
            if (map.get(num) == 1){
                output[idx++] = num;
            }
        }
        return output;
    }

    /**
     * 位运算
     * @param nums
     * @return
     */
    public int[] singleNumber2(int[] nums) {
        // difference between two numbers (x and y) which were seen only once
        int bitmask = 0;
        for (int num : nums) bitmask ^= num;

        // rightmost 1-bit diff between x and y
        int diff = bitmask & (-bitmask);

        int x = 0;
        // bitmask which will contain only x
        for (int num : nums) if ((num & diff) != 0) x ^= num;

        return new int[]{x, bitmask^x};
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        System.out.println(Arrays.toString(singleNumber(arr)));
    }
}
