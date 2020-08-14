package com.ming.leetCode;

import java.util.LinkedList;
import java.util.List;

/**
 * 257 二叉树的所有路径
 *   给定一个二叉树，返回所有从根节点到叶子节点的路径
 *
 *   说明： 叶子节点是指 没有子节点的节点
 *
 *   示例：
 *   输入  1
 *       2   3
 *        5
 *     输出 ["1->2->5","1->3"]
 * @Author ming
 * @time 2020/3/19 19:23
 */
public class Solution257 {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {val = x;}
    }
    /**
     * 递归查找法
     * @param root
     */
    public List<String> binaryTreePaths(TreeNode root){
        List<String> paths = new LinkedList<>();

        findLeafPaths(root,"",paths);
        return paths;

    }

    private void findLeafPaths(TreeNode root, String path, List<String> paths) {
        if (root != null){
            //判断是不是叶子节点
            path = path + root.val;
            if (root.left == null && root.right ==null){
                //是叶子节点 ，添加到list中
                paths.add(path);
            } else {
                path += "->";
                //还有子节点，继续向下找
                findLeafPaths(root.left, path, paths);//向左子树找
                findLeafPaths(root.right, path, paths);//向右子树找
            }
        }
    }


    /**
     * 迭代法
     * @param root
     * @return
     */
    public List<String> binaryTreePaths2(TreeNode root) {
        LinkedList<String> paths = new LinkedList();

        if (root == null){
            return paths;
        }

        LinkedList<TreeNode> nodeStack = new LinkedList<>();
        LinkedList<String> pathsStack = new LinkedList();

        nodeStack.add(root);
        paths.add(Integer.toString(root.val));

        TreeNode node;
        String path;

        while (!nodeStack.isEmpty()){
            node = nodeStack.pollLast();
            path = pathsStack.pollLast();
            if (node.left == null && node.right == null){
                paths.add(path);
            }

            if (node.left != null){
                nodeStack.add(node.left);
                pathsStack.add(path + "->" + Integer.toString(node.left.val));
            }
            if (node.right != null){
                nodeStack.add(node.right);
                pathsStack.add(path + "->" + Integer.toString(node.right.val));
            }
        }
        return paths;
    }
}
