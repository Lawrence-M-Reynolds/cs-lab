# Palindrome Linked List
**Source:** LeetCode

## Problem description:
Given the head of a singly linked list, return true if it is a palindrome or false otherwise.

 

Example 1:


Input: head = [1,2,2,1]
Output: true
Example 2:


Input: head = [1,2]
Output: false
 

Constraints:

The number of nodes in the list is in the range [1, 105].
0 <= Node.val <= 9
 

Follow up: Could you do it in O(n) time and O(1) space?

## Solution:
Note, I probably should have just used 3 separate loops: find the midsection, reverse one half, then compare.
My solution is slightly more efficient but more complicated to understand.

````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func isPalindrome(head *ListNode) bool {
    
    if head.Next == nil {
        // Single is palindrome
        return true
    }
    
    dummyStart := &ListNode{
        0,
        head,
    }
    
    pS := head
    pF := head
    
    pHalfSearch := head
    
    for pF != nil {
        
        nextPs := pS.Next
        pHalfSearch = nextPs   
        
        if pF.Next == nil {
            break
        } else if pS != head {
            pS.Next = dummyStart.Next
            dummyStart.Next = pS   
        }
                
        pS = nextPs
        pF = pF.Next.Next
    }
    
    pStartSearch := dummyStart.Next
    
    for pHalfSearch != nil {
        if pStartSearch.Val != pHalfSearch.Val {
            return false
        }
        pStartSearch = pStartSearch.Next
        pHalfSearch = pHalfSearch.Next
    }
    
    return true
}
````