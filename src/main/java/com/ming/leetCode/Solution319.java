package com.ming.leetCode;

/**
 * 初始时有 n 个灯泡关闭。 第 1 轮，你打开所有的灯泡。 第 2 轮，每两个灯泡你关闭一次。 第 3 轮，每三个灯泡切换一次开关（如果关闭则开启，如果开启则关闭）。第 i 轮，每 i 个灯泡切换一次开关。 对于第 n 轮，你只切换最后一个灯泡的开关。 找出 n 轮后有多少个亮着的灯泡。
 *
 * 示例:
 *
 * 输入: 3
 * 输出: 1
 * 解释:
 * 初始时, 灯泡状态 [关闭, 关闭, 关闭].
 * 第一轮后, 灯泡状态 [开启, 开启, 开启].
 * 第二轮后, 灯泡状态 [开启, 关闭, 开启].
 * 第三轮后, 灯泡状态 [开启, 关闭, 关闭].
 *
 * 你应该返回 1，因为只有一个灯泡还亮着。
 *
 * @Author ming
 * @time 2020/3/25 14:49
 */
public class Solution319 {

    /**
     * 自己写 。。超时了
     *
     * @param n
     * @return
     */
    public int bulbSwitch(int n) {

        int[] light = new int[n+1];//值是0时表示是灯亮，是1时表示灯灭
        int lightNum = n;
        for (int i = 2; i<=n ;i++){
            int j = 1;
            while (i*j<=n){
                if (light[i*j] == 0){
                    light[i*j] =1;
                    lightNum--;
                }else {
                    light[i*j] =0;
                    lightNum++;
                }
                j++;
            }

        }
        return lightNum;
    }


    /**
     * ...????????
     *
     * 我刚开始是使用数组做的，超出了内存的限制，后来看了deen大佬的题解，这里只是做一个总结
     * 对于这道题，首先分析对于第i个灯泡，只有它的因子轮的操作可以改变它的状态，例如4号灯泡，就只会在1,2,4这些轮改变，5号灯泡，就只会在1,5这两轮改变，因为初始的所有的灯泡的状态都为关闭，所以如果一个灯泡含有奇数个因子，那么在这些操作之后他应该是开启的，相对的，如果是偶数个因子，那就是关闭的，所以问题此时转化为了看1-n的所有的灯泡有几个因子
     * 然后去分析每个数的因子，看它们因子的个数，但是这里有一个可以优化的点就是对于一个数例如 a=b*c,b和c一定是在根号a的两端或者就等于根号a，那么统计它们的因子的个数只需要让j从1到根号a，如果灯泡序号可以整除这个j，那么因子数加2,当然如果两个因子数一样（也就是这个数字可以开平方）就加1，由此可以总结出来的一个规律是对于能开平方的数字，它的因子数一定是一个奇数，而对于不能开平方数字，它的因子数为一个偶数，所以又将问题转化为找出1-n中可以开平方的数
     * 对于1-n中的可以开平方的数字，例如36，它里面可以开平方的数字1*1，2*2...6*6，对于100，它里面可以开平方的数字，1*1,...10*10,可以发现，对于一个数，它里面可以开平方的数字的个数就是它自己的开平方数，所以这道题的最终的解法就变为了直接求n的开平方数就是最后的结果
     *
     *
     * @param n
     * @return
     */
    public int bulbSwitch2(int n) {
        return (int)Math.sqrt(n);
    }
}
