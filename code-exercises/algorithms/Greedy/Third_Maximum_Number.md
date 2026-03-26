Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not exist, return the maximum number.

 

Example 1:

Input: nums = [3,2,1]
Output: 1
Explanation:
The first distinct maximum is 3.
The second distinct maximum is 2.
The third distinct maximum is 1.
Example 2:

Input: nums = [1,2]
Output: 2
Explanation:
The first distinct maximum is 2.
The second distinct maximum is 1.
The third distinct maximum does not exist, so the maximum (2) is returned instead.
Example 3:

Input: nums = [2,2,3,1]
Output: 1
Explanation:
The first distinct maximum is 3.
The second distinct maximum is 2 (both 2's are counted together since they have the same value).
The third distinct maximum is 1.
 

Constraints:

1 <= nums.length <= 104
-231 <= nums[i] <= 231 - 1
 

Follow up: Can you find an O(n) solution?

===============

````Java
class Solution {
    public int thirdMax(int[] nums) {
        short maxP = -1;
        short midP = -1;
        short minP = -1;

        for (short i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (maxP == -1 || nums[maxP] < num) {
                minP = midP;
                midP = maxP;
                maxP = i;
            } else if (midP == -1 || nums[midP] < num) {
                if (nums[maxP] != num) {
                    minP = midP;
                    midP = i;   
                }
            } else if (minP == -1 || nums[minP] < num) {
                if (nums[midP] != num) {
                    minP = i;
                }
            }
        }
        return minP == -1 ? nums[maxP] : nums[minP];
    }
}
````

````golang
func thirdMax(nums []int) int {
    
    var maxP int = -1
    var midP int = -1
    var minP int = -1
    
    for i, num := range nums {
        if maxP == -1 || nums[maxP] < num {
            minP = midP
            midP = maxP
            maxP = i
        } else if midP == -1 || nums[midP] < num {
            if nums[maxP] != num {
                minP = midP
                midP = i 
            }
        } else if minP == -1 || nums[minP] < num {
            if nums[midP] != num {
                minP = i
            }
        }
    }
    
    if minP == -1 {
        return nums[maxP]
    }
    
    return nums[minP]
}
````