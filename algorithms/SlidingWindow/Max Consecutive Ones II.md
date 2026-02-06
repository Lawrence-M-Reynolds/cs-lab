# Max Consecutive Ones II
## Problem
Given a binary array nums, return the maximum number of consecutive 1's in the array if you can flip at most one 0.

 

Example 1:

Input: nums = [1,0,1,1,0]
Output: 4
Explanation: 
- If we flip the first zero, nums becomes [1,1,1,1,0] and we have 4 consecutive ones.
- If we flip the second zero, nums becomes [1,0,1,1,1] and we have 3 consecutive ones.
The max number of consecutive ones is 4.
Example 2:

Input: nums = [1,0,1,1,0,1]
Output: 4
Explanation: 
- If we flip the first zero, nums becomes [1,1,1,1,0,1] and we have 4 consecutive ones.
- If we flip the second zero, nums becomes [1,0,1,1,1,1] and we have 4 consecutive ones.
The max number of consecutive ones is 4.
 

Constraints:

1 <= nums.length <= 105
nums[i] is either 0 or 1.
 

Follow up: What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?

## Basic solution - for a fixed array.
**Python**

class Solution:
    
    def findMaxConsecutiveOnes(self, nums: List[int]) -> int:
        
        result = 0
        zeroCount = 0
        
        l = 0
        
        for r, num in enumerate(nums):
            if num == 0:
                zeroCount += 1
                
            while zeroCount > 1:
                if nums[l] == 0:
                    zeroCount -= 1
                l += 1
                
            result = max(result, r - l + 1)
                
                
        return result

## Advanced solution for an iterable/stream

class Solution:
    
    @staticmethod
    def findMaxConsecutiveOnesStream(iterator: iter) -> int:
        result = 0
        
        l = 0
        midZero = -1
        
        for r, num in enumerate(iterator):
            if num == 0:
                if midZero == -1:
                    midZero = r
                else:
                    l = midZero + 1
                    midZero = r
                    
            result = max(result, (r - l + 1))
                    
        return result
       
    def findMaxConsecutiveOnes(self, nums: List[int]) -> int:
        return self.findMaxConsecutiveOnesStream(iter(nums))
            