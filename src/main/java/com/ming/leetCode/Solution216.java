package com.ming.leetCode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 216 组合总和III
 *   找出所有相加之和为 n 的k个数的组合。组合中只允许含有1-9的正整数，
 *   并且每种组合中不存在的重复的数字。
 *
 *   说明 ：
 *      所有数字都是正整数
 *      解集不能包含重复的组合
 *
 * @Author ming
 * @time 2020/2/26 11:08
 */
public class Solution216 {

    /**
     * 回溯问题，细节很多，但是也有一点套路
     *    1.先画出递归树，帮助分析
     *    2.然后使用深度优先遍历，搜索可能的解
     *    3.注意状态重置（恢复现场，以免出错） 和剪枝（避免不必要的搜索消耗时间）。
     *
     *    分析
     *    1.做减法 n 减到0 时 判断 个数 是否等于 k,如果 不等 ，就剪掉
     *       如果 减时小于0 时，也剪掉。
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {

        List<List<Integer>> resultList = new ArrayList<>();
        // 一开始做一些特殊判断
        if (k <= 0 || n <= 0 || k >= n) {
            return resultList;
        }

        // 寻找 n 的上限：[9, 8, ... , (9 - k + 1)]，它们的和为 (19 - k) * k / 2
        // 比上限还大，就不用搜索了：
        if (n > (19 - k) * k / 2) {
            return resultList;
        }

        //根据官方对 Stack的使用建议，这里将 Deque对象当做stack使用
        //注意只使用关于栈的接口
        Deque<Integer> path = new ArrayDeque<>();
        dfs(k, n, 1, path, resultList);

        return resultList;
    }

    /**
     * 回溯
     * @param k         剩下要找k 个数
     * @param residue   剩余多少
     * @param start     下一轮搜索的起始元素是多少
     * @param path      深度优先遍历的路径参数（状态变量）
     * @param res       保存结果集的列表
     */
    private void dfs(int k, int residue, int start, Deque<Integer> path, List<List<Integer>> res){
        //这一步判断可以放到上一层，减少递归深度
        if (residue < 0){
            return;
        }
        if (k == 0){   //剩余数 等于0 ，准备结束这条分支
            if (residue == 0){//判断 结束这条分支 是否符合条件，
                res.add(new ArrayList<>(path));//符合条件，添加这条路径，栈
                return;
            }
            //不符合条件，直接返回
            return;
        }

        for (int i= start; i<=9; i++){
            path.addLast(i);//添加这个数
            dfs(k-1, residue-i,i+1, path, res);//分支判断剩余的数
            path.removeLast(); //删掉这个数，判断下一个分支
        }
    }

    /**
     * 回溯 + 剪枝
     * @param k         剩下要找k 个数
     * @param residue   剩余多少
     * @param start     下一轮搜索的起始元素是多少
     * @param path      深度优先遍历的路径参数（状态变量）
     * @param res       保存结果集的列表
     */
    private void dfs2(int k, int residue, int start, Deque<Integer> path, List<List<Integer>> res){
        // 剪枝：[start, 9] 这个区间里的数都不够 k 个，不用继续往下搜索
        if (10 - start < k){
            return;
        }
        if (k == 0){   //剩余数 等于0 ，准备结束这条分支
            if (residue == 0){//判断 结束这条分支 是否符合条件，
                res.add(new ArrayList<>(path));//符合条件，添加这条路径，栈
                return;
            }
            //不符合条件，直接返回
            return;
        }

        // 枚举起点值 [..., 7, 8, 9]
        // 找 3 个数，起点最多到 7
        // 找 2 个数，起点最多到 8
        // 规律是，起点上界 + k = 10，故起点上界 = 10 - k
        for (int i = start; i <= 10 - k; i++) {
            // 剪枝
            if (residue - i < 0) {
                break;
            }
            path.addLast(i);
            dfs(k - 1, residue - i, i + 1, path, res);
            path.removeLast();
        }
    }
}
