# Remove Linked List Elements

**Source:** LeetCode

## Problem description:
Given the head of a linked list and an integer val, remove all the nodes of the linked list that has Node.val == val, and return the new head.

 

Example 1:

Input: head = [1,2,6,3,4,5,6], val = 6
Output: [1,2,3,4,5]

Example 2:

Input: head = [], val = 1
Output: []

Example 3:

Input: head = [7,7,7,7], val = 7
Output: []

 

Constraints:

    The number of nodes in the list is in the range [0, 104].
    1 <= Node.val <= 50
    0 <= val <= 50


## Solution:
### Iterative
````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func removeElements(head *ListNode, val int) *ListNode {
    
    dummy := &ListNode {
        0,
        head,
    }
    
    prev := dummy
    curr := dummy.Next
    for curr != nil {
        if curr.Val == val {
            prev.Next = nil
            curr = curr.Next
        } else {
            prev.Next = curr
            prev = curr
            curr = curr.Next
        }
    }
    
    return dummy.Next
}

````

### Recursive - not as memory efficient
````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func removeElements(head *ListNode, val int) *ListNode {    
    return getNextValidNode(head, val)
}

func getNextValidNode(node *ListNode, val int) *ListNode {
    if node == nil {
        return nil
    }
    
    if node.Val == val {
        return getNextValidNode(node.Next, val)
    } else {
        node.Next = getNextValidNode(node.Next, val)
        return node
    }
}
````