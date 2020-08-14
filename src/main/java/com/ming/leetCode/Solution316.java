package com.ming.leetCode;

import java.util.Stack;

/**
 * 316 去除重复字母
 *      给你一个仅包含小写字母的字符串，请你去除字符串中重复的字母，
 *      使得每个字母只出现一次。需要保证返回结果的字典序最小（要求不能打乱其他字符的相对位置）
 *
 *      示例1： 输入"bcabc" 输出 "abc"
 *      示例2： 输入"cbacdcbc" 输出“acdb”
 * @Author ming
 * @time 2020/3/24 15:24
 */
public class Solution316 {

    /**
     * 使用栈 处理
     * @param s
     * @return
     */
    public String removeDuplicateLetters(String s) {

        Stack<Character>  stack = new Stack<>();

        for (int i=0;i<s.length();i++){
            Character c = s.charAt(i);
            if (stack.contains(c)) continue;

            while(!stack.isEmpty() && stack.peek() > c && s.indexOf(stack.peek(), i) != -1){
                //栈不为空， 栈顶 大于当前元素 且 后面的元素还有栈顶元素，那么 留一且字典序最小规则，栈顶元素要出栈
                stack.pop();
            }

            //把当前元素压入栈
            stack.push(c);
        }

        char[] res = new char[stack.size()];
        for (int i =0;i < stack.size(); i++){
            res[i] = stack.get(i);
        }
        return new String(res);

    }
}
