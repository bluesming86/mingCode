package com.ming.leetCode;

/**
 * 221.最大正方形
 *      在一个由0和1组成的二维矩阵内，找到只包含1的最大正方形，并返回其面积
 *    示例  输入
 *      1 0 1 0 0
 *      1 0 1 1 1
 *      1 1 1 1 1
 *      1 0 0 1 0
 *      输出: 4
 * @Author ming
 * @time 2020/2/26 16:34
 */
public class Solution221 {

    /**
     * 方法1： 暴力法
     *  最简单的方法是找出矩阵中所有可以形成的1正方形。现在的问题是如何做到这一点？
     *      1.我们用一个变量去来记录迄今为止发现的最大正方形的边长，以及用一个变量记录当前正方形的大小，两个变量初始值都为0
     *      2.从矩阵的左上角开始搜索1，找到0 不需要做任何操作，只要找到1我们就试图找到由1组成的最大正方形
     *      3.为此我们向右和向下移动，临时增加列索引和行索引，然后用标志标记该行列是否全都为1；
     *      4.如果全部都为1，则继续搜索行列，如果找到0，便停止移动，更新最大正方形的边长。然后从最初发现1的元素旁边遍历矩阵，
     *          直到矩阵的所有元素都被遍历
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        int rows = matrix.length;
        int cols = rows >0 ? matrix[0].length:0;
        int maxsqlen = 0;
        for (int i = 0; i < rows ; i ++){
            for (int j = 0;j < cols; j++){
                if (matrix[i][j] == '1'){
                    int sqlen = 1;
                    boolean flag = true;
                    while (sqlen + i < rows && sqlen +j < cols && flag){
                        for (int k = j; k <= sqlen + j; k++){
                            if (matrix[i + sqlen][k] == '0'){
                                flag = false;
                                break;
                            }
                        }
                        for (int k = i; k <= sqlen + i;k++){
                            if (matrix[k][j + sqlen] == '0'){
                                flag = false;
                                break;
                            }
                        }
                        if (flag){
                            sqlen ++;
                        }
                    }
                    if (maxsqlen < sqlen){
                        maxsqlen = sqlen;
                    }
                }
            }
        }

        return maxsqlen * maxsqlen;
    }

    /**
     * 方法二： 动态规划
     *
     * @param matrix
     * @return
     */
    public int maximalSquare2(char[][] matrix) {
        int rows = matrix.length;
        int cols = rows > 0?matrix[0].length : 0;
        int[][] dp = new int[rows+1][cols + 1];
        int maxsqlen = 0;
        for (int i = 1;i <= rows; i++){
            for (int j= 1;j<= cols; j++){
                if (matrix[i-1][j-1] == '1'){
                    dp[i][j] = Math.min(Math.min(dp[i][j-1], dp[i-1][j]), dp[i -1][j -1])+1;
                    maxsqlen = Math.max(maxsqlen, dp[i][j]);
                }
            }
        }
        return maxsqlen * maxsqlen;
    }

    /**
     * 方法二： 动态规划优化
     *
     * @param matrix
     * @return
     */
    public int maximalSquare3(char[][] matrix) {
        int rows = matrix.length;
        int cols = rows > 0 ? matrix[0].length : 0;
        int[] dp = new int[cols + 1];
        int maxsqlen = 0, prev = 0;
        for (int i = 1;i <= rows; i++){
            for (int j= 1;j<= cols; j++){
                int temp = dp[j];
                if (matrix[i -1][j -1] == '1'){
                    dp[j] = Math.min(Math.min(dp[j-1], prev), dp[j]) +1;
                    maxsqlen = Math.max(maxsqlen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }
        return maxsqlen * maxsqlen;
    }
}
