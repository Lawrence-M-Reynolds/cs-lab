# Design HashMap

**Source:** LeetCode

## Problem description:
Design a HashMap without using any built-in hash table libraries.

Implement the MyHashMap class:

MyHashMap() initializes the object with an empty map.
void put(int key, int value) inserts a (key, value) pair into the HashMap. If the key already exists in the map, update the corresponding value.
int get(int key) returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
void remove(key) removes the key and its corresponding value if the map contains the mapping for the key.
 

Example 1:

Input
["MyHashMap", "put", "put", "get", "get", "put", "get", "remove", "get"]
[[], [1, 1], [2, 2], [1], [3], [2, 1], [2], [2], [2]]
Output
[null, null, null, 1, -1, null, 1, null, -1]

Explanation
MyHashMap myHashMap = new MyHashMap();
myHashMap.put(1, 1); // The map is now [[1,1]]
myHashMap.put(2, 2); // The map is now [[1,1], [2,2]]
myHashMap.get(1);    // return 1, The map is now [[1,1], [2,2]]
myHashMap.get(3);    // return -1 (i.e., not found), The map is now [[1,1], [2,2]]
myHashMap.put(2, 1); // The map is now [[1,1], [2,1]] (i.e., update the existing value)
myHashMap.get(2);    // return 1, The map is now [[1,1], [2,1]]
myHashMap.remove(2); // remove the mapping for 2, The map is now [[1,1]]
myHashMap.get(2);    // return -1 (i.e., not found), The map is now [[1,1]]
 

Constraints:

0 <= key, value <= 106
At most 10^4 calls will be made to put, get, and remove.

## Solution:
````golang
const numberOfBucketds = 10_000

type MyHashMap struct {
    hashTable[][]int
}


func Constructor() MyHashMap {
    return MyHashMap {
        make([][]int, numberOfBucketds),
    }
}


func calculateHash(key int) int {
    return key % numberOfBucketds
}

func keyBucketIndexAndValue(bucket []int, key int) (int, int) {
    for i := 0; i < len(bucket); i += 2 {
        if bucket[i] == key {
            return i, bucket[i + 1]
        }
    }
    return -1, -1
}


func (this *MyHashMap) Put(key int, value int)  {
    hash := calculateHash(key)
    bucket := this.hashTable[hash]
    
    existingKeyIndex, _ := keyBucketIndexAndValue(bucket, key)
    if existingKeyIndex == -1 {
        bucket = append(bucket, key, value)
    } else {
        bucket[existingKeyIndex + 1] = value
    }
    
    this.hashTable[hash] = bucket
    
    /*
    Calculate key hash
    find bucket
    Add key and value to bucket array
    */
}


func (this *MyHashMap) Get(key int) int {
    hash := calculateHash(key)
    bucket := this.hashTable[hash]
    _, existingKeyValue := keyBucketIndexAndValue(bucket, key)
    
    return existingKeyValue
    /*
    Calculate key hash
    search every second value for the key
    return the value
    */
}


func (this *MyHashMap) Remove(key int)  {
    hash := calculateHash(key)
    bucket := this.hashTable[hash]
    
    existingKeyIndex, _ := keyBucketIndexAndValue(bucket, key)
    if existingKeyIndex == - 1 {
        return
    } 
    
    bucket[existingKeyIndex] = bucket[len(bucket) - 2]
    bucket[existingKeyIndex + 1] = bucket[len(bucket) - 1]
    
    this.hashTable[hash] = bucket[:(len(bucket) - 2)]
    
    /*
    Calculate key hash
    search every second value for the key
    delete both the key and the value from the array
    */
}


/**
 * Your MyHashMap object will be instantiated and called as such:
 * obj := Constructor();
 * obj.Put(key,value);
 * param_2 := obj.Get(key);
 * obj.Remove(key);
 */
````