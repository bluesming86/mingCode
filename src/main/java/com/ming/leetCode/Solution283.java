package com.ming.leetCode;

/**
 * 283. 移动零
 *      给定一个数组 nums ,编写一个函数将所有 0 移动到数组的末尾，
 *      同时保持非零 元素的相对顺序
 *
 *     示例：
 *      输入：【0,1,0,3,12】
 *      输出:【1，3,12,0,0】
 *
 * @Author ming
 * @time 2020/3/20 19:22
 */
public class Solution283 {

    /**
     * 自己写的
     *   多循环了次。
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int length = nums.length;
        if(length == 0) return;

        int maxIndex = length -1;

        for(int i = length-1; i >= 0; i--){
            if(nums[i] == 0){
                //把 0  后面的到maxIndex的数往前， 在maxIndex给0,重新设置 maxIndex
                for(int j = i;j <= maxIndex-1 ;j++){
                    nums[j] = nums[j+1];
                }
                nums[maxIndex] = 0;
                maxIndex--;
            }
        }
    }

    /**
     *  直接覆盖。一次
      * @param nums
     */
    public void moveZeroes2(int[] nums) {
        //定义 nums[0]~nums[lastNonZeroIndex]!=0
        int lastNonZeroIndex = -1;//上一个不是 0的index
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {//把不等于0 的 跟到上一个不是0 的数的后面
                nums[++lastNonZeroIndex] = nums[i];
                //避免原地覆盖
                if (i != lastNonZeroIndex) {
                    nums[i] = 0;
                }
            }
        }
    }
}
