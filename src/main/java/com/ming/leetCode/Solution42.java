package com.ming.leetCode;

import java.util.Stack;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * 算法题42
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 示例:
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 *
 * @Author ming
 * @time 2019/10/24 11:39
 */
public class Solution42 {
    /**
     * 暴力法
     * 直观想法
     * 直接按问题描述进行。对于数组中的每个元素，我们找出下雨后水能达到的最高位置，等于两边最大高度的较小值减去当前高度的值。
     * 算法
     * <p>
     * 初始化 ans=0
     * 从左向右扫描数组：
     * 初始化 max_left=0和 max_right=0
     * 从当前元素向左扫描并更新：
     * max_left=max(max_left,height[j])
     * 从当前元素向右扫描并更新：
     * max_right=max(max_right,height[j])
     * 将min(max_left,max_right)−height[i] 累加到 ans
     * 复杂性分析
     * <p>
     * 时间复杂度： O(n^2)。数组中的每个元素都需要向左向右扫描。
     * 空间复杂度 O(1)的额外空间。
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int sum = 0;
        int size = height.length;
        //遍历每个元素
        for (int i = 0; i < size; i++) {
            int maxLeft = 0;//存放元素左边最大高度
            int maxRight = 0;//存放元素右边最大高度
            int minHeight = 0;
            //思路： 计算这个元素能存多少水，那么要找到左边最大高度 跟右边最大高度，然后取高度中小的值，减去 这个元素的高度就是能存的雨水量
            //计算左边最大高度  左边的最大高度要大于当前元素高度才算
            for (int j = i; j >= 0; j--) {
                if (height[j] > maxLeft && height[j] > height[i]) {
                    maxLeft = height[j];
                }
            }
            //计算右边最大高度 右边高度要大于当前元素高度
            for (int j = i; j < size; j++) {
                if (height[j] > maxRight && height[j] > height[i]) {
                    maxRight = height[j];
                }
            }

            //得出左右两个高度中的最小值
            minHeight = maxLeft;
            if (maxRight < minHeight) {
                minHeight = maxRight;
            }
            //如果最小高度 大于 当前元素高度 那么 高度差就是这个元素能存的雨水量
            if (minHeight > height[i]) {
                sum += minHeight - height[i];
            }
        }

        return sum;
    }

    /**
     * 方法2 动态编程
     * 直观想法
     *
     * 在暴力方法中，我们仅仅为了找到最大值每次都要向左和向右扫描一次。但是我们可以提前存储这个值。因此，可以通过动态编程解决。
     *
     * 算法
     *
     * 找到数组中从下标 i 到最左端最高的条形块高度 left_max。
     * 找到数组中从下标 i 到最右端最高的条形块高度 right_max。
     * 扫描数组 height 并更新答案：
     * 累加 min(max_left[i],max_right[i])−height[i] 到 ans 上
     *
     *
     *复杂性分析
     *
     * 时间复杂度：O(n)

     * 存储最大高度数组，需要两次遍历，每次 O(n)O
     * 最终使用存储的数据更新ans ，O(n)。
     * 空间复杂度：O(n) 额外空间。
     *
     * 和方法 1 相比使用了额外的 O(n)O 空间用来放置 left_max 和 right_max 数组。
     *
     */
    public int trap2(int[] height) {
        int ans = 0;
        if (height.length == 0) return 0;

        int size = height.length;

        int[] leftMax = new int[size];//元素对应左边的最大数组
        int[] rightMax = new int[size];//元素对应右边的最大数组

        leftMax[0] = height[0];
        for (int i = 1; i < size; i++) {
            leftMax[i] = max(height[i], leftMax[i - 1]);//跟前面一个比 取最大值
        }

        rightMax[size - 1] = height[size - 1];
        for (int i = size - 2; i >= 0; i--) {
            rightMax[i] = max(height[i], rightMax[i + 1]); //跟后面一个值比 取最大值
        }

        for (int i = 1; i < size - 1; i++) {
            ans += min(leftMax[i], rightMax[i]) - height[i];//左右中小的 减去高度就是雨水量
        }

        return ans;
    }

