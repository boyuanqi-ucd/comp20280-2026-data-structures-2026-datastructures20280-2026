Starting repository for `Data Structures` COMP20280 2025-2026
EXERCISES 1

Q5: Unit Tests

I ran all the JUnit5 test classes (such as SinglyLinkedListTest and DoublyLinkedListTest).
All tests passed successfully.
The tests cover the main operations, but they may not include every possible edge case.



Q6: Difference between singly linked list and circular linked list

In a singly linked list, the last node points to null.
In a circular linked list, the last node points back to the first node, so the list forms a loop.



Q7: When would you prefer a linked list over an array?

A linked list is useful when the size changes often.
It is also better when we need frequent insertions or deletions, because elements do not need to be shifted like in an array.



Q8: Two use-cases for a circular linked list

Use-case 1: Round-robin scheduling
Circular linked lists can be used when tasks need to take turns in a cycle, such as CPU scheduling.

Use-case 2: Music playlist or buffer system
They are also useful for playlists or streaming buffers, where after the last item, it goes back to the first automatically.