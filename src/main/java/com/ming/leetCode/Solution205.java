package com.ming.leetCode;

import java.util.HashMap;

/**
 * 205 同构字符串
 *  给定两个字符串 s和t,判定它们是否是同构的。
 *  如果s中的字符可以被替换到t,那么这两个字符串是同构的。
 *  所有出现的字符串都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
 *
 *   示例1：
 *     输入 s= ssg  t=add
 *     输出 true
 *
 *    示例2
 *      输入 s = foo  t = bar
 *      输出 false
 *
 *    示例 3
 *      输入 s = papaer   t= title
 *      输出 true
 *
 *    说明：
 *       你可以假设 s 和 t 具有相同的长度
 * @Author ming
 * @time 2020/2/22 16:16
 */
public class Solution205 {

    //解法一

    /**
     *
     *  我们 可以利用一个map 来处理映射。对于 s到 t的映射，我们同时遍历 s 和t ,假设当前遇到的字符分别是 c1 和c2.
     *   如果 map[c1] 不存在，那么将 c1 映射到 c2,即map[c1] = c2;
     *   如果 map[c1] 存在，那么就判断 map[c1] 是否等于c2, 也就是验证之前的映射和当前的字母是否相同
     *
     *   对于这道题，我们只需要验证 s - > t 和 t->s 两个方向即可。如果验证一个方向是不可以的
     *   举例 ： s = ab, t=cc, 如果 单看s->t ,那么 a->c, b->c是没问题的。
     *   必须再验证 t->s ,此时 c->a,c->B ,一个字母对应了多个字母，所以不是同构的。
     *   因此要调用两次
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s , String t){
        return isIsomorphicHelper(s,t) && isIsomorphicHelper(t,s);
    }
    public boolean isIsomorphicHelper(String s , String t){

        int n = s.length();

        HashMap<Character,Character> map = new HashMap<Character,Character>();
        for (int i = 0; i < n ; i++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if (map.containsKey(c1)){
                if (map.get(c1) != c2){
                    return false;
                }
            } else {
                map.put(c1, c2);
            }
        }
        return true;
    }

    /**
     * 解法二 替换成数字。然后比较
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic2(String s , String t){
        int n = s.length();

        int[] mapS = new int[128];
        int[] mapT = new int[128];

        for (int i = 0; i < n ; i ++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            //当前的映射值是否相同
            if (mapS[c1] != mapT[c2]){
                return false;
            } else {
                //是否已经修改过，修改过就不需要再处理
                if (mapS[c1] == 0){
                    mapS[c1] = i + 1;
                    mapT[c2] = i + 1;
                }
            }
        }

        return  true;
    }
    /**
     * 对 abaddee 和 cdbccff
     *
     * a b a d d e e
     * c d b c c f f
     * ^
     *
     * 当前
     * a -> 0
     * c -> 0
     *
     * 修改映射
     * a -> 1
     * c -> 1
     *
     * a b a d d e e
     * c d b c c f f
     *   ^
     *
     * 当前
     * b -> 0
     * d -> 0
     *
     * 修改映射
     * b -> 2
     * d -> 2
     *
     *
     * a b a d d e e
     * c d b c c f f
     *     ^
     * 当前
     * a -> 1 (之前被修改过)
     * b -> 0
     *
     * 出现不一致，所以两个字符串不异构
     *
     */

}
