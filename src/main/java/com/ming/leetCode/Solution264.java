package com.ming.leetCode;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * 264抽数II
 *  编写一个程序，找出第n个丑数
 *    丑数就是只包含质因数 2,3,5的正整数。
 *
 *   示例：
 *      输入 n =10
 *      输出 12
 *      解释 1， 2，3,5,6,8,9.10，12 是前10个丑数
 *
 *    说明 1 是丑数
 *         n 不超过1690
 * @Author ming
 * @time 2020/3/20 8:59
 */
public class Solution264 {

    /**
     * 超时
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {

        int result = 1;//已知 1是丑数了，所以默认从1
        while (n != 1){
            result++;

            if (judgeUglyNumber(result)){
                n--;
            }
        }

        return result;
    }

    public boolean judgeUglyNumber(int num){

        if (num < 1) return  false;

        while (num != 1){
            if (num % 2 ==0){
              num = num / 2;
            }
            if (num % 3 == 0){
                num = num /3;
            }
            if (num % 5 == 0){
                num = num / 5;
            }
        }

        return num == 1;
    }

    /**
     * 堆方法的 类
     */
    class Ugly {
        public int[] nums = new int[1690];
        Ugly() {
            HashSet<Long> seen = new HashSet();
            PriorityQueue<Long> heap = new PriorityQueue<Long>();
            seen.add(1L);
            heap.add(1L);

            long currUgly, newUgly;
            int[] primes = new int[]{2, 3, 5};
            for(int i = 0; i < 1690; ++i) {
                currUgly = heap.poll();
                nums[i] = (int)currUgly;
                for(int j : primes) {
                    newUgly = currUgly * j;
                    if (!seen.contains(newUgly)) {
                        seen.add(newUgly);
                        heap.add(newUgly);
                    }
                }
            }
        }
    }

    /**
     * 动态规划的 类
     *  直接找到 1690 个丑数，保存
     *
     *  i2  i3  i5 的作用是记录 上一次使用对应丑数因子 2,3,5使用的下标
     *  默认的下标初始值 都是0  指向第一个数 1
     *
     *  第2丑数  i2=0 i3=0 i5=0  n = [1]
     *      min(n[i2]*2,n[i3]*3,n[i5]*5) = min(1*2,1*3,1*5) = 2
     *      因此 是i2,要++ 因此 i2 =1
     *      n=[1,2]
     *  第3丑数  i2=1 i3=0 i5=0  n=[1,2]
     *      min(n[i2]*2,n[i3]*3,n[i5]*5) = min(2*2,1*3,1*5) = 3
     *      因此 是i3++ 。即 i3 = 1;
     *      n = [1,2,3]
     *  第4丑数  i2=1 i3=1 i5=0 n=[1,2,3]
     *       min(n[i2]*2,n[i3]*3,n[i5]*5) = min(2*2,2*3,1*5) = 4
     *       因此 是i2++ 。即 i2 = 2;
     *       n = [1,2,3,4]
     *  第5丑数  i2=2 i3=1 i5=0 n=[1,2,3,4]
     *       min(n[i2]*2,n[i3]*3,n[i5]*5) = min(3*2,2*3,1*5) = 5
     *       因此 是i2++ 。即 i2 = 2;
     *       n = [1,2,3,4]
     *  依次类推
     */
    class Ugly2 {
        public int[] nums = new int[1690];
        Ugly2() {
            nums[0] = 1;//第一个丑数是 1
            int ugly, i2 = 0, i3 = 0, i5 = 0;

            for(int i = 1; i < 1690; ++i) {
                ugly = Math.min(Math.min(nums[i2] * 2, nums[i3] * 3), nums[i5] * 5);
                nums[i] = ugly;

                if (ugly == nums[i2] * 2) ++i2;
                if (ugly == nums[i3] * 3) ++i3;
                if (ugly == nums[i5] * 5) ++i5;
            }
        }
    }
}
