package com.ming.leetCode;

/**
 * 300 最长上升子序列
 *      给定一个无序的整数数组，找到其中最长上升子序列的长度
 *
 *      示例:
 *
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 * 说明:
 *
 * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
 * 你算法的时间复杂度应该为 O(n^2) 。
 * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
 * @Author ming
 * @time 2020/3/21 10:52
 */
public class Solution300 {

    /**
     * 动态规划
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];//用来记录 对应的数的最长子序列长度
        dp[0] = 1;
        int maxans = 1; //记录最长子序列长度
        for (int i = 1; i < dp.length; i++) {
            int maxval = 0;
            for (int j = 0; j < i; j++) {//找出 从下标0 开始到 这个数 最长子序列长度
                if (nums[i] > nums[j]) {
                    maxval = Math.max(maxval, dp[j]);//取 之前计算过的最长子序列长度，那么 +1 就是 当前 i 所指的最长子序列长度
                }
            }
            dp[i] = maxval + 1;
            maxans = Math.max(maxans, dp[i]);
        }
        return maxans;
    }

    /**\
     * 解题思路：
     * 降低复杂度切入点： 解法一中，遍历计算 dpdp 列表需 O(N)O(N)，计算每个 dp[k]dp[k] 需 O(N)O(N)。
     *
     * 动态规划中，通过线性遍历来计算 dpdp 的复杂度无法降低；
     * 每轮计算中，需要通过线性遍历 [0,k)[0,k) 区间元素来得到 dp[k]dp[k] 。我们考虑：是否可以通过重新设计状态定义，使整个 dpdp 为一个排序列表；这样在计算每个 dp[k]dp[k] 时，就可以通过二分法遍历 [0,k)[0,k) 区间元素，将此部分复杂度由 O(N)O(N) 降至 O(logN)O(logN)。
     * 设计思路：
     *
     * 新的状态定义：
     * 我们考虑维护一个列表 tailstails，其中每个元素 tails[k]tails[k] 的值代表 长度为 k+1k+1 的子序列尾部元素的值。
     * 如 [1,4,6][1,4,6] 序列，长度为 1,2,31,2,3 的子序列尾部元素值分别为 tails = [1,4,6]tails=[1,4,6]。
     * 状态转移设计：
     * 设常量数字 NN，和随机数字 xx，我们可以容易推出：当 NN 越小时，N<xN<x 的几率越大。例如： N=0N=0 肯定比 N=1000N=1000 更可能满足 N<xN<x。
     * 在遍历计算每个 tails[k]tails[k]，不断更新长度为 [1,k][1,k] 的子序列尾部元素值，始终保持每个尾部元素值最小 （例如 [1,5,3]][1,5,3]]， 遍历到元素 55 时，长度为 22 的子序列尾部元素值为 55；当遍历到元素 33 时，尾部元素值应更新至 33，因为 33 遇到比它大的数字的几率更大）。
     * tailstails 列表一定是严格递增的： 即当尽可能使每个子序列尾部元素值最小的前提下，子序列越长，其序列尾部元素值一定更大。
     * 反证法证明： 当 k < ik<i，若 tails[k] >= tails[i]tails[k]>=tails[i]，代表较短子序列的尾部元素的值 >> 较长子序列的尾部元素的值。这是不可能的，因为从长度为 ii 的子序列尾部倒序删除 i-1i−1 个元素，剩下的为长度为 kk 的子序列，设此序列尾部元素值为 vv，则一定有 v<tails[i]v<tails[i] （即长度为 kk 的子序列尾部元素值一定更小）， 这和 tails[k]>=tails[i]tails[k]>=tails[i] 矛盾。
     * 既然严格递增，每轮计算 tails[k]tails[k] 时就可以使用二分法查找需要更新的尾部元素值的对应索引 ii。
     * 算法流程：
     *
     * 状态定义：
     *
     * tails[k]tails[k] 的值代表 长度为 k+1k+1 子序列 的尾部元素值。
     * 转移方程： 设 resres 为 tailstails 当前长度，代表直到当前的最长上升子序列长度。设 j∈[0,res)j∈[0,res)，考虑每轮遍历 nums[k]nums[k] 时，通过二分法遍历 [0,res)[0,res) 列表区间，找出 nums[k]nums[k] 的大小分界点，会出现两种情况：
     *
     * 区间中存在 tails[i] > nums[k]tails[i]>nums[k] ： 将第一个满足 tails[i] > nums[k]tails[i]>nums[k] 执行 tails[i] = nums[k]tails[i]=nums[k] ；因为更小的 nums[k]nums[k] 后更可能接一个比它大的数字（前面分析过）。
     * 区间中不存在 tails[i] > nums[k]tails[i]>nums[k] ： 意味着 nums[k]nums[k] 可以接在前面所有长度的子序列之后，因此肯定是接到最长的后面（长度为 resres ），新子序列长度为 res + 1res+1。
     * 初始状态：
     *
     * 令 tailstails 列表所有值 =0=0。
     * 返回值：
     *
     * 返回 resres ，即最长上升子子序列长度。
     * 复杂度分析：
     * 时间复杂度 O(NlogN)O(NlogN) ： 遍历 numsnums 列表需 O(N)O(N)，在每个 nums[i]nums[i] 二分法需 O(logN)O(logN)。
     * 空间复杂度 O(N)O(N) ： tailstails 列表占用线性大小额外空间
     * @param nums
     * @return
     */
    public int lengthOfLIS2(int[] nums) {
        int maxStep = 1,length= nums.length;
        if (length == 0) return 0;

        int[] dp = new int[length+1];
        dp[maxStep] = nums[0];

        for (int i = 1; i < length; i++){
            if (nums[i] > dp[maxStep]){
                dp[++maxStep] = nums[i];
            } else {
                int j = 1, r = maxStep, pos =0;// 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0

                while( j <= r){
                    int mid = (j + r) >> 1;
                    if (dp[mid] < nums[i]){
                        pos = mid;
                        j = mid +1;
                    } else {
                        r = mid -1;
                    }
                }

                dp[pos + 1] = nums[i];
            }
        }
        return maxStep;
    }
}
