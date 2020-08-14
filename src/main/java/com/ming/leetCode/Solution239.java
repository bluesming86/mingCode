package com.ming.leetCode;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * 滑动窗口最大值
 *      给定一个数组nums 有一个大小为k的滑动窗口，从数组的最左侧
 *      移动到数组的最右侧。你只可以看到在滑动窗口内的k个数字。
 *      滑动窗口每次只向右移动一位。
 *     返回滑动窗口的最大值
 *
 *     示例
 *      输入 nums=[1,3,-1,-3,5,3,6,7] 和 k = 3
 *      输出 [3,3,5,5,6,7]
 *      解释:
 *
 *   滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 * @Author ming
 * @time 2020/3/19 16:03
 */
public class Solution239 {

    /**
     * 自己写的 。暴力法
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {

        if(nums == null || nums.length ==0){
            return new int[0];
        }

        int length = nums.length;
        if(k > length){
            k = length;
        }
        int result[] = new int[length - k +1];
        int maxValueIndex = -1;
        int maxValue = nums[0];
        for(int i=0; i<=length - k; i++){

            if(maxValueIndex > i && maxValueIndex != -1){
                if(maxValue < nums[i+k-1]){
                    maxValue = nums[i+k-1];
                    maxValueIndex = i+k-1;
                }
            } else {
                maxValue = nums[i];
                maxValueIndex = i;
                for(int j =i+1; j<i+k; j++){
                    if(maxValue<nums[j]){
                        maxValue = nums[j];
                        maxValueIndex = j;
                    }
                }
            }

            result[i] = maxValue;
        }
        return result;
    }

    /**
     * 方法一 ： 暴力法
     *
     * 虽然代码简单点，但是效率没自己写的高。
     * 复杂度分析
     *      时间复杂度：{O}(N k) 。其中 N 为数组中元素个数。
     *      空间复杂度：{O}(N - k + 1) ，用于输出数组。
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow2(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];

        int[] output = new int[n -k +1];
        for (int i = 0; i< n-k +1; i++){
            int max = Integer.MIN_VALUE;
            for (int j = i; j< i+k; j++){
                max = Math.max(max, nums[j]);
            }
            output[i] = max;
        }
        return output;
    }

    ArrayDeque<Integer> deq = new ArrayDeque<>();//数组队列
    int[] nums;
    public void cleanDeque(int i, int k){//
        if (!deq.isEmpty() && deq.getFirst() == i-k){
            deq.removeFirst();
        }
        while (!deq.isEmpty() && nums[i] > nums[deq.getLast()]){
            deq.removeLast();
        }
    }
    /**
     * 方法二 双向队列
     *     如何优化 时间复杂度呢？ 首先想到的是使用堆，因此在最大堆中 heap[0] 永远是最大的元素。
     *     在大小为k的堆中插入一个元素消耗log(k)时间。
     *
     *     我们可以使用 双向队列，该数据结构可以从两端以常数时间压入/弹出元素。
     *     存储双向队列的索引比存储元素更方便，因此两者都能在数组解析中使用。
     *
     *     算法
     *
     *     算法非常直截了当：
     *
     *     处理前 k 个元素， 初始化双向队列。
     *     遍历整个数组。 在每一步：
     *     清理双向队列
     *     - 只保留当前滑动窗口中有的元素的索引
     *     - 移除 比当前元素小的所有元素，
     * @param nums
     * @param k
     * @return
     */
    public  int[] maxSlidingWindow3(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];
        if (k == 1) return nums;

        this.nums = nums;
        int max_idx=0;
        for (int i = 0; i< k;i++){
            cleanDeque(i, k);
            deq.addLast(i);

            if (nums[i] > nums[max_idx]) max_idx = i;
        }
        int[] output = new int[n - k +1];
        output[0] = nums[max_idx];

        for (int i = k; i < n; i++){
            cleanDeque(i, k);
            deq.addLast(i);
            output[i - k + 1] = nums[deq.getFirst()];
        }
        return  output;
    }


    /**
     *  动态规划
     *
     * @param nums
     * @param k
     * @return
     */
    public  int[] maxSlidingWindow4(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];
        if (k == 1) return nums;

        int [] left = new int[n];
        left[0] = nums[0];
        int [] right = new int[n];
        right[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            // from left to right
            if (i % k == 0) left[i] = nums[i];  // block_start
            else left[i] = Math.max(left[i - 1], nums[i]);

            // from right to left
            int j = n - i - 1;
            if ((j + 1) % k == 0) right[j] = nums[j];  // block_end
            else right[j] = Math.max(right[j + 1], nums[j]);
        }

        int [] output = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++)
            output[i] = Math.max(left[i + k - 1], right[i]);

        return output;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        System.out.println(Arrays.toString(maxSlidingWindow(nums,2)) );

    }
}
