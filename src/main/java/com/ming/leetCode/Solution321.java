package com.ming.leetCode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 321.拼接最大数
 *  给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，
 *  表示两个自然数各位上的数字。现在从这两个数组中选出 k (k <= m + n) 
 *  个数字拼接成一个新的数，
 *  要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
 * 求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
 * 说明: 请尽可能地优化你算法的时间和空间复杂度。
 *
 * 示例 1:
 * 输入:
 * nums1 = [3, 4, 6, 5]
 * nums2 = [9, 1, 2, 5, 8, 3]
 * k = 5
 * 输出:
 * [9, 8, 6, 5, 3]
 * 示例 2:
 *
 * 输入:
 * nums1 = [6, 7]
 * nums2 = [6, 0, 4]
 * k = 5
 * 输出:
 * [6, 7, 6, 0, 4]
 * 示例 3:
 *
 * 输入:
 * nums1 = [3, 9]
 * nums2 = [8, 9]
 * k = 3
 * 输出:
 * [9, 8, 9]
 *
 * @Author ming
 * @time 2020/3/26 17:12
 */
public class Solution321 {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int[] ans = new int[k];

        for(int i=Math.max(0,k-n);i<=Math.min(k,m);i++){
            int[] seq1 = maxSubSequence(nums1,i);    //子序列1
            int[] seq2 = maxSubSequence(nums2,k-i);  //子序列2
            int[] temp = merge(seq1,seq2);           //归并
            if(compare(temp,ans)){                   //比较逻辑大小
                for(int j=0;j<k;j++){
                    ans[j] = temp[j];
                }
            }
        }
        return ans;
    }

    //求数组中k个顺序不变的最大子序列
    private int[] maxSubSequence(int[] nums, int k){
        int l = nums.length;
        if(l<=k) return nums;

        //代表最多可以丢弃几个数
        int drop = l-k;

        int[] ans = new int[k];
        if(k==0) return ans;
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<l;i++){
            while(!stack.empty() && nums[i]>stack.peek() && drop-->0){
                stack.pop();
            }
            stack.push(nums[i]);

        }
        //裁剪大小
        while(stack.size()>k) stack.pop();

        for(int i=k-1;i>=0;i--){
            ans[i] = stack.pop();
        }
        return ans;
    }

    //归并数组
    //这里遇到了好多坑，一开始是想着按照归并排序那种方式归并的，结果发现在遇到连续几位数字相同的情况下时会出现问题
    private int[] merge(int[] nums1, int[] nums2){
        int l1 = nums1.length;
        int l2 = nums2.length;
        if(l1==0) return nums2;
        if(l2==0) return nums1;
        int ans[] = new int[l1+l2];
        int i1 = 0;
        int i2 = 0;
        for(int i=0;i<l1+l2;i++){
            if(compare(Arrays.copyOfRange(nums1, i1, l1),Arrays.copyOfRange(nums2, i2, l2))){
                ans[i] = nums1[i1++];
            }
            else{
                ans[i] = nums2[i2++];
            }
        }
        return ans;
    }

    //比较数组的逻辑大小，如果数组长短不一样并且前n个数字完全一样，则认为长度大的那个数组大
    //返回值：若nums1>nums2，返回true
    private boolean compare(int[] nums1, int[] nums2){
        int n = Math.min(nums1.length,nums2.length);
        for(int i=0;i<n;i++){
            if(nums1[i]>nums2[i]) return true;
            else if(nums1[i]<nums2[i]) return false;
            else continue;
        }
        return nums1.length>nums2.length;
    }

}
