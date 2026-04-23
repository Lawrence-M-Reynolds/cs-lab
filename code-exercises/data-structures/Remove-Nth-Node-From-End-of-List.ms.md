# Remove Nth Node From End of List

**Source:** LeetCode

## Problem description:
Given the head of a linked list, remove the nth node from the end of the list and return its head.

 

Example 1:

Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]

Example 2:

Input: head = [1], n = 1
Output: []

Example 3:

Input: head = [1,2], n = 1
Output: [1]

 

Constraints:

    The number of nodes in the list is sz.
    1 <= sz <= 30
    0 <= Node.val <= 100
    1 <= n <= sz

 

Follow up: Could you do this in one pass?

## Solution:
````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func removeNthFromEnd(head *ListNode, n int) *ListNode {
    if head.Next == nil {
        return nil
    }
    
    dummyBaseNode := ListNode {
        0,
        head,
    }
    
    pSearchNode := head
    pPreDeleteNode := &dummyBaseNode
    pPreDeleteDistance := 1
    
    for pSearchNode.Next != nil {
        if pPreDeleteDistance == n {
            pPreDeleteNode = pPreDeleteNode.Next
        } else {
            pPreDeleteDistance++
        }
        pSearchNode = pSearchNode.Next
    }
    
    pPreDeleteNode.Next = pPreDeleteNode.Next.Next
    return dummyBaseNode.Next
}
````