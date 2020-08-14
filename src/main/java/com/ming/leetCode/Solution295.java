package com.ming.leetCode;

import java.util.List;
import java.util.PriorityQueue;

/**
 * 295 数据流的中位数
 *
 *      中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 *
 *      例如 【2,3,4】 的中位数 是3
 *          【2,3】 的中位数是（2+3）/2 = 2.5
 *
 *      设计一个支持 以下两种操作的数据结构：
 *         。void addNum(int num)  从数据流中添加一个整数到数据结构中
 *         。double findMedian() 返回目前所有元素的中位数
 *
 *      示例 ：
 *         addNum(1)
 *         addNum(2)
 *         findMedian() -> 1.5
 *         addNum(3)
 *         findMedian() -> 2
 *
 *
 * @Author ming
 * @time 2020/3/21 9:18
 */
public class Solution295 {
    class MedianFinder {

        private int count;//存放元素总个数
        private PriorityQueue<Integer> maxHeap;//大的有序队列
        private PriorityQueue<Integer> minHeap;//小的有序队列

        /** initialize your data structure here. */
        public MedianFinder() {
            count = 0;
            maxHeap = new PriorityQueue<>((x,y) -> y-x);//按倒序排序，
            minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            count++;
            maxHeap.offer(num);
            minHeap.add(maxHeap.poll());

            //如果两个堆合起来的元素个数是奇数，小顶堆 要拿出 堆顶元素给大顶堆
            if ((count&1) != 0){
                maxHeap.add(minHeap.poll());
            }
        }

        public double findMedian() {
            if ((count & 1) == 0){
                //如果两个堆合起来的元素个数是偶数，数据流的中位数就是各自堆顶元素的平均值
                return (double)(maxHeap.peek() + minHeap.peek())/2;
            } else {
                //如果两个堆合起来的元素个数是奇数，数据流的中位数大顶堆的堆顶元素
                return (double)maxHeap.peek();
            }

        }
    }



}
