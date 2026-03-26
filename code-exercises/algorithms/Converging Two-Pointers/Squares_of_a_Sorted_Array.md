# Squares of a Sorted Array

## Problem description

Given an integer array nums sorted in non-decreasing order, return an array of the squares of each number sorted in non-decreasing order.

 

Example 1:

Input: nums = [-4,-1,0,3,10]
Output: [0,1,9,16,100]
Explanation: After squaring, the array becomes [16,1,0,9,100].
After sorting, it becomes [0,1,9,16,100].
Example 2:

Input: nums = [-7,-3,2,3,11]
Output: [4,9,9,49,121]
 

Constraints:

1 <= nums.length <= 104
-104 <= nums[i] <= 104
nums is sorted in non-decreasing order.
 

Follow up: Squaring each element and sorting the new array is very trivial, could you find an O(n) solution using a different approach?

## Pseudocode


## Solution

````Java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];
        
        int l = 0;
        int r = nums.length - 1;
        
        for (int i = result.length - 1; i >= 0; i--) {
                int lVal = nums[l] * nums[l];
                int rVal = nums[r] * nums[r];
            if (rVal >= lVal) {
                result[i] = rVal;
                r--;
            } else {
                result[i] = lVal;
                l++;
            }
        }
        
        return result;
    }
}
````

````Python
class Solution:
    def sortedSquares(self, nums: List[int]) -> List[int]:
        result = nums[:]
        
        l = 0
        r = len(nums) - 1
        
        for i in range(len(result) - 1, -1, -1):
            lVal = nums[l] * nums[l];
            rVal = nums[r] * nums[r];
            if rVal >= lVal :
                result[i] = rVal
                r -= 1
            else :
                result[i] = lVal
                l += 1
        
        return result
        
````

````golang
func sortedSquares(nums []int) []int {
    
    result := make([]int, len(nums))
    
    l := 0
    r := len(nums) - 1
    
    for i := len(result) - 1; i >= 0; i-- {
        lVal := nums[l] * nums[l]
        rVal := nums[r] * nums[r]
        
        if rVal >= lVal {
            result[i] = rVal
            r--
        } else {
            result[i] = lVal
            l++
        }
    }
    
    return result
    
}

````