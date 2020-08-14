package com.ming.leetCode;

/**
 * 306 累加数
 *      累加数是一个字符串，组成它的数字可以形成累加序列。
 *
 *      一个有效的累加序列必须至少包含3个数。除最开始的两个数以外，字符串中的其他数都等于它之前的两个数相加的和。
 *
 *      给定一个只包含数字 0 - 9 的字符串，编写一个算法来判断给定输入是否是累加数。
 *
 *      说明： 累加序列里的数 不会以0 开头，所以不会出现 1,2,03 或者 1,02,3 的情况
 *
 *      示例1： 输入 “112358”
 *              输出 true
 *              解释  累加序列为 1,1,2,3,5,8
 *                             1+1=2,1+2=3,2+3=5,3+5=8
 *
 *      示例2   输入“199100199”
 *              输出 true
 *              解释 累加序列为  1,99,100，199
 *                  1+99=100， 99+100=199
 * @Author ming
 * @time 2020/3/21 15:04
 */
public class Solution306 {

    public boolean isAdditiveNumber(String num) {
        return backtrack(num,0,0,0,0);//第一个0：从num的0号位置开始计算
        //第二个0：前面两个数的总和初始为0
        //第三个0：前面一个数的值初始为0
        //第四个0：当前搜索的数字是第几个数（需要先找到前两个再对第三个及以后进行分析，所以需要这个k）
    }
    private boolean backtrack(String num,int index,long presum,long prenum,int k)
    {
        if(k>2&&index>=num.length()) return true;//index是当前数字的开始处
        for(int len=1;len+index<=num.length();len++) {//len是当前数字的长度
            long f=isSum(presum,num,index,index+len-1,k) ;//presum是之前二者的和，（num index，index+len-1）是当前数字
            if(f>=0){
                if (backtrack(num,index+len,f+prenum,f,k+1)) return true;
                //f>0时表示当前匹配成功进入递归，此时f变成prenum，f+prenum变成当前数的前两个数字之和（一开始的prenum就是f的前一个数）
            }
        }
        return false;
    }
    private long isSum(long sum,String num,int l,int h,int k) {
        if(num.charAt(l)=='0'&&l<h) return -1;
        long s=0;
        while(l<=h)
        {
            s=s*10+num.charAt(l)-'0';
            l++;
        }//num的从l到h的这一段字符串对应的数值
        if(k<2) return s;//前两个数直接返回isSum=1的情况
        return sum==s?s:-1;//后面就需判断二者和是否为sum（之前两个数二者的和）
    }
}