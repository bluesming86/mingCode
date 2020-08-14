package com.ming.leetCode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 299 猜数字游戏
 * 你正在和你的朋友玩 猜数字（Bulls and Cows）游戏：你写下一个数字让你的朋友猜。每次他猜测后，你给他一个提示，告诉他有多少位数字和确切位置都猜对了（称为“Bulls”, 公牛），有多少位数字猜对了但是位置不对（称为“Cows”, 奶牛）。你的朋友将会根据提示继续猜，直到猜出秘密数字。
 *
 * 请写出一个根据秘密数字和朋友的猜测数返回提示的函数，用 A 表示公牛，用 B 表示奶牛。
 *
 * 请注意秘密数字和朋友的猜测数都可能含有重复数字。
 *
 * 示例 1:
 *
 * 输入: secret = "1807", guess = "7810"
 *
 * 输出: "1A3B"
 *
 * 解释: 1 公牛和 3 奶牛。公牛是 8，奶牛是 0, 1 和 7。
 * 示例 2:
 *
 * 输入: secret = "1123", guess = "0111"
 *
 * 输出: "1A1B"
 *
 * 解释: 朋友猜测数中的第一个 1 是公牛，第二个或第三个 1 可被视为奶牛。
 * 说明: 你可以假设秘密数字和朋友的猜测数都只包含数字，并且它们的长度永远相等。
 * @Author ming
 * @time 2020/3/21 10:17
 */
public class Solution299 {
    public String getHint(String secret, String guess) {

        int bulls = 0;//公牛 位置跟数字都正确
        int cows = 0;//奶牛，数字正确，位置不正确。

        StringBuffer secretBuffer = new StringBuffer(secret);//用来存放
        StringBuffer guessBuffer = new StringBuffer(guess);//
        //先找到公牛，并把其在答案字符串 跟 猜测字符串中去除
        for(int i=0; i<guess.length(); i++){
            if(guess.charAt(i) == secret.charAt(i) ){//一样是公牛，就把 这个字母给 去掉用“_” 替换
                bulls++;
                secretBuffer.replace(i,i+1,"_");
                guessBuffer.replace(i,i+1,"_");
            }
        }
        //找奶牛。找到一只。同理，把答案中的对应位置 去掉，这样就不会重复确认
        for(int i=0;i<guessBuffer.length(); i++ ){ //
            if(guessBuffer.charAt(i) == '_') {
                continue;
            }

            int k = secretBuffer.indexOf(guessBuffer.charAt(i)+"");
            if(k >=0){
                cows++;
                secretBuffer.replace(k,k+1,"_");
            }
        }

        return bulls + "A" + cows + "B";
    }


    /*
       Time complexity: O(n)
       Space complexity: O(n)
    */
    public String getHint2(String secret, String guess) {
        int aCount = 0;
        int bCount = 0;

        ArrayList<Character> list = new ArrayList<>();//存放 猜测串中 除了公牛外的 其他数字

        HashMap<Character, Integer> map = new HashMap<>();//存放 答案串中 除了公牛外的 其他数字map,对应的个数
        for (int i = 0; i < secret.length(); i++) {
            char temp = secret.charAt(i);
            if (temp == guess.charAt(i)){
                aCount++;
            } else {
                list.add(guess.charAt(i));
                if (map.containsKey(temp)) {
                    map.put(temp, map.get(temp) + 1);
                }else {
                    map.put(temp, 1);
                }
            }
        }
        for (Character c : list) {//遍历 猜测的其他值
            if (map.containsKey(c)) {// 在答案中 能找到
                bCount++; //奶牛+1
                map.put(c, map.get(c) - 1); //答案中 减少一个，
                if (map.get(c) == 0) //当这个数，减到0了，就删除 这个key
                    map.remove(c);
            }
        }
        return aCount + "A" + bCount + "B";
    }

    /*
       Time complexity: O(n)
       Space complexity: O(1)
    */
    public String getHint3(String secret, String guess) {
        int aCount = 0;     // 公牛数
        int bCount = 0;     // 母牛数
        int[] mapS = new int[10];//记录secret其他数字出现的次数 0-9
        int[] mapG = new int[10];//记录guess其他数字出现的次数 0-9

        for (int i = 0; i < secret.length(); i++) {
            char temp = secret.charAt(i);
            if (temp == guess.charAt(i))
                aCount++;
            else {
                mapS[temp - '0']++;
                mapG[guess.charAt(i) - '0']++;
            }
        }
        //0-9 各个数字的个数。对比 取最小值，就是 奶牛个数。
        for (int i = 0; i < 10; i++) {
            bCount += Math.min(mapG[i], mapS[i]);
        }
        return aCount + "A" + bCount + "B";
    }
}
