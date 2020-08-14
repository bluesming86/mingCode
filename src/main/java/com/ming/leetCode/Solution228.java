package com.ming.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 228 汇总区间
 *  给定一个无重复元素的有序整数数组，返回数组区间范围的汇总
 *示例 1:
 *
 * 输入: [0,1,2,4,5,7]
 * 输出: ["0->2","4->5","7"]
 * 解释: 0,1,2 可组成一个连续的区间; 4,5 可组成一个连续的区间。
 * 示例 2:
 *
 * 输入: [0,2,3,4,6,8,9]
 * 输出: ["0","2->4","6","8->9"]
 * 解释: 2,3,4 可组成一个连续的区间; 8,9 可组成一个连续的区间。
 *
 * @Author ming
 * @time 2020/3/13 20:40
 */
public class Solution228 {

    /**
     * 自己写的
     * @param nums
     * @return
     */
    public static List<String> summaryRanges(int[] nums) {

        List<String> resultList = new ArrayList<>();

        Integer startIndex =null;
        Integer endIndex = null;
        Integer preNum = null;

        for (int i = 0; i < nums.length; i++) {
            if (preNum == null){
                //重新开始
                startIndex =i;
                endIndex = i;
                preNum = nums[i];
            }else if  (preNum + 1 == nums[i]){
                endIndex = i;
                preNum = nums[i];
            } else {
                //不是顺序的
                if (startIndex == endIndex){//就一个
                    resultList.add(""+nums[startIndex]);
                } else{//多个
                    resultList.add(nums[startIndex] + "->" + nums[endIndex]);
                }
                startIndex = null;//标识这组结束
                endIndex = null;//标识这组结束
                preNum = null;//标识这组结束
                i--;//不属于这组，从这个开始
            }
        }
        if (startIndex != null && endIndex!= null){
            if (startIndex == endIndex){//就一个
                resultList.add(""+nums[startIndex]);
            } else{//多个
                resultList.add(nums[startIndex] + "->" + nums[endIndex]);
            }
        }

        return resultList;
    }

    /**
     * 官方题解
     * @param nums
     * @return
     */
    public List<String> summaryRanges2(int[] nums) {
        List<String> summary = new ArrayList<>();
        for (int i = 0,j=0; j< nums.length; ++j){
            if (j + 1< nums.length && nums[j+1] == nums[j] + 1){
                continue;
            }

            if (i == j){
                summary.add(nums[i] + "");
            } else {
                summary.add(nums[i] + "->" +nums[j]);
            }
            i = j+1;
        }
        return summary;
    }
    /**
     * 官方题解2
     * @param nums
     * @return
     */
    public List<String> summaryRanges3(int[] nums) {
        List<String> summary = new ArrayList<>();
        for (int i = 0,j=0; j< nums.length; ++j){
            i = j;

            while (j + 1< nums.length && nums[j+1] == nums[j] + 1){
                ++j;
            }

            if (i == j){
                summary.add(nums[i] + "");
            } else {
                summary.add(nums[i] + "->" +nums[j]);
            }
        }
        return summary;
    }
}
