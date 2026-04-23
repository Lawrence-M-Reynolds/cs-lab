# Reverse Linked List

**Source:** LeetCode

## Problem description:

Given the head of a singly linked list, reverse the list, and return the reversed list.

 

Example 1:

Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]

Example 2:

Input: head = [1,2]
Output: [2,1]

Example 3:

Input: head = []
Output: []

 

Constraints:

    The number of nodes in the list is the range [0, 5000].
    -5000 <= Node.val <= 5000

 

Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?


## Solution 1:
### Iterative
````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func reverseList(head *ListNode) *ListNode {    
    var pPrev *ListNode = nil
    pCurr := head
        
    for pCurr != nil {
        pNext := pCurr.Next
        
        pCurr.Next = pPrev

        pPrev = pCurr
        pCurr = pNext
    }
    
    return pPrev
    
}
````

### Recursive
````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func reverseList(head *ListNode) *ListNode {
    if head == nil {
        return nil
    }
    
    var pPrev *ListNode = nil
    return reverse(pPrev, head)
    
}

func reverse(pPrev, pCurr *ListNode) *ListNode {
    pNext := pCurr.Next
    pCurr.Next = pPrev
    
    if pNext == nil {
        return pCurr
    }
    
    return reverse(pCurr, pNext)
}
````