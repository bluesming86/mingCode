package com.ming.leetCode;

/**
 * 328 奇偶链表
 *      给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。
 *         这里的奇数节点和偶数节点指的数节点编号的奇偶性，而不是节点的值的奇偶性。
 *
 *         示例 1:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 1->3->5->2->4->NULL
 * 示例 2:
 *
 * 输入: 2->1->3->5->6->4->7->NULL
 * 输出: 2->3->6->7->1->5->4->NULL
 * @Author ming
 * @time 2020/3/26 20:30
 */
public class Solution328 {

    public ListNode oddEvenList(ListNode head) {

        if (head == null) return null;
        ListNode odd = head,//奇数节点
                even = head.next,//偶数节点
                evenHead = even;//偶数节点的头，最后分类完后，接到奇数节点后面，就完成分装
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;


    }

     class ListNode {
       int val;
          ListNode next;
          ListNode(int x) { val = x; }
     }
}
