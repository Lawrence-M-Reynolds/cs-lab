# Design Linked List (double)

**Source:** LeetCode

## Problem description:

Design your implementation of the linked list. You can choose to use a singly or doubly linked list.
A node in a singly linked list should have two attributes: val and next. val is the value of the current node, and next is a pointer/reference to the next node.
If you want to use the doubly linked list, you will need one more attribute prev to indicate the previous node in the linked list. Assume all nodes in the linked list are 0-indexed.

Implement the MyLinkedList class:

MyLinkedList() Initializes the MyLinkedList object.
int get(int index) Get the value of the indexth node in the linked list. If the index is invalid, return -1.
void addAtHead(int val) Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
void addAtTail(int val) Append a node of value val as the last element of the linked list.
void addAtIndex(int index, int val) Add a node of value val before the indexth node in the linked list. If index equals the length of the linked list, the node will be appended to the end of the linked list. If index is greater than the length, the node will not be inserted.
void deleteAtIndex(int index) Delete the indexth node in the linked list, if the index is valid.
 

Example 1:

Input
["MyLinkedList", "addAtHead", "addAtTail", "addAtIndex", "get", "deleteAtIndex", "get"]
[[], [1], [3], [1, 2], [1], [1], [1]]
Output
[null, null, null, null, 2, null, 3]

Explanation
MyLinkedList myLinkedList = new MyLinkedList();
myLinkedList.addAtHead(1);
myLinkedList.addAtTail(3);
myLinkedList.addAtIndex(1, 2);    // linked list becomes 1->2->3
myLinkedList.get(1);              // return 2
myLinkedList.deleteAtIndex(1);    // now the linked list is 1->3
myLinkedList.get(1);              // return 3
 

Constraints:

0 <= index, val <= 1000
Please do not use the built-in LinkedList library.
At most 2000 calls will be made to get, addAtHead, addAtTail, addAtIndex and deleteAtIndex.

## Solution:
````golang
type MyLinkedList struct {
    head *LinkedListNode
    tail *LinkedListNode
    size int
}

type LinkedListNode struct {
    next *LinkedListNode
    prev *LinkedListNode
    val int
}


func Constructor() MyLinkedList {
    return MyLinkedList {}
}


func (this *MyLinkedList) Get(index int) int {
    node := this.getNodeAtIndex(index)
    if node == nil {
        return -1
    }
    
    return node.val
}

func (this *MyLinkedList) getNodeAtIndex(index int) *LinkedListNode {
    if index < 0 || index >= this.size {
        return nil
    }
    
    var curr *LinkedListNode
    if index < this.size / 2 {
        curr = this.head
        for p := 0; p < index; p++ {
            curr = curr.next
        }
    } else {
        curr = this.tail
        for p := this.size - 1; p > index; p-- {
            curr = curr.prev
        }
    }
    return curr
}


func (this *MyLinkedList) AddAtHead(val int)  {
    newNode := &LinkedListNode {
        this.head,
        nil,
        val,
    }
    
    if this.head != nil {
        this.head.prev = newNode
    } else {
        this.tail = newNode    
    }
    
    this.head = newNode
    this.size++
}


func (this *MyLinkedList) AddAtTail(val int)  {
    newNode := &LinkedListNode {
        nil,
        this.tail,
        val,
    }
    
    if this.tail != nil {
        this.tail.next = newNode
    } else {
        this.head = newNode    
    }
    
    this.tail = newNode
    this.size++
}


func (this *MyLinkedList) AddAtIndex(index int, val int)  {
    if index < 0 || index > this.size {
        return
    } else if index == this.size {
        this.AddAtTail(val)
    } else if index == 0 {
        this.AddAtHead(val)
    } else {
        replaceNode := this.getNodeAtIndex(index)
        newNode := &LinkedListNode {
            replaceNode,
            replaceNode.prev,
            val,
        }
        
        replaceNode.prev.next = newNode
        replaceNode.prev = newNode
        this.size++
    }
}


func (this *MyLinkedList) DeleteAtIndex(index int)  {
    deleteNode := this.getNodeAtIndex(index)
    if deleteNode == nil {
        return
    }
    
    if deleteNode.next == nil {
        this.tail = deleteNode.prev
    } else {
        deleteNode.next.prev = deleteNode.prev
    }
    
    if deleteNode.prev == nil {
        this.head = deleteNode.next
    } else {
        deleteNode.prev.next = deleteNode.next
    }
    
    this.size--
}


/**
 * Your MyLinkedList object will be instantiated and called as such:
 * obj := Constructor();
 * param_1 := obj.Get(index);
 * obj.AddAtHead(val);
 * obj.AddAtTail(val);
 * obj.AddAtIndex(index,val);
 * obj.DeleteAtIndex(index);
 */
````