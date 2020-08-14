package com.ming.leetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 242 有效的字母异位词
 * 示例 1 ：
 *      输入 s = "anagram" , t = "nagaram"
 *      输出 true
 * 示例2 ：
 *      输入 s ="rat" , t = "car"
 *      输出:  false
 * @Author ming
 * @time 2020/3/19 17:34
 */
public class Solution242 {

    /**
     * 方法一 排序法
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }

    /**
     * 哈希表法
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram2(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }
        int[] table = new int[26];
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            table[t.charAt(i) - 'a']--;
            if (table[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
