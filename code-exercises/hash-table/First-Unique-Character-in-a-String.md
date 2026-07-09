# First Unique Character in a String

**Source:** LeetCode

## Problem description:
Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.

 

Example 1:

Input: s = "leetcode"

Output: 0

Explanation:

The character 'l' at index 0 is the first character that does not occur at any other index.

Example 2:

Input: s = "loveleetcode"

Output: 2

Example 3:

Input: s = "aabb"

Output: -1

 

Constraints:

1 <= s.length <= 105
s consists of only lowercase English letters.

## Solution:
````golang
func firstUniqChar(s string) int {
    letterLookupTable := make([]int, 26)
    
    for i := range len(s) {
        lI := s[i] - 'a'
        letterLookupTable[lI] += 1
    }
    
    for i := range len(s) {
        lI := s[i] - 'a'
        if letterLookupTable[lI]  == 1 {
            return i
        }
    }
    
    return -1
}
````