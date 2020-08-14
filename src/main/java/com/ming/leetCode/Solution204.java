package com.ming.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 204.计算质数
 *   统计所有小于 非负整数n的质数的数量
 *   示例：
 *     输入 10；
 *     输出： 4
 *     解释： 小于10的质数一共有4个， 它们是 2,3,5,7
 * @Author ming
 * @time 2020/2/22 14:08
 */
public class Solution204 {
    /**
     * 输入 499979 超时了
     *   本地执行时 耗时 8032毫秒
     * @param n
     * @return
     */
    public static int countPrimes(int n){
        List<Integer> primesList = new ArrayList<Integer>();

        for (int i = 2; i < n; i++){

            boolean primesFlag = true;
            for (int x : primesList){
                if (i % x == 0){
                    primesFlag = false;
                    break;
                }
            }
            if (primesFlag == true){
                primesList.add(i);
            }
        }
        return primesList.size();
    }

    /**
     * 厄拉多塞筛法
     *     解题思路，
     *       先干掉2的 倍数，然后干掉3的倍数，（4 是2的倍数，过滤），干掉5的倍数，（6是2和3的倍数）
     *        干掉7的倍数，就这样一路  就行了
     *
     *       输入 499979 耗时 8 毫秒
     * @param n
     * @return
     */
    public static int countPrimes2(int n){

        if (n < 2){
            return 0;//输入的数小于0  直接返回0个质数
        }

        int[] a = new int[n+1];//先标记为0 表示全部是质数， 其中 1 表示不是质数
        int count = 2; //计数器，记录 不是质数的数目 初始已经 0 跟1 是非质数，因此 初始值为2
        for (int i = 2; i < n;i++){// 已经知道 0 1 不是质数 ,从2 开始判断

            if (a[i] == 0){ //是质数
                for (int j = 2; i *j < n;j ++){ //开始去除 这个质数的倍数 的数字 并对应的位置改成 1 表示不是质数
                    if (a[j] == 1){//已经标记过了，直接判断下一
                        continue;
                    }
                    a[i * j] = 1; //标记不是质数
                    count++;//不是质数 个数加1
                }
            }
        }
        return n - count;//数
    }

    /**
     * 厄拉多塞筛法
     *     解题思路，
     *       先干掉2的 倍数，然后干掉3的倍数，（4 是2的倍数，过滤），干掉5的倍数，（6是2和3的倍数）
     *        干掉7的倍数，就这样一路  就行了
     *
     *   优化： 在去掉 时， 在后面的数，如果 还标记是质时那么他要想标成不是 质数，那么必定 开根号的值，
     *      如 输入 27
     *          那么 i = 2 过滤 掉 一批 4 6 8 10 12 14 16 18 20 22 24 26
     *          那么 i = 3 过滤 掉 一批 6 9 12 15 18 21 24 27
     *          i= 4 已经标记了 直接下一个
     *          i= 5 过滤掉 10 15 20 25
     *          i= 6 时 发现 6* 6 = 36 已经超过 输入的数，么小6的 乘积都已经过滤了 ，额 > 6的 超过的数 就可以跳出循环，全部的非质数都已经被全部标记了
     *            检验 i = 7 时  i* 2 , i* 3; i*4 ;i * 5 ; i*6 这些判定 都在上面 执行过了。i = 8 ......=27 同理。
     *          因此在跳出循环的判定 就可以改成 i*i < n 时，来减少循环
     *
     *       输入 499979 耗时 2 毫秒
     * @param n
     * @return
     */
    public static int countPrimes3(int n){

        if (n < 2){
            return 0;//输入的数小于0  直接返回0个质数
        }

        int[] a = new int[n+1];//先标记为0 表示全部是质数， 其中 1 表示不是质数
        int count = 2; //计数器，记录 不是质数的数目 初始已经 0 跟1 是非质数，因此 初始值为2
        for (int i = 2; i * i < n;i++){// 已经知道 0 1 不是质数 ,从2 开始判断

            if (a[i] == 0){ //是质数
                for (int j = i; i * j < n;j ++){ //从 i 开始循环 优化，因为在低于i 的 肯定在i-1 的判定中已经判定过了，因此直接从i开始
                    //开始去除 这个质数的倍数 的数字 并对应的位置改成 1 表示不是质数
                    if (a[i * j] == 1){//已经标记过了，直接判断下一
                        continue;
                    }
                    a[i * j] = 1; //标记不是质数
                    count++;//不是质数 个数加1
                }
            }
        }
        return n - count;//数
    }

    /**
     *  先特殊处理 0 - 3
     *   然后处理 不在6的倍数两侧的一定不是质数
     *   然后判定6的倍数的两侧数，
     * @param n
     * @return
     */
    public static int countPrimes4(int n){
        int count = 0;
        for (int i = 1; i < n ;i ++){
            if (isPrime(i)){
                count ++;
            }
        }
        return count;
    }
    public static boolean isPrime(int num){
        if (num <= 3){
            return num > 1;
        }

        //不在6的倍数两侧的一定不是质数
        if (num % 6 != 1 && num % 6 != 5){
            return false;
        }

        //判定是否是质数
        int sqrt = (int) Math.sqrt(num);
        for (int i = 5; i <= sqrt; i += 6){
            if (num % i == 0 || num % (i + 2)==0){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(countPrimes(10));
        System.out.println(countPrimes2(10));
        System.out.println(countPrimes3(10));
    }
}
