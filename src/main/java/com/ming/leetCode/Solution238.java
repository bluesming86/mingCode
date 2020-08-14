package com.ming.leetCode;

/**
 * 238 除自身以外的数组的乘积
 *   给你一个长度为n的数组nums,其中 n>1, 返回输出数组output,
 *   其中output[i]等于nums中除nums[i]之外的其余各元素的乘积
 * @Author ming
 * @time 2020/3/19 14:35
 */
public class Solution238 {

    /**
     * 自己写的 的
     * 貌似效率有限
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int result[] = new int[length];
        int lastResult = 1;
        int tempResult;
        for(int i = 0;i<length; i++){
            tempResult = lastResult;
            lastResult = tempResult * nums[i];
            for(int j=i+1;j<length; j++){
                tempResult *= nums[j];
            }
            result[i] = tempResult;
        }
        return result;
    }

    /**
     * 左右乘积法。
     *
     * 复杂度分析
     *
     * 时间复杂度：O(N)，其中 NN 指的是输入数组的大小。
     * 空间复杂度：O(N)，使用了 L 和 R 数组去构造答案。
     * @param nums
     * @return
     */
    public int[] productExceptSelf2(int[] nums) {
        int length = nums.length;

        int[] L = new int[length];//左乘积 保存的是 各个元素 左边的乘积
        int[] R = new int[length];//右乘积 保存的是 各个元素的 右边的乘积

        int[] answer = new int[length]; //结果集

        L[0] = 1;//第一个元素，左乘积 标记为1
        for (int i = 1; i < length; i++){
            L[i] = nums[i - 1] * L[i-1]; //左乘积为上一个数的左乘积 乘以上一个数
        }
        R[length-1] = 1;//最后一个元素，右乘积 标记为1
        for (int i = length -2; i>= 0;i--){//从倒数第第二个 开始找
            R[i] = nums[i + 1]* R[i+1]; //
        }

        for (int i = 0; i< length; i++){ //结果集  左乘积 乘以右乘积 即为各个元素的结果
            answer[i] = L[i] * R[i];
        }
        return answer;
    }

    /**
     * 也是左右乘积，
     * 优于上面的是，计算出左乘积后，在计算右乘积时，直接算出结果
     *
     * 复杂度分析
     *
     * 时间复杂度：O(N) ，其中 NN 指的是输入数组的大小。
     * 空间复杂度：O(1) ，问题的描述中说明了输出数组不算空间复杂度。
     * @param nums
     * @return
     */
    public int[] productExceptSelf3(int[] nums) {
        int length = nums.length;

        int[] answer = new int[length];

        answer[0] = 1;
        for (int i= 1;i<length; i++){
            answer[i] = nums[i -1] * answer[i -1]; //左乘积 直接先放入 结果集中
        }
        int R = 1;//用来保存右乘积
        for (int i = length -1; i>=0; i--){
            answer[i] = answer[i] * R;
            R *= nums[i];
        }
        return answer;
    }
}
