package com.ming.leetCode;

import java.util.Stack;

/**
 * 227, 基本计算器II
 *   实现一个基本的计算器来计算一个简单的字符串表达式的值。
 *   字符串表达式 仅包含非负整数， +,0,*,/四种运算符合空格。整数除法仅保留整数部分
 *
 *   示例1
 *      输入 “3+2*2”  输出7
 *      输入 “3/2”  输出1
 *      输入  “3+5 /2” 输出5
 *
 * @Author ming
 * @time 2020/3/13 17:11
 */
public class Solution227 {

    /**
     * 思路： 先把乘除法值计算出来，然后简化成加法
     * 步骤
     *      1.先跳过空格
     *      2.出现数字则记录整个数字是多少，然后根据之前的运算符决定下一步
     *          如果是加号，说明前面的运算独立于后面的运算，就可以将结果暂时放入栈中
     *          如果是减号，可以看成-tempNum,。然后把值入栈
     *          如果乘号或者除号，由于前面的运算独立于此，可以先计算 lastNum 和 tempNum 积，然后入栈
     *      3.最后将栈中的所有元素相加就是答案
     * @param s
     * @return
     */
    public int calculate(String s){

        Stack<Integer> numStack = new Stack<>();
        char lastOP = '+';//默认数字前面是+加号
        char[] arr = s.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ') continue;//空格直接跳过

            if (Character.isDigit(arr[i])){
                int tempNum = arr[i] - '0';//去的ascii码 减去 0 的ascii码就是 本来的数
                while (++i < arr.length && Character.isDigit(arr[i])){//判断取的字符是否是数字，
                    tempNum = tempNum * 10+ (arr[i] - '0');
                }
                i--;//因为取到了不是数字，要后退一步。

                if (lastOP == '+') {
                    numStack.push(tempNum);
                } else if (lastOP == '-'){
                    numStack.push(-tempNum);
                }  else {
                    //取到的这个数 前面的符号 是乘法或者除法。 那么 得先跟栈里的最上面的数字 进行运算，把运算后的结果在 存入栈内。
                    numStack.push(res(lastOP, numStack.pop(), tempNum));
                }
            }else {
                lastOP = arr[i];//改变符号
            }
        }

        int ans = 0;
        for (Integer num : numStack) {//把栈内的数相加。就是结果
            ans += num;
        }
        return ans;
    }

    private int res(char op, int a, int b){
        if(op == '*') return a * b;
        else if(op == '/') return a / b;
        else if(op == '+') return a + b; //其实加减运算可以忽略
        else return a - b;
    }

}
