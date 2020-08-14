package com.ming.leetCode;

/**
 * 222 完全二叉树的节点个数
 *      给出一个完全二叉树，求出该树的节点个数
 *      说明：
 *          完全二叉树的定义：
 *              在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，
 *           并且最下面一层的节点都集中在该层最左边的若干位置。
 *           若最底层为第h层，则该层包含 1~2^h个结点
 *
 *     示例：
 *      输入    1
 *            2    3
 *          4  5  6
 *     输出  6
 * @Author ming
 * @time 2020/2/26 17:39
 */
public class Solution222 {

    /**
     * 方法一： 线性时间
     *    最简单的解决方法就是用递归一个个计算节点
     *    复杂度分析
     *      时间复杂度 O(n)
     *      空间复杂度 O(d) = O(log N ) ,其中d指的是树的高度，运行过程中堆栈所使用的空间。
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        return root != null ? 1 + countNodes(root.right) + countNodes(root.left) : 0;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
