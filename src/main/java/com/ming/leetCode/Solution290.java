package com.ming.leetCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 290 单词规律
 *  给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
 *
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
 *
 * 示例1:
 *
 * 输入: pattern = "abba", str = "dog cat cat dog"
 * 输出: true
 * 示例 2:
 *
 * 输入:pattern = "abba", str = "dog cat cat fish"
 * 输出: false
 * 示例 3:
 *
 * 输入: pattern = "aaaa", str = "dog cat cat dog"
 * 输出: false
 * 示例 4:
 *
 * 输入: pattern = "abba", str = "dog dog dog dog"
 * 输出: false
 * 说明:
 * 你可以假设 pattern 只包含小写字母， str 包含了由单个空格分隔的小写字母。
 * @Author ming
 * @time 2020/3/20 20:19
 */
public class Solution290 {
    public boolean wordPattern(String pattern, String str) {


        String[] strArr = str.split(" ");

        if (pattern.length() != strArr.length) return false;
        Map< Character,String> map = new HashMap<>();
        Map< String,Character> map2 = new HashMap<>();

        for (int i=0;i<strArr.length;i++){
            if (map.containsKey(pattern.charAt(i)) && !map2.containsKey(strArr[i])){
                return false;
            } if(map2.containsKey(strArr[i]) && !map.containsKey(pattern.charAt(i))){
                return false;
            } else if(map.containsKey(pattern.charAt(i)) && map2.containsKey(strArr[i])
                    && (!map.get(pattern.charAt(i)).equals(strArr[i] ) || map2.get(strArr[i]) != pattern.charAt(i)) ){
                return false;
            }else{
                map.put(pattern.charAt(i), strArr[i]);
                map2.put(strArr[i], pattern.charAt(i));
            }
        }

        return true;
    }

    public boolean wordPattern2(String pattern, String str) {
        String[] words = str.split(" ");
        if (words.length != pattern.length()) return false;
        Map<Object, Integer> mem = new HashMap<>();
        for (int i = 0; i < words.length; i++)
            if (!Objects.equals(mem.put(words[i], i), mem.put(pattern.charAt(i), i))) return false;
        return true;
    }
}
