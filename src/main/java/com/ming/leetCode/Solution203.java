package com.ming.leetCode;

/**
 * 移除链表元素
 *  删除链表中等于给定val的所有节点
 *    示例： 1->2->6->3->4->5->6-> , val = 6
 *    输出   1->2->3->4->5
 * @Author ming
 * @time 2020/2/22 11:25
 */
public class Solution203 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode removeElements(ListNode head, int val) {
        ListNode preHead = new ListNode(0); //牵头节点，用来返回链表，next 后面的为结果链表
        ListNode temp = head;//临时节点，用来遍历原来链表，判断每个节点是否要记录到结果节点中
        ListNode moveNode = preHead; //移动节点，判定过程中用来记录跟跳过节点用
        while(temp != null){
            if(temp.val == val){ //是要删除的节点
                moveNode.next = temp.next; //那么就跳过该结点
            } else { //不是要删除的 节点，就记录该节点，继续看下一个节点
                moveNode.next = temp; //记录该节点
                moveNode = moveNode.next;//移动到这个节点上
            }
            temp = temp.next;//遍历下一个节点
        }

        return preHead.next;
    }

    /**
     * 别人写的
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements2(ListNode head, int val) {
        ListNode sentinel = new ListNode(0);
        sentinel.next = head;

        ListNode prev = sentinel, curr = head;
        while (curr != null) {
            if (curr.val == val) prev.next = curr.next;
            else prev = curr;
            curr = curr.next;
        }
        return sentinel.next;
    }


}



