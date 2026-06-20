# Copy List with Random Pointer
**Source:** LeetCode

## Problem description:
A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.

Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the next and random pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.

For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes x and y in the copied list, x.random --> y.

Return the head of the copied linked list.

The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:

val: an integer representing Node.val
random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it does not point to any node.
Your code will only be given the head of the original linked list.

 

Example 1:


Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
Example 2:


Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]
Example 3:



Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]
 

Constraints:

0 <= n <= 1000
-104 <= Node.val <= 104
Node.random is null or is pointing to some node in the linked list.

## Solution:
````golang
/**
 * Definition for a Node.
 * type Node struct {
 *     Val int
 *     Next *Node
 *     Random *Node
 * }
 */

func copyRandomList(head *Node) *Node {
    /*
    # Create the nodes of the new list
    iterate through each node and create the copy, but as well as adding
    it to a new list, insert it as the next item in the original list.
    (make sure that you move the next item in the original array)
    
    # Set the random links of the nodes in the new list
    Iterate through the now modified original list by two elements at the time,
    so just touching the original list items.
    Set the random pointer of the next item (the new list item) to the next pointer
    of the node that the random pointer of the original list item.
    (ignore those with null random pointers)
    
    # Clean the original list
    Again, iterate through the now modified original list by two elements at the time,
    so just touching the original list items.
    remove the next itme of each element touched.
    */
        
    createAndInterweaveNewListNodes(head)
    
    updateRandomPointersOfNewListNodes(head)
    
    return extractNewNodeListFromOriginal(head)
    
}

func createAndInterweaveNewListNodes(head *Node) {
    for pO := head; pO != nil; pO = pO.Next.Next {
        newNode := &Node {
            pO.Val,
            pO.Next,
            nil,
        }
        
        pO.Next = newNode
    }
}

func updateRandomPointersOfNewListNodes(head *Node) {
    for pO := head; pO != nil; pO = pO.Next.Next {
        pN := pO.Next
        if pO.Random != nil {
            pN.Random = pO.Random.Next  
        }
    }
}

func extractNewNodeListFromOriginal(head *Node) *Node {
    newListDummyHead := &Node{}
    for pO, pN := head, newListDummyHead; pO != nil; pO = pO.Next {
        pN.Next = pO.Next
        pO.Next = pO.Next.Next
        
        pN = pN.Next
    }
    
    return newListDummyHead.Next
}

````