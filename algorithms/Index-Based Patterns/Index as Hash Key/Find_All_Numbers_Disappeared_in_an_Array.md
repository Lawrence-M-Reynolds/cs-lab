
## Problem description:

Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums.

 

Example 1:

Input: nums = [4,3,2,7,8,2,3,1]
Output: [5,6]
Example 2:

Input: nums = [1,1]
Output: [2]
 

Constraints:

n == nums.length
1 <= n <= 105
1 <= nums[i] <= n
 

Follow up: Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.


## Solution:
````Java
class Solution {
        /*
        - var resultSize = 0;
        
        - For each value in the array
            - set the index of the array that the mod(value) points to (-1) to the negative equivalent
            - If the value was already negative then resultSize++
        
        - Create result array of length resultSize.
        
        - For each value in the array
            - if the value is negative, add that index to the result array (moving the index).
        
        */

    public List<Integer> findDisappearedNumbers(int[] nums) {
        
        var resultSize = 0;
        for (int num : nums) {
            var index = Math.abs(num) - 1;
            if (nums[index] < 1) {
                resultSize++;
            } else {
                nums[index] = -nums[index];
            }
        }
        
        List<Integer> result = new ArrayList<>(resultSize);
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }
        
        return result;
    }
}
````

````Python
class Solution:
    def findDisappearedNumbers(self, nums: List[int]) -> List[int]:
            
        for num in nums :
            index = abs(num) - 1
            if nums[index] > 0 :
                nums[index] = -nums[index]
                
        return [i + 1 for i, num in enumerate(nums) if num > 0]
````

````golang
func abs(x int) int {
    if x < 0 {
        return -x
    }
    return x
}

func findDisappearedNumbers(nums []int) []int {
    resultSize := 0
    
    for _, num := range nums {
        index := abs(num) - 1
        if nums[index] < 1 {
            resultSize++
        } else {
            nums[index] = -nums[index]
        }
    }
    
    result := make([]int, resultSize)
    j := 0
    for i, num := range nums {
        if num > 0 {
            result[j] = i + 1
            j++
        }
    }
    
    return result
}
````