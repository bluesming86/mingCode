package com.ming.leetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 229.求众数 II
 *  给定一个大小为n的数组，找出其中所有出现超过【n/3】次的元素。
 *  说明： 要求算法的时间复杂度为 O（n）, 空间复杂度为O(1)
 *
 *  示例 输入：【3,2,3】 输出【3】
 *      输入【1,1,1,3,3,2,2,2】 输出【1,2】
 * @Author ming
 * @time 2020/3/14 9:22
 */
public class Solution229 {

    /**
     * 摩尔投票
     *
     *   因为 是找出【n/3】次的元素，那么 最多只有 2个。
     *   我们可以先选出两个候选人A，B，遍历数组，
     *
     *   代码三步走
     *   1，如果投给A（当前元素 等于A），则A的票数++
     *   2，如果投给B（当前元素等于B），这B的票数++
     *   3.如果A,B 都不投（即当前与A，B都不相等）。 那么检查此时A或B的票数是否减为0，如果为0，则当前元素成为新的候选人，
     *      如果A，B两人的票数都不为） ，那么 A，B两人的候选人的票数均减少一，（说明投给别人没有投给他）
     *
     * @param nums
     * @return
     */
    public List<Integer> majorityElment(int[] nums){
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length ==0){
            return res;
        }
        //定义两个候选者 和它们的票数
        int cand1=0, cand2=0;
        int cnt1 =0,cnt2=0;

        //投票过程
        for (int num : nums) {
            //如果投的是候选1，票数++
            if (num == cand1){
                cnt1++;
                continue;//下一个人
            }
            //如果投的是候选2，票数++
            if (num == cand2){
                cnt2++;
                continue;//下一个人
            }

            //两个都没投，1的票数为0，那么它成为新的候选人
            if (cnt1 == 0){
                cand1 = num;
                cnt1++;
                continue;
            }
            if (cnt2 == 0){
                cand2 = num;
                cnt2++;
                continue;
            }
            //如果cand1 和cand2的数量都不为0，那就都-1
            cnt1--;
            cnt2--;
        }
        //检查两个候选人的票符不符合
        cnt1 = cnt2 = 0;
        for (int num : nums) {
            if (num == cand1){
                cnt1++;
            } else if (num == cand2){
                cnt2++;
            }
        }
        int n = nums.length;
        if (cnt1 > n/3){
            res.add(cand1);
        }
        if (cnt2 > n/2){
            res.add(cand2);
        }
        return res;
    }

    /**
     * 自己写的
     * 使用了额外的空间，
     * 不符合 空间复杂度 O（1）
     * @param nums
     * @return
     */
    public List<Integer> majorityElement(int[] nums){

        Map<Integer, Integer> timesMap = new HashMap<>();
        List<Integer> resList = new ArrayList<>();
        int limit = nums.length /3;
        for (int num : nums) {
            int times = 1;
            if (timesMap.containsKey(num)){
                times = timesMap.get(num) +1 ;
            }
            timesMap.put(num, times);
            if (times > limit && !resList.contains(num)){
                resList.add(num);
            }
        }

        return resList;
    }
}
