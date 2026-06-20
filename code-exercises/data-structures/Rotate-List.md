# Rotate List

**Source:** LeetCode

## Problem description:
Given the head of a linked list, rotate the list to the right by k places.

 

Example 1:


Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]
Example 2:


Input: head = [0,1,2], k = 4
Output: [2,0,1]
 

Constraints:

The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 109

## Solution:
````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func rotateRight(head *ListNode, k int) *ListNode {
    if head == nil || k == 0 {
        return head
    }
    
    end, l := getListLengthAndEndNode(head)
    
    k_rel := k % l
    newHeadIndex := l - k_rel
        
    if newHeadIndex == 0 {
        return head
    }
    
    end.Next = head
    newEnd := findNodeAtIndex(head, newHeadIndex - 1)
    newHead := newEnd.Next
    newEnd.Next = nil
    
    return newHead
    
}

func getListLengthAndEndNode(head *ListNode) (*ListNode, int) {
    l := 0
    end := head
    
    for p := head; p != nil; p = p.Next {
        l++
        end = p
    }
    
    return end, l
}

func findNodeAtIndex(head *ListNode, index int) *ListNode {
    node := head
    for i := 0; i < index; i ++ {
        node = node.Next
    }
    return node
}
````