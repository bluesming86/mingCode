package com.ming.leetCode;

import java.util.HashMap;

/**
 * 337 打家劫舍III
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
 * <p>
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 *
 * @Author ming
 * @time 2020/3/28 9:43
 */
public class Solution337 {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 解法1  暴力递归。找最优子结构
     *
     * 先来定义这个问题的状态
     * 爷爷节点获取到最大的偷取的钱数呢
     *
     * 首先要明确相邻的节点不能偷，也就是爷爷选择偷，儿子就不能偷了，但是孙子可以偷
     * 二叉树只有左右两个孩子，一个爷爷最多2个儿子,4个孙子
     * 根据以上条件，我们可以得出单个节点的钱该怎么算
     * ** 4个孙子偷的钱 + 爷爷的钱 VS 两个儿子偷的钱 哪个组合钱多，就当做当前节点能偷的最大钱数。这就是动态规划里面的最优子结构**
     *
     *
     * 由于是二叉树，这里可以选择计算所有子节点
     *
     * 4个孙子投的钱加上爷爷的钱如下
     * int method1 = root.val + rob(root.left.left) + rob(root.left.right) + rob(root.right.left) + rob(root.right.right)
     * 两个儿子偷的钱如下
     * int method2 = rob(root.left) + rob(root.right);
     * 挑选一个钱数多的方案则
     * int result = Math.max(method1, method2);
     * @param root
     * @return
     */
    public int rob1(TreeNode root) {
        if (root == null) return 0;//空节点，就加0

        int money = root.val;
        if (root.left != null) {//左边 儿子不为空，就加上孙子的钱
            money += (rob1(root.left.left) + rob1(root.left.right));
        }

        if (root.right != null) {//右边 儿子不为空，就加上孙子的钱
            money += (rob1(root.right.left) + rob1(root.right.right));
        }

        /**
         * 返回  两个儿子的钱 跟 爷爷+ 孙子的 钱的 最大值
         */
        return Math.max(money, rob1(root.left) + rob1(root.right));
    }

    /**
     * 解法2-记忆化-解决重复子问题
     * 针对解法一种速度太慢的问题，经过分析其实现，我们发现爷爷在计算自己能偷多少钱的时候，同时计算了4个孙子能偷多少钱，也计算了2个儿子能偷多少钱。这样在儿子当爷爷时，就会产生重复计算一遍孙子节点。
     *
     * 于是乎我们发现了一个动态规划的关键优化点
     * 重复子问题
     * 我们这一步针对重复子问题进行优化，我们在做斐波那契数列时，使用的优化方案是记忆化,但是之前的问题都是使用数组解决的，把每次计算的结果都存起来，下次如果再来计算，就从缓存中取，不再计算了，这样就保证每个数字只计算一次。
     * 由于二叉树不适合拿数组当缓存，我们这次使用哈希表来存储结果，TreeNode当做key，能偷的钱当做value
     *
     * 解法1加上记忆化优化后代码如下
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        HashMap<TreeNode, Integer> memo = new HashMap<TreeNode, Integer>();
        return robInternal(root, memo);
    }

    public int robInternal(TreeNode root, HashMap<TreeNode, Integer> memo) {
        if (root == null) return 0;
        if (memo.containsKey(root)) return memo.get(root);
        int money = root.val;

        if (root.left != null) {
            money += (robInternal(root.left.left, memo) + robInternal(root.left.right, memo));
        }
        if (root.right != null) {
            money += (robInternal(root.right.left, memo) + robInternal(root.right.right, memo));
        }
        int result = Math.max(money, robInternal(root.left, memo) + robInternal(root.right, memo));
        memo.put(root, result);
        return result;
    }


    /**
     * 解法3-终极解法
     * 上面两种解法用到了孙子节点，计算爷爷节点能偷的钱还要同时去计算孙子节点投的钱，虽然有了记忆化，但是还是有性能损耗。
     *
     * 我们换一种办法来定义此问题
     * 每个节点可选择偷或者不偷两种状态，根据题目意思，相连节点不能一起偷
     *
     * 当前节点选择偷时，那么两个孩子节点就不能选择偷了
     * 当前节点选择不偷时，两个孩子节点只需要拿最多的钱出来就行(两个孩子节点偷不偷没关系)
     * 我们使用一个大小为2的数组来表示 int[] res = new int[2] 0代表不偷，1代表偷
     * 任何一个节点能偷到的最大钱的状态可以定义为
     *
     * 当前节点选择不偷: 当前节点能偷到的最大钱数 = 左孩子能偷到的钱 + 右孩子能偷到的钱
     * 当前节点选择偷: 当前节点能偷到的最大钱数 = 左孩子选择自己不偷时能得到的钱 + 右孩子选择不偷时能得到的钱 + 当前节点的钱数
     * 表示为公式如下
     *
     * root[0] = Math.max(rob(root.left)[0], rob(root.left)[1]) + Math.max(rob(root.right)[0], rob(root.right)[1])
     * root[1] = rob(root.left)[0] + rob(root.right)[0] + root.val;
     * 将公式做个变换就是代码啦
     *
     * @param root
     * @return
     */
    public int rob3(TreeNode root) {
        int[] result = robInternal(root);
        return Math.max(result[0], result[1]);
    }

    public int[] robInternal(TreeNode root) {
        if (root == null) return new int[2];
        int[] result = new int[2];// 0 存放不偷时能获的的钱， 1存放偷时，能偷到的钱

        int[] left = robInternal(root.left);//递归获取左边的树
        int[] right = robInternal(root.right);//递归获取右边的树

        result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); //因为当前节点不偷时， 子节点可以选择偷或者不偷。那么就取左边的偷时、跟不偷时获取钱的最大值 再加上 右边的情况
        result[1] = left[0] + right[0] + root.val;// 当前节点 偷时，那么子节点不能偷，因此 能偷的钱 为。当前节点的钱加 子节点不能偷的时候的钱

        return result;
    }

}
