package com.ming.leetCode;

/**
 * 189. 旋转数组
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 *
 * 示例 1:
 *
 * 输入: [1,2,3,4,5,6,7] 和 k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右旋转 1 步: [7,1,2,3,4,5,6]
 * 向右旋转 2 步: [6,7,1,2,3,4,5]
 * 向右旋转 3 步: [5,6,7,1,2,3,4]
 *
 *  示例 2:
 *
 * 输入: [-1,-100,3,99] 和 k = 2
 * 输出: [3,99,-1,-100]
 * 解释:
 * 向右旋转 1 步: [99,-1,-100,3]
 * 向右旋转 2 步: [3,99,-1,-100]
 *
 * 说明:
 *
 * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
 * 要求使用空间复杂度为 O(1) 的 原地 算法。
 * @Author ming
 * @time 2020/2/21 8:56
 */
public class Solution189 {

    /**
     * 方法1 ： 暴力法
     * 移动k 个位置 那么久移动k 次循环
     *
     * 复杂度分析
     *
     * 时间复杂度：O(n*k) 。每个元素都被移动 1 步（O(n)） k次（O(k)） 。
     * 空间复杂度：O(1) 。没有额外空间被使用。
     *
     * @param nums
     * @param k
     */
    public void rotate1(int[] nums, int k) {

        if (nums!= null && nums.length >0){
            k = k % nums.length;
            int temp ,preNum = nums[nums.length-1];
            for (int i = 0; i < k; i++) {
                for (int j = 0; i <nums.length; j++){
                    temp = nums[j];
                    nums[j] = preNum;
                    preNum = temp;
                }
            }
        }
    }

    /**
     * 使用额外的数组
     *
     * 我们可以用一个额外的数组来将每个元素放到正确的位置上，也就是原本数组里下标为 ii的我们把它放到 (i+k)\%数组长度(i+k)%数组长度 的位置。然后把新的数组拷贝到原数组中。
     *
     * @param nums
     * @param k
     */
    public void rotate2(int[] nums, int k) {

        if (nums != null && nums.length > 0){
            int[] a = new int[nums.length];
            for (int i=0;i<nums.length; i++){
                a[(i+k)%nums.length] = nums[i];
            }
            for (int i= 0; i< nums.length;i++){
                nums[i] = a[i];
            }
        }

    }

    /**
     * 使用额外的数组
     *
     * 我写的， 额外数组用来存 后面要调 0开始的数
     *  然后 正常的 移动位置
     *  最后在把额外数组 补充到 前面
     * @param nums
     * @param k
     */
    public void rotate3(int[] nums, int k) {

        if (nums != null && nums.length > 0){
            int length = nums.length;
            k = k % length;
            int[] a = new int[k];
            for (int i = 0; i < k; i++){
                a[i] = nums[length - k + i];
            }
            for (int i = length -1;i >= k; i--){
                nums[i] = nums[i-k];
            }
            for (int i = 0;i<k ;i++){
                nums[i] = a[i];
            }
        }

    }

    /**
     * 使用环状替换
     *  就是根据移动k一直走下去，当重新走到起始位置时停止，形成一个环，
     *
     *  时间复杂度：O(n)O(n) 。只遍历了每个元素一次。
     * 空间复杂度：O(1)O(1) 。使用了常数个额外空间。
     * @param nums
     * @param k
     */
    public void rotate4(int[] nums, int k) {

        if (nums != null && nums.length >0){
            k = k% nums.length;
            int length = nums.length;
            int count = 0;
            for (int i = 0; count <length ; i++){
                int startIndex = i;
                int currentIndex = i;
                int moveNum = nums[startIndex];
                int temp ,nextIndex;
                do {
                    nextIndex = (currentIndex + k) % length;
                    temp = nums[nextIndex];
                    nums[nextIndex] = moveNum;
                    moveNum = temp;
                    currentIndex = nextIndex;
                    count ++;
                }while (startIndex != currentIndex);
            }
        }

    }

    /**
     * 使用反转
     * 先反转，然后 根据移动的 k 左边反转
     * k 的右边在反转，就能到结果
     *
     * 时间复杂度：O(n) 。 nn 个元素被反转了总共 3 次。
     * 空间复杂度：O(1) 。 没有使用额外的空间。
     * @param nums
     * @param k
     */
    public void rotate5(int[] nums, int k) {

        //先全部反转
        reverse(nums,0,nums.length-1);
        //移动k的左边反转
        reverse(nums,0,k-1);
        //k的右边反转
        reverse(nums, k, nums.length - 1);

    }

    public void reverse(int[] nums, int startIndex, int endIndex){
        while (startIndex < endIndex){
            int temp = nums[endIndex];
            nums[endIndex] = nums[startIndex];
            nums[startIndex] = temp;
            endIndex --;
            startIndex++;
        }
    }

}
