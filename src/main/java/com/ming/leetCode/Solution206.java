package com.ming.leetCode;

/**
 * 206 反转链表
 *   示例：
 *     输入 1->2->3->4->5->null
 *     输出 5->4->3->2->1->null
 * @Author ming
 * @time 2020/2/22 17:32
 */
public class Solution206 {

    /**
     * 迭代法
     *
     *    head : 1->2->3->4->5->null
     *
     *    1: moveNode = null;
     *       temp : 2->3->4->5->null
     *       head : 1->null
     *       moveNode - > head : 1->null
     *       head = temp = 2->3->4->5->null
     *
     *    2: temp = head.next = 3->4->5->null
     *       head.next = moveNode :  2->1 ->null
     *       moveNode = head : 2->1 ->null
     *        head = temp = 3->4->5->null
     *    3: temp = head.next =  4->5->null
     *       head.next = moveNode :  3->2->1 ->null
     *       moveNode = head : 3->2->1 ->null
     *       head = temp = 4->5->null
     *    4: temp = head.next =  5->null
     *       head.next = moveNode :  4->3->2->1 ->null
     *       moveNode = head :4->3->2->1 ->null
     *       head = temp = 5->null
     *    5: temp = head.next =  null
     *      head.next = moveNode :  5->4->3->2->1 ->null
     *      moveNode = head :5->4->3->2->1 ->null
     *      head = temp = null
     *      head == null 结束迭代
     *
     *      时间复杂度：O(n)O(n)，假设 nn 是列表的长度，时间复杂度是 O(n)O(n)。
     * 空间复杂度：O(1)O(1)。
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head){
        ListNode moveNode = null;
        ListNode temp;//用来保存 后续的链
        while(head != null){
            temp = head.next;
            head.next = moveNode;
            moveNode = head;
            head = temp;
        }
        return moveNode;
    }

    /**
     * 递归方法
     *    递归版本 稍微复杂一些，其关键在于反向工作。假设列表的其余部分已经被反转，现在我该如何反转它前面的部分？
     *    假设列表为：
     *      N1->...->Nk-1->Nk->Nk+1->...->Nm->null
     *    若从节点Nk+1到Nm已经被反转，而我们正处于Nk
     *      N1->...->Nk-1->Nk->Nk+1<-...<-Nm
     *     我们希望Nk+1 的下一个节点指向Nk
     *     所以，Nk.next.next=Nk
     *    要小心的是 N1的下一个必须指向null.如果你忽略了这一点，你的链表中可能会产生循环。如果使用大小为2的链表测试代码，这可能会捕获此错误。
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head){

        if (head == null|| head.next == null) return head;

        ListNode p = reverseList2(head.next);
        head.next.next = head;
        head.next = null;

        return p;
    }



    class ListNode{
        int val;
        ListNode next;
        ListNode(int x){val = x;}
    }
}

