package com.ming.leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 226 翻转二叉树
 * 翻转一棵二叉树
 *  示例：
 *      4
 *     2  7
 *    1 3 6 9
 *
 *    输出：
 *         4
 *       7    2
 *      9 6  3 1
 * @Author ming
 * @time 2020/3/7 17:29
 */
public class Solution226 {

    /**
     * 递归法
     * @param root
     * @return
     */
    public static TreeNode invertTree(TreeNode root){
        if (root == null){
            return null;
        }

        TreeNode right = invertTree(root.right);//反转右边的子树
        TreeNode left = invertTree(root.left);// 反转左边的子树

        //交换结点
        root.left = right;
        root.right = left;
        return root;
    }

    /**
     * 迭代法
     *   层序遍历，
     * @param root
     * @return
     */
    public static TreeNode invertTree2(TreeNode root){

        if (root == null) return null;

        Queue<TreeNode> queue = new LinkedList<>();//队列
        queue.add(root);

        while (!queue.isEmpty()){
            TreeNode current = queue.poll(); //出列
            TreeNode temp = current.left;//临时存放，用于交换位置
            current.left = current.right;
            current.right = temp;
            if (current.left != null)queue.add(current.left);
            if (current.right != null)queue.add(current.right);
        }

        return root;
    }

    /**
     * 利用前序遍历
     *   先序遍历 -- 从顶向下交换
     * @param root
     * @return
     */
    public static TreeNode invertTree3(TreeNode root){

        if (root == null) return null;

        //保存右子树
        TreeNode rightTree = root.right;
        //交换左右子树的位置
        root.right = invertTree3(root.left);
        root.left = invertTree3(rightTree);
        return root;
    }

    /**
     * 利用中序遍历
     * @param root
     * @return
     */
    public static TreeNode invertTree4(TreeNode root){

        if (root == null) return null;

        invertTree4(root.left);//递归找到左节点
        TreeNode rightTree = root.right;//保存右节点
        root.right = root.left;
        root.left = rightTree;

        //递归找到右节点，继续交换，因此此时左节点已经交换过了，所以此时的右节点为root.left
        invertTree4(root.left);

        return root;
    }

    /**
     * 利用后序遍历
     * @param root
     * @return
     */
    public static TreeNode invertTree5(TreeNode root){
        //后序遍历 - 从下向上交换
        if (root == null) return null;
        TreeNode leftNode = invertTree5(root.left);
        TreeNode rightNode = invertTree5(root.right);
        root.right = leftNode;
        root.left = rightNode;
        return root;
    }


    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){
            val = x;
        }
    }
}
