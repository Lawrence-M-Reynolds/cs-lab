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
### Discussion
It can be shown that, if the following is true:
    - the faster pointer (pF) is moving by two, while the slow pointer (pS) moves by one
    - the Intersection (I) where the cycle starts is at distance (d) from the start of the list (b) then the 

Then if you were to move forward by (d) from the point where both pointers meet (M), you will reach I.
Therefore, if you were to move from (M) and the base node (b) a distance (d) simultaneously, then they would both meet at (I).

#### Proof
##### CASE (d) = 0 (so the list is basically just a circle, where the (I) is at (b). )
Then (M) will be at (I).
This is because, when (pF) will have completed the cycle once, while (pS) will have gone halfway. And then (pF) will complete its second cycle when (ps) complets it's first, so both will be at (I).

##### CASE (d) > 0, (so there's a line before the circle)
We know that when (pS) has reached (I), then (pF) must have moved distance (d) around the circle, beacuse (pS) must have moved by (d) and (pF) is twice as fast.
This time, if (pS) arrives halfway around the circle then (pF) will be back at distance (d) from (I).
If the size of the circle is (C) then we know that the distance between the two pointers must therefor be:
    (C/2) - d
And because of their relative speeds we know that they must therefore meet when (pF) has moved twice this distance:
    C - 2d
Because (pF)'s origin was at distance (d) around the circle, therefore both pointers meet at this distance round the circle:
    C - 2d + d  or
    C - d
So we can therefore deduce that the distance from this point back to (I) must be d.

TODO: Need to generalise this for when d >> C
 
###


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
    pFastNode := head.Next
    
    for pFastNode != nil && pFastNode.Next != nil {
        if pSlowNode == pFastNode {
            return findIntersectionNode(head, pSlowNode.Next)
        }
        
        pSlowNode = pSlowNode.Next
        pFastNode = pFastNode.Next.Next
    }
    
    return nil
    
}

func findIntersectionNode(pLeftNode *ListNode, pRightNode *ListNode) *ListNode {
    for pLeftNode != pRightNode {
        pLeftNode = pLeftNode.Next
        pRightNode = pRightNode.Next
    }
    
    return pLeftNode
    
}
````