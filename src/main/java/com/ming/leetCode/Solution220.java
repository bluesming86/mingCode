package com.ming.leetCode;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 220 存在重复元素III
 *      给定一个整数数组，判断数组中是否有两个不同的索引i和j,
 *      使得nums[i] 和nums[j]的差的绝对值最大为t,并且i和j之间的差的绝对值最大为k.
 *
 *   示例1：
 *      输入：nums=[1,2,3,1] , k =3, t=0
 *      输出: true
 *
 *   示例2：
 *      输入：nums=[1,0,1,1], k=1, t=2
 *      输出true
 *   示例3：
 *      输入 nums=[1,5,9,1,5,9], k=2, t=3
 *      输出: false
 * @Author ming
 * @time 2020/2/26 15:23
 */
public class Solution220 {

    /**
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {

        int length = nums.length;
        for (int i = 0 ; i < length; i++){
            for (int j = i+1; j<= i+k && j < length; j++){
                if (Math.abs((long)nums[j] - nums[i]) <= t){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 二叉搜索树
     *   思路
     *      如果窗口中维护的元素是有序的，只需要用二分搜索检查条件二是否满足的就可以了。
     *      利用自平衡二叉搜索树，可以在对数时间内通过 插入 和删除 来对滑动窗口内元素排序
     *   算法
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {

        TreeSet<Integer> set = new TreeSet<>();
        for (int i=0; i < nums.length; i ++){
            Integer s = set.ceiling(nums[i]);//返回set中 大于等于 nums[i]的最小元素
            if (s != null && s <= nums[i] + t) return true;

            Integer g = set.floor(nums[i]);// 返回set中  小于等于 nums[i] 的最大元素
            if (g != null && nums[i] <= g+t) return true;

            set.add(nums[i]);
            if (set.size() > k){
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

}
