package com.ming.leetCode;

/**
 * 318 最大单词长度乘积
 *   给定一个字符串数组words, 找到length(words[i]) * length(word[j])的最大值，
 *   并且这两个单词不包含有公共字母。你可以认为每个单词只包含 小写字母。如果不存在着样的两个单词，
 *   返回0；
 *
 *示例 1:
 *
 * 输入: ["abcw","baz","foo","bar","xtfn","abcdef"]
 * 输出: 16
 * 解释: 这两个单词为 "abcw", "xtfn"。
 * 示例 2:
 *
 * 输入: ["a","ab","abc","d","cd","bcd","abcd"]
 * 输出: 4
 * 解释: 这两个单词为 "ab", "cd"。
 * 示例 3:
 *
 * 输入: ["a","learning","aaa","aaaa"]
 * 输出: 0
 * 解释: 不存在这样的两个单词。
 *
 * @Author ming
 * @time 2020/3/25 10:08
 */
public class Solution318 {
    //暴力法
    public int maxProduct(String[] words) {
        int maxLength = 0;
        for (int i=0 ;i< words.length; i++){

            String str = words[i];
            for (int j=i+1;j<words.length; j++){
                boolean flag = true;
                //判断是否存在同样的字母
                for (int k =0;k<str.length(); k++){
                    if (words[j].contains(str.charAt(k)+"")){
                        flag = false;
                        break;
                    }
                }

                if (flag == true){
                    //不包含公共的字母，符合条件
                    maxLength = Math.max(maxLength, str.length() * words[j].length());
                }
            }

        }

        return maxLength;
    }

    /**
     * 用二进制的一位表示某一个字母是否出现过，0表示没出现，1表示出现。
     * "abcd"二进制表示00000000 00000000 00000000 00001111、
     *   "bc"二进制表示00000000 00000000 00000000 00000110。当两个字符串没有相同的字母时，二进制数与的结果为0。
     *
     * @param words
     * @return
     */
    public int maxProduct2(String[] words) {
        int wlength = words.length;
        int[] arr = new int[wlength];
        for(int i = 0; i < wlength; ++i){
            int length = words[i].length();
            for(int j = 0; j < length; ++j){
                arr[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }
        int ans = 0;
        for(int i = 0; i < wlength; ++i){
            for(int j = i + 1; j < wlength; ++j){
                if((arr[i] & arr[j]) == 0){
                    int k = words[i].length() * words[j].length();
                    ans = ans < k ? k : ans;
                }
            }
        }
        return ans;
    }

}
