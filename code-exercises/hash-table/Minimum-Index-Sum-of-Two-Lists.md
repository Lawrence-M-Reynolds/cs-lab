# Minimum Index Sum of Two Lists

**Source:** LeetCode

## Problem description:
Given two arrays of strings list1 and list2, find the common strings with the least index sum.

A common string is a string that appeared in both list1 and list2.

A common string with the least index sum is a common string such that if it appeared at list1[i] and list2[j] then i + j should be the minimum value among all the other common strings.

Return all the common strings with the least index sum. Return the answer in any order.

 

Example 1:

Input: list1 = ["Shogun","Tapioca Express","Burger King","KFC"], list2 = ["Piatti","The Grill at Torrey Pines","Hungry Hunter Steakhouse","Shogun"]
Output: ["Shogun"]
Explanation: The only common string is "Shogun".
Example 2:

Input: list1 = ["Shogun","Tapioca Express","Burger King","KFC"], list2 = ["KFC","Shogun","Burger King"]
Output: ["Shogun"]
Explanation: The common string with the least index sum is "Shogun" with index sum = (0 + 1) = 1.
Example 3:

Input: list1 = ["happy","sad","good"], list2 = ["sad","happy","good"]
Output: ["sad","happy"]
Explanation: There are three common strings:
"happy" with index sum = (0 + 1) = 1.
"sad" with index sum = (1 + 0) = 1.
"good" with index sum = (2 + 2) = 4.
The strings with the least index sum are "sad" and "happy".
 

Constraints:

1 <= list1.length, list2.length <= 1000
1 <= list1[i].length, list2[i].length <= 30
list1[i] and list2[i] consist of spaces ' ' and English letters.
All the strings of list1 are unique.
All the strings of list2 are unique.
There is at least a common string between list1 and list2.

## Solution:
````golang
import "math"

func findRestaurant(list1 []string, list2 []string) []string {
    list1StringToIndexMap := make(map[string]int, len(list1))
    
    for i, str := range list1 {
        list1StringToIndexMap[str] = i
    }
    
    var bestStrs []string
    bestSum := math.MaxInt
    
    for i1, str := range list2 {
        if i1 > bestSum {
            break
        }

        i2, exists := list1StringToIndexMap[str]
        sum := i1 + i2
        if exists {
            if sum < bestSum {
                bestStrs = bestStrs[:0]
                bestStrs = append(bestStrs, str)
                bestSum = sum
            } else if sum == bestSum {
                bestStrs = append(bestStrs, str)
            }
        }
    }
    
    return bestStrs
}
````