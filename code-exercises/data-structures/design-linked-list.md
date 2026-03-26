# Singly Linked List

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
```
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
```

Constraints:

0 <= index, val <= 1000
Please do not use the built-in LinkedList library.
At most 2000 calls will be made to get, addAtHead, addAtTail, addAtIndex and deleteAtIndex.


## Solution:
````Java
class MyLinkedList {
    
    private Node base;
    private int size;

    public MyLinkedList() {
        base = new Node(1, null);
        size = 0;
    }
    
    
    public int get(int index) {
        if (index > size - 1 || index < 0) {
            return -1;
        }
        
        Node node = base;
        for (int i = -1; i < index; i++) {
            node = node.next;
        }
        
        return node.val;
    }
    
    public void addAtHead(int val) {
        Node head = base.next;
        base.next = new Node(val, head);
        size++;
    }
    
    public void addAtTail(int val) {
        Node last = base;
        while (last.next != null) {
            last = last.next;
        }
        
        last.next = new Node(val, null);
        size++;
    }
    
    public void addAtIndex(int index, int val) {
        if (index > size || index < 0) {
            return;
        }
        
        Node previous = base;
        for (int i = 0; i < index; i++) {
            previous = previous.next;
        }
        
        Node next = previous.next;
        previous.next = new Node(val, next);
        size++;
    }
    
    public void deleteAtIndex(int index) {
        if (index >= size || index < 0) {
            return;
        }
        
        Node previous = base;
        for (int i = 0; i < index; i++) {
            previous = previous.next;
        }
        
        Node toRemove = previous.next;
        previous.next = toRemove.next;
        size--;
    }
    
    class Node {
        private int val;
        private Node next;
        
        private Node (int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
````

````Python
class MyLinkedList:

    class Node:
        def __init__(self, val: int, next: 'Node'):
            self.val = val
            self.next = next
    
    def __init__(self):
        self.base = self.Node(0, None)
        self.size = 0
        

    def get(self, index: int) -> int:
        if index > (self.size - 1) or index < 0:
            return -1;
        
        node = self.base;
        for i in range(-1, index):
            node = node.next
        
        return node.val

    def addAtHead(self, val: int) -> None:
        head = self.base.next
        self.base.next = self.Node(val, head)
        self.size += 1

    def addAtTail(self, val: int) -> None:
        last = self.base
        while last.next is not None:
            last = last.next
            
        last.next = self.Node(val, None)
        self.size += 1

    def addAtIndex(self, index: int, val: int) -> None:
        if index < 0 or index > self.size:
            return
        
        previous = self.base
        for i in range(0, index):
            previous = previous.next
            
        next = previous.next
        previous.next = self.Node(val, next)
        self.size += 1

    def deleteAtIndex(self, index: int) -> None:
        if index < 0 or index >= self.size:
            return
        
        previous = self.base
        for i in range(0, index):
            previous = previous.next
            
        previous.next = previous.next.next
        
        self.size -= 1


# Your MyLinkedList object will be instantiated and called as such:
# obj = MyLinkedList()
# param_1 = obj.get(index)
# obj.addAtHead(val)
# obj.addAtTail(val)
# obj.addAtIndex(index,val)
# obj.deleteAtIndex(index)
````

````golang
````