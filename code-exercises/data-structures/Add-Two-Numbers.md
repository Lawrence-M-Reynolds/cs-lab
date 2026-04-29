#   Add Two Numbers

**Source:** LeetCode

## Problem description:
You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

 

Example 1:


Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
Example 2:

Input: l1 = [0], l2 = [0]
Output: [0]
Example 3:

Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]
 

Constraints:

The number of nodes in each linked list is in the range [1, 100].
0 <= Node.val <= 9
It is guaranteed that the list represents a number that does not have leading zeros.

## Solution:
````golang
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {

    resultDummyHead := &ListNode{}
    p := resultDummyHead
    remainder := 0
    p1 := l1
    p2 := l2
    for p1 != nil || p2 != nil {
        sum := getNodeVal(p1) + getNodeVal(p2)
        
        result := sum + remainder
        
        if result >= 10 {
            result = result - 10
            remainder = 1
        } else {
            remainder = 0
        }
        
        p.Next = &ListNode {
            result,
            nil,
        }
        
        p1 = getNextNode(p1)
        p2 = getNextNode(p2)
        p = p.Next
    }
    
    if remainder > 0 {
        p.Next = &ListNode {
            remainder,
            nil,
        }
    }
    
    return resultDummyHead.Next
}

func getNextNode(node *ListNode) *ListNode {
    if node == nil {
        return nil
    } else {
        return node.Next
    }
}

func getNodeVal(node *ListNode) int {
    if node == nil {
        return 0
    } else {
        return node.Val
    }
}

````
### Failed strategy that exceeds the maximum limits of the primitive int data types
````golang
import "fmt"
import "math"

/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
    val1 := convertListToInt(l1)
    fmt.Printf("val1 = %d\n", val1)
    
    val2 := convertListToInt(l2)
    fmt.Printf("val2 = %d\n", val2)
    
    l1New := converIntToList(val1)
    val1New := convertListToInt(l1New)
    fmt.Printf("val1New = %d\n", val1New)
    
    return converIntToList(val1 + val2)
    // return l1New
}

func converIntToList(val int) *ListNode {
    fmt.Printf("converting to list = %d\n", val)
    valTrack := 0
    
    resultDummyHead := &ListNode{}
    p := resultDummyHead
    value := 0
    prevMultiplier := 1
    for pow := 1; valTrack < val; pow++ {
        multiplier := int(math.Pow(float64(10), float64(pow)))
        value = (val % multiplier - valTrack)
        
        valTrack += value
        fmt.Printf("value = %d, pow = %d, valTrack = %d\n", value, pow, valTrack)
        
        p.Next = &ListNode {
            value / prevMultiplier,
            nil,
        }
        
        prevMultiplier = multiplier
        p = p.Next
        
    }
    
    if resultDummyHead.Next == nil {
        resultDummyHead.Next = &ListNode {
            0,
            nil,
        }
    }
    
    return resultDummyHead.Next  
}

func convertListToInt(list *ListNode) int {
    result := 0
    
    // listSize := getListSize(list)
    // fmt.Printf("listSize = %d\n", listSize)
    
    pow := 0
    
    for p := list; p != nil; p = p.Next {
        multiplier := int(math.Pow(float64(10), float64(pow)))
        result += p.Val * multiplier
        // fmt.Printf("10^%d = %d, multiplier = %d\n", pow, multiplier)
        fmt.Printf("p.Val = %d, result = %d, 10^%d, multiplier = %d\n", p.Val, result, pow, multiplier)
        pow++
    }
    
    return result
}

// func getListSize(list *ListNode) int {
//     size := 0
    
//     for p := list; p != nil; p = p.Next {
//         size++
//     }
//     return size
// }
````