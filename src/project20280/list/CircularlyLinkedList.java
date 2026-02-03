package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        if (isEmpty()) {
            return null;
        }
        Node<E> curr = tail.next; // Start from head (tail.next)
        for (int j = 0; j < i; j++) {
            curr = curr.next;
        }
        return curr.getData();
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        if (i == 0) {
            addFirst(e);
        } else if (i == size) {
            addLast(e);
        } else {
            Node<E> curr = tail.next; // Start from head
            for (int j = 0; j < i - 1; j++) {
                curr = curr.next;
            }
            Node<E> newNode = new Node<>(e, curr.next);
            curr.setNext(newNode);
            size++;
        }
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        if (i == 0) {
            return removeFirst();
        } else if (i == size - 1) {
            return removeLast();
        } else {
            Node<E> curr = tail.next; // Start from head
            for (int j = 0; j < i - 1; j++) {
                curr = curr.next;
            }
            Node<E> toRemove = curr.next;
            curr.setNext(toRemove.next);
            size--;
            return toRemove.getData();
        }
    }

    public void rotate() {
        if (tail != null) {
            tail = tail.next; // Move tail to the next node (head becomes new tail)
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr;
        Node<E> start;
        boolean firstIteration;

        public CircularlyLinkedListIterator() {
            if (tail != null) {
                curr = (Node<E>) tail.next; // Start from head
                start = curr;
                firstIteration = true;
            } else {
                curr = null;
                start = null;
                firstIteration = false;
            }
        }

        @Override
        public boolean hasNext() {
            if (curr == null) {
                return false;
            }
            return firstIteration || curr != start;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            E res = curr.getData();
            curr = curr.next;
            firstIteration = false;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> head = tail.next;
        E data = head.getData();
        if (size == 1) {
            tail = null;
        } else {
            tail.setNext(head.next);
        }
        size--;
        return data;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        E data = tail.getData();
        if (size == 1) {
            tail = null;
        } else {
            // Find the node before tail
            Node<E> curr = tail.next;
            while (curr.next != tail) {
                curr = curr.next;
            }
            curr.setNext(tail.next);
            tail = curr;
        }
        size--;
        return data;
    }

    @Override
    public void addFirst(E e) {
        if (isEmpty()) {
            tail = new Node<>(e, null);
            tail.setNext(tail); // Point to itself
        } else {
            Node<E> newNode = new Node<>(e, tail.next);
            tail.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        if (isEmpty()) {
            tail = new Node<>(e, null);
            tail.setNext(tail); // Point to itself
        } else {
            Node<E> newNode = new Node<>(e, tail.next);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }


    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail.next; // Start from head
        do {
            sb.append(curr.getData());
            curr = curr.next;
            if (curr != tail.next) {
                sb.append(", ");
            }
        } while (curr != tail.next);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}