    /**
     * 方法3： 栈的应用
     * 直观想法
     * 我们可以不用像方法2那样存储最大高度，而是用栈来跟踪可能储水的最长的条形块。使用栈就可以在一次遍历内完成计算
     * 我们在遍历数组时维护一个栈。如果当前的条形块小于或等于栈顶的条形块，我们将条形块的索引入栈，意思是当前的条形块被
     *   栈中的前一个条形块界定。如果我们发现一个条形块长于栈顶，我们可以确定栈顶的条形块被当前条形块的栈的前一个条形块界定
     *   因此我们可以弹出栈顶元素并且累加答案到ans.
     *
     * 算法
     *   使用栈来存储条形块的索引下标
     *   遍历数组
     *      当栈非空且height[current] > height[st.top()]
     *          意味着栈中元素可以被弹出。弹出栈顶元素top.
     *          计算当前元素和栈顶元素的举例，准备进行填充操作 distance = current - st.top() -1
     *          找出界定高度  bounded_height = min(height[current],height[st.top()]) - height[stop]
     *          往答案中累加积水量 ans += distance x bounded_height
     *      将当前索引下标入栈
     *      将current移动到下一个位置
     *
     * 复杂性分析
     *      时间复杂度： O(n)
     *          单次变量  O(n)，每个条形块最多访问两次（由于栈的弹入和弹出），并且弹入和弹出栈都是O（1）的。
     *      空间复杂度： O(n). 栈最多在阶梯型或平坦型条形块结构中占用 O(n)的空间
     *
     */
    public int trap3(int[] height) {
        int ans = 0, current =0;

        Stack<Integer> st = new Stack<Integer>();
        while(current < height.length){
            while (!st.empty() && height[current] > height[st.peek()]){
                int top = st.peek();
                st.pop();
                if (st.empty()) break;

                int distance = current - st.peek() -1;
                int boundedHeight = min(height[current],height[st.peek()]) - height[top];
                ans += distance * boundedHeight;
            }
            st.push(current++);
        }

        return ans;
    }

    /**
     *  方法4 ： 使用双指针
     *  直观想法
     *    和方法2相比，我们不从左和从右分开计算，我们想办法一次完成遍历
     *    从动态编程方法的示意图中我们注意到，只要right_max[i] > left_max[i](元素0到元素6)，
     *    积水高度将由left_max决定，类似地left_max[i] > right_max[i](元素8到元素11)。
     *    所以我们可以认为如果一端有更高的条形块（例如右端），积水的高度依赖于当前方向的高度（从左到右）。
     *    当我们发现另一侧（右侧）的条形块高度不是最高的，我们则开始从相反的方向遍历（从右到左）。
     *    我们必须在遍历时维护left_max和right_max，但是我们现在可以使用两个指针交替进行，实现1次遍历即可完成。
     *  算法
     *      初始化left指针为0并且right指针为size-1
     *      while left < right, do:
     *          if height[left] < height[right]
     *              if height[left] >= left_max ,更新left_max
     *              else 累加 left_max - height[left] 到ans
     *              left = left +1
     *          else
     *              if height[right] >= right_max, 更新right_max
     *              else 累加 right_max - height[right] 到 ans
     *              right = right -1
     *
     *   复杂性分析
     *      时间复杂度： O(n). 单次遍历的时间O(n)
     *      空间复杂度： O(1)的额外空间。 left,right,left_max和right_max只需要常数的空间
     */
    public int trap4(int[] height) {
        if (height.length == 0) return 0;

        int left = 0,right = height.length - 1;
        int ans = 0;
        int leftMax = 0,rightMax = 0;
        while( left < right){
            if (height[left] < height[right]) {
                if (height[left] >= leftMax ){
                    leftMax = height[left];
                }else {
                    ans += (leftMax - height[left]);
                }
                ++left;
            }else {
                if ( height[right] >= rightMax){
                    rightMax = height[right];
                } else {
                    ans += (rightMax - height[right]);
                }
                --right;
            }
        }
        return ans;
    }
}
