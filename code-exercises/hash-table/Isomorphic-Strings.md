# Isomorphic Strings

**Source:** LeetCode

## Problem description:
Given two strings s and t, determine if they are isomorphic.

Two strings s and t are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.

 

Example 1:

Input: s = "egg", t = "add"

Output: true

Explanation:

The strings s and t can be made identical by:

    Mapping 'e' to 'a'.
    Mapping 'g' to 'd'.

Example 2:

Input: s = "f11", t = "b23"

Output: false

Explanation:

The strings s and t can not be made identical as '1' needs to be mapped to both '2' and '3'.

Example 3:

Input: s = "paper", t = "title"

Output: true

 

Constraints:

    1 <= s.length <= 5 * 104
    t.length == s.length
    s and t consist of any valid ascii character.


## Solution:
```golang
func isIsomorphic(s string, t string) bool {

    s_checkSlice := make([]uint8, 129)
    t_checkSlice := make([]uint8, 129)
    
    for i := range len(s) {
        sl := s[i] + 1
        tl := t[i] + 1
        
        tl_check := s_checkSlice[sl]
        if tl_check == 0 {
            s_checkSlice[sl] = tl
        } else if tl_check != tl {
            return false
        } 

        sl_check := t_checkSlice[tl]
        if sl_check == 0 {
            t_checkSlice[tl] = sl
        } else if sl_check != sl {
            return false
        } 
    }
    
    return true
}
````