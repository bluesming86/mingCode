package com.ming.learning.other;

import java.util.Arrays;
import java.util.Random;

/**
 * 随机工具类
 *
 * @Author ming
 * @time 2020/7/20 11:09
 */
public class RandomTools {

    private static final Random RANDOM = new Random();


    public static void main(String[] args) {
//        test1();
//        System.out.println(randomNumberCode());
//        System.out.println(randomNumberCode(6));
//        System.out.println(randomNumberCode(8));
//        System.out.println(randomNumberCode(10));
        test2();
    }

    public static void test2() {
        String[] day = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};
        String[] month = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        shuffleArray(day);
        shuffleArray(month);


        for (int i = 0; i < day.length; i++) {
            System.out.println(day[i]);
        }
        for (int i = 0; i < month.length; i++) {
            System.out.println(month[i]);
        }

    }

    //
    public static void test1() {
        int i = 0;
        while (i++ != 100) {
            System.out.print(nextInt(1, 2));
            if (i % 30 == 0) {
                System.out.println("");
            }
        }
    }


    /**
     * 从start 到 end 的随机数， 包含 start 跟 end
     *
     * @param start 开始
     * @param end   结束
     * @return
     */
    public static int nextInt(int start, int end) {
        isTrue(end >= start, "Start value must be smaller or equal to end value.", new Object[0]);
        isTrue(end >= 0, "Both range values must be non-negative.", new Object[0]);

        return start == end ? start : start + RANDOM.nextInt(end - start + 1);
    }

    public static String randomNumberCode() {
        return randomNumberCode(6);
    }

    /**
     * 随机数字验证码
     *
     * @param num 随机数量
     * @return
     */
    public static String randomNumberCode(int num) {
        isTrue(num >= 0, "Both range values must be non-negative.", new Object[0]);

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(nextInt(0, 9));
            num--;
        }
        return sb.toString();
    }

    public static void isTrue(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    /**
     * 思路： 获取start 到 end 的 随机数 。
     * 然后 把start 下表的数据组值 交换到随机数下标，
     * 然后 start ++
     * 一路打乱到 start = end
     *
     * @param arr
     * @return
     */
    public static Object[] shuffleArray(Object[] arr) {
        if (arr == null || arr.length < 1) return arr;

        Object object = new Object();
        int endIndex = arr.length - 1;
        for (int i = 0; i < arr.length; i++) {
            int randomIndex = nextInt(i, endIndex);

            //交换
            object = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = object;
        }
        return arr;
    }
}
