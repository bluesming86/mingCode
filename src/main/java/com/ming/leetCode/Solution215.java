package com.ming.leetCode;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * 215.数组中的第K个最大元素
 *  在末排序的数组中找到第K个最大的元素。请注意，你需要找的是数组排序后的 第K个最大元素，而不是第K个不同的元素
 *
 *  示例1 :
 *      输入 【3,2,1,5,6,4】 和k=2
 *      输出 4
 *  示例 2:
 *      输入  【3,2,3,1,2,4,5,5,6】 和 k = 4
 *      输出： 4
 *
 *   说明： 你可以假设k 总是有效的，且 1《= k <= 数组的长度
 * @Author ming
 * @time 2020/3/5 16:18
 */
public class Solution215 {

    /**
     * 方法一： 堆
     *  思路 是创建一个大顶堆，将所有数组中的元素加入堆中，并保持堆的大小 小于等于 k.
     *  这样，堆中就保留了前 k个最大的元素。这样，堆顶就是元素的正确答案。
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k){

        PriorityQueue<Integer> heep = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);

        for (int n : nums){
            heep.add(n);
            if (heep.size() >k){
                heep.poll();
            }
        }
        return heep.poll();
    }

    /**
     * 方法二： 快速选择
     *
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k){
        this.numsTemp = nums;
        int size = nums.length;
        return quickSelect(0, size -1, size - k);
    }

    int [] numsTemp;

    /**
     * 交换
     * @param a
     * @param b
     */
    public void swap(int a, int b){
        int temp = this.numsTemp[a];
        this.numsTemp[a] = this.numsTemp[b];
        this.numsTemp[b] = temp;
    }

    public int partition(int left, int right, int pivot_index){
        int pivot = this.numsTemp[pivot_index];

        //1 move pivot to end.   pivot 枢纽，枢
        swap(pivot_index, right);
        int store_index = left;

        //2 move all smaller elements to the left , 把全部小的结点移动到左边
        for (int i = left; i <= right; i++) {
            if (this.numsTemp[i] < pivot){
                swap(store_index, i);
                store_index++;
            }
        }

        //3， move pivot to its final place.  移动枢纽到 最终的位置
        swap(store_index, right);

        return store_index;
    }

    public int quickSelect(int left, int right, int k_smallest){
        if (left == right){
            return this.numsTemp[left];
        }

        Random random_num = new Random();
        int pivot_index = left + random_num.nextInt(right - left);

        pivot_index = partition(left, right, pivot_index);

        if (k_smallest == pivot_index){
            return this.numsTemp[k_smallest];
        } else if (k_smallest < pivot_index){
            //go left side  向左走
            return quickSelect(left, pivot_index - 1, k_smallest);
        }
        //go right side  向右走
        return quickSelect(pivot_index +1, right, k_smallest);
    }
}
