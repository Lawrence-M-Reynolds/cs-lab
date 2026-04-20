# Linked List Cycle II

**Source:** LeetCode

## Problem description:
Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to (0-indexed). It is -1 if there is no cycle. Note that pos is not passed as a parameter.

Do not modify the linked list.

 

Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.

Example 2:

Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.

Example 3:

Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.

 

Constraints:

    The number of the nodes in the list is in the range [0, 104].
    -105 <= Node.val <= 105
    pos is -1 or a valid index in the linked-list.

 

Follow up: Can you solve it using O(1) (i.e. constant) memory?

## Solution:

````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func detectCycle(head *ListNode) *ListNode {
    if head == nil {
        return nil
    }    
    
    pSlowNode := head
    pSlowIndex := 0
    pFastNode := head.Next
    pFastIndex := 1
    
    for pFastNode != nil && pFastNode.Next != nil {
        if pSlowNode == pFastNode {
            // circleSize := pFastNode - pSlowNode
            // intersectionIndex := pSlowNode - circleSize

            searchStartIndex := 2*pSlowIndex - pFastIndex
            pLeftNode, pRightNode := getSearchStartNodes(head, pSlowNode, searchStartIndex)
            
            return findIntersectionNode(pLeftNode, pRightNode)
        }
        
        pSlowNode = pSlowNode.Next
        pSlowIndex++
        
        pFastNode = pFastNode.Next.Next
        pFastIndex += 2
    }
    
    return nil
    
}

func getSearchStartNodes(head *ListNode, pSlowNode *ListNode, searchStartIndex int) (*ListNode, *ListNode) {
    pLeftNode := head
    pRightNode := pSlowNode
    
    if searchStartIndex >= 0 {
        for i := 1; i <= searchStartIndex; i++ {
            pLeftNode = pLeftNode.Next
        }
    } else {
        for i := searchStartIndex; i < 0; i++ {
            pRightNode = pRightNode.Next
        }
    }
    
    return pLeftNode, pRightNode
}

func findIntersectionNode(pLeftNode *ListNode, pRightNode *ListNode) *ListNode {
    for pLeftNode != pRightNode {
        pLeftNode = pLeftNode.Next
        pRightNode = pRightNode.Next
    }
    
    return pLeftNode
    
}
````