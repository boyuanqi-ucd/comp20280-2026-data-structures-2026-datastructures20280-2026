package project20280.stacksqueues;

import project20280.interfaces.Queue;
import project20280.list.CircularlyLinkedList;

/**
 * Realization of a circular FIFO queue as an adaptation of a
 * CircularlyLinkedList. This provides one additional method not part of the
 * general Queue interface. A call to rotate() is a more efficient simulation of
 * the combination enqueue(dequeue()). All operations are performed in constant
 * time.
 */

public class LinkedCircularQueue<E> implements Queue<E> {

    private CircularlyLinkedList<E> list;

    public LinkedCircularQueue() {
        list = new CircularlyLinkedList<>();
    }

    /**
     * Rotates the queue by moving the front element to the back.
     * This is more efficient than enqueue(dequeue()).
     */
    public void rotate() {
        list.rotate();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        list.addLast(e);
    }

    @Override
    public E first() {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public E dequeue() {
        return list.removeFirst();
    }

}
