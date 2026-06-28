# Intersection of Two Arrays

**Source:** LeetCode

## Problem description:
Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique and you may return the result in any order.

 

Example 1:

Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2]
Example 2:

Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [9,4]
Explanation: [4,9] is also accepted.
 

Constraints:

1 <= nums1.length, nums2.length <= 1000
0 <= nums1[i], nums2[i] <= 1000

## Solution:
````golang
func intersection(nums1 []int, nums2 []int) []int {
    set1 := make(map[int]struct{}, len(nums1))
    
    return findIntersection(nums1, nums2, set1)
}


func findIntersection(nums1 []int, nums2 []int, 
                      set1 map[int]struct{}) []int {
    
    result := make([]int, 0, len(nums2))
    for _, num := range nums1 {
        set1[num] = struct{}{}
    }
    
    for _, num := range nums2 {
        _, exists := set1[num]
        if exists{
            result = append(result, num)
            delete(set1, num)
        }
    }
    
    return result
}

````