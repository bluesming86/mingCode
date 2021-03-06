package com.ming.leetCode;

import java.util.Stack;

/**
 * 224 基本计算器
 *  实现一个基本的计算器来计算一个简单的字符串表达式的值。
 *  字符串表达式可以包含左括号（，右括号），加号 +, 减号 - ，非负整数 和空格。
 *
 *  示例 1：
 *      输入："1 + 1"
 *      输出：2
 *  示例 2：
 *      输入：" 2-1 + 2"
 *      输出： 3
 *  示例3：
 *      输入："(1+(4+5+2)-3)+(6+8)"
 *      输出： 23
 *
 *  说明
 *      你可以假设所给定的表达式，都是有效的
 *      请不要使用内置的库函数 eval
 * @Author ming
 * @time 2020/3/5 17:33
 */
public class Solution224 {

    /**
     * 方法一： 栈 和反转字符串
     *  这个问题适合用栈来解决，因为表达式中包含括号，我们可以使用栈来查找每个子表达式的值。
     *  本质上，我们需要延迟处理主表达式，直到完成对括号的中间子表达式的值，我们使用栈来解决它。
     *
     *  我们将表达式的元素一个接一个的添加到栈，知道我们遇到一个右括号 ）。然后逐个弹出栈中的元素，在运行时对子表达式进行求值，知道遇到 左括号为止。
     *
     *  我们需要理解 + 和 - 的区别。 + 遵循结合律 例如 A+B+C 等价于 （A + B）+C = A+ (B+C).然后 - 不遵循着个一规则，这是该方法中所有问题的根本原因。
     *
     *  如果我们使用栈并从左到右 读取表达式的元素，这最终我们会从右到左计算表达式。就会出现（A-B）-C 等于 （C-B）-A的情况。这是不正确。
     *  减法即不遵循结合律也不遵循交换律
     *
     *  这个问题很容易解决，我们通过反转字符串，然后再按需添加到栈中，我们将从右到左放入栈中，并从左到右正确的计算表达式
     *
     *  算法
     *      1.按逆序迭代字符串。
     *      2.操作数可以由多个字符组成，字符串“123”表示数字123，它可以被构造为 123 >> 120+3>>100+20+3.如果我们读取的字符是一个数字，则我们要将读取的数字乘以 10 的幂并将当前数字相加，形成操作数。因为我们是按逆序处理字符串。
     *      3.操作数由多个字符组成，一旦我们遇到的字符不是数字，则我们将操作数添加到栈上。
     *      4.当我们遇到最括号 (，这意味这遇到了一个子表达式结束。由于我们是逆序，所以开括号成了表达式的结尾。则需要从栈中弹出操作数和运算发来计算表达式，直到弹出相应的右括号。子表达式的最终结果最终添加到栈上。
     *      5.将非数字字符添加到栈上。
     *      6.这个做直到我们得到最终的结果。可能我们没有更多的字符要处理，但是栈仍然是非空的。当主表达式没有用括号括起来时，就会发生这种情况。因此，在完成对整个表达式求值之后，我们将检查栈是否非空。如果是的话，我们将栈中的元素作为最终表达式处理，并像遇到左括号时那样对其求值。我们还可以用一组括号覆盖原表达式，以此避免额外调用。
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        int operand = 0;
        int n = 0;
        Stack<Object> stack = new Stack<Object>();

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                // Forming the operand - in reverse order.
                operand = (int) Math.pow(10, n) * (int) (ch - '0') + operand;
                n += 1;
            } else if (ch != ' ') {
                if (n != 0) {
                    // Save the operand on the stack
                    // As we encounter some non-digit.
                    stack.push(operand);
                    n = 0;
                    operand = 0;
                }
                if (ch == '(') {
                    int res = evaluateExpr(stack);
                    stack.pop();
                    // Append the evaluated result to the stack.
                    // This result could be of a sub-expression within the parenthesis.
                    stack.push(res);
                } else {
                    // For other non-digits just push onto the stack.
                    stack.push(ch);
                }
            }
        }
        //Push the last operand to stack, if any.
        if (n != 0) {
            stack.push(operand);
        }
        // Evaluate any left overs in the stack.
        return evaluateExpr(stack);
    }

    public int evaluateExpr(Stack<Object> stack) {
        int res = 0;
        if (!stack.empty()) {
            res = (int) stack.pop();
        }
        // Evaluate the expression till we get corresponding ')'
        while (!stack.empty() && !((char) stack.peek() == ')')) {
            char sign = (char) stack.pop();
            if (sign == '+') {
                res += (int) stack.pop();
            } else {
                res -= (int) stack.pop();
            }
        }
        return res;
    }


    /**
     * 方法二：栈和不反转字符串
     * 解决 - 结合律的问题的一个分厂简单的方法就是使将 - 运算符看作右侧操作数的大小。一旦我们将 - 看作操作数的大小，则表达式将只剩下一个操作符。就是 + 运算符，而 + 是遵循结合律的。
     *
     * 例如，A-B-CA−B−C 等于 A + (-B) + (-C)A+(−B)+(−C)。
     *
     * 重写以后的表达式将遵循结合律，所以我们从左或从右计算表达式都是正确的。
     * 我们需要注意的是给定的表达式会很复杂，即会有嵌套在其他表达式的表达式。即 (A - (B - C))，我们需要 B-C 外面的 - 号与 B-C 关联起来，而不是仅仅与 B 关联起来。
     * / 我们可以通过遵循前面的基本练习并将符号与其右侧的表达式关联来解决此问题。然而，我们将采用的方法有一个小的转折，因为我们将在运行中评估大多数表达式。这减少了推送和弹出操作的数量。
     *
     * 算法：
     *      1.正序迭代字符串。
     *      2.操作数可以由多个字符组成，字符串 "123" 表示数字 123，它可以被构造为：123 >> 120 + 3 >> 100 + 20 + 3。
     *              如果我们读取的字符是一个数字，则我们要将先前形成的操作数乘以 10 并于读取的数字相加，形成操作数。
     *      3.每当我们遇到 + 或 - 运算符时，我们首先将表达式求值到左边，然后将正负符号保存到下一次求值。
     *      4.如果字符是左括号 (，我们将迄今为止计算的结果和符号添加到栈上，然后重新开始进行计算，就像计算一个新的表达式一样。
     *      5.如果字符是右括号 )，则首先计算左侧的表达式。则产生的结果就是刚刚结束的子表达式的结果。如果栈顶部有符号，则将此结果与符号相乘。
     *
     * @param s
     * @return
     */
    public int calculate2(String s) {

        Stack<Integer> stack = new Stack<Integer>();
        int operand = 0;
        int result = 0; // For the on-going result
        int sign = 1;  // 1 means positive, -1 means negative

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                // Forming operand, since it could be more than one digit
                operand = 10 * operand + (int) (ch - '0');
            } else if (ch == '+') {
                // Evaluate the expression to the left,
                // with result, sign, operand
                result += sign * operand;

                // Save the recently encountered '+' sign
                sign = 1;

                // Reset operand
                operand = 0;
            } else if (ch == '-') {
                result += sign * operand;
                sign = -1;
                operand = 0;
            } else if (ch == '(') {
                // Push the result and sign on to the stack, for later
                // We push the result first, then sign
                stack.push(result);
                stack.push(sign);

                // Reset operand and result, as if new evaluation begins for the new sub-expression
                sign = 1;
                result = 0;
            } else if (ch == ')') {
                // Evaluate the expression to the left
                // with result, sign and operand
                result += sign * operand;

                // ')' marks end of expression within a set of parenthesis
                // Its result is multiplied with sign on top of stack
                // as stack.pop() is the sign before the parenthesis
                result *= stack.pop();

                // Then add to the next operand on the top.
                // as stack.pop() is the result calculated before this parenthesis
                // (operand on stack) + (sign on stack * (result from parenthesis))
                result += stack.pop();

                // Reset the operand
                operand = 0;
            }
        }
        return result + (sign * operand);
    }
}
