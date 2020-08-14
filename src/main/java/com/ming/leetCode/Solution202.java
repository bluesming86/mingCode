package com.ming.leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * 202 快乐数
 * 一个 快乐数 定义为：对于一个正整数，每一次将改数替换为它每个位置上的数字的平方和。
 * 然后重复这个过程直到这个数变为1，也可能是无限循环但始终变不到1.
 * 如果可以变为1，那么这个数就是快乐数。
 * @Author ming
 * @time 2020/2/22 10:05
 */
public class Solution202 {

    /**
     * 方法一 使用 集合记录出现过的数
     *  若已经出过了，那么判定为循环
     *
     *   存在问题，当数过大时，集合有可能撑爆，占用很大内存
     * @param n
     * @return
     */
    public static boolean isHappy(int n){
        Set<Integer> res = new HashSet<Integer>();
        res.add(0);
        res.add(1);
        while( !res.contains(n) ){
            res.add(n);
            n = bitSquareSum(n);
        }
        if( n == 1){
            return true;
        }
        System.out.println(res);
        return false;
    }

    public static int bitSquareSum(int n){
        int sum = 0;
        while ( n != 0){
            sum += (n % 10)*(n % 10);
            n = n / 10;
        }
        return sum;
    }

    /**
     * 方法2  使用快慢指针 判定循环
     *   快指针 每次走两步， 慢指针 每次走一步。
     *   当二者相等时，即为一个循环周期
     *   此时，判断是不是因为1引起的循环，是的话，就是快乐数，否者不是快乐数
     *
     * @param n
     * @return
     */
    public static boolean isHappy2(int n){
        int slow=n,fast= n;
        do{
            slow = bitSquareSum(slow);

            fast = bitSquareSum(fast);
            fast = bitSquareSum(fast);
        } while ( slow != fast);

        return slow == 1;
    }
    public static void main(String[] args) {
        System.out.println(isHappy(2));
    }

}
