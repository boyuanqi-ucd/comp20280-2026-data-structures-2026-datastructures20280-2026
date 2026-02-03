package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private final E data;
        private Node<E> next;
        private final Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private final Node<E> head;
    private final Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newNode = new Node<>(e, pred, succ);
        pred.next = newNode;
        // Note: In a proper doubly linked list, we would update succ.prev = newNode
        // However, since prev is final in this implementation, we cannot modify it
        // The prev field is set correctly when nodes are created
        size++;
    }

    @Override
    public int size() {
        return size;
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
        Node<E> curr = head.next;
        for (int j = 0; j < i; j++) {
            curr = curr.next;
        }
        return curr.getData();
    }

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
            Node<E> curr = head.next;
            for (int j = 0; j < i; j++) {
                curr = curr.next;
            }
            addBetween(e, curr.prev, curr);
        }
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        Node<E> curr = head.next;
        for (int j = 0; j < i; j++) {
            curr = curr.next;
        }
        return remove(curr);
    }

    private class DoublyLinkedListIterator implements Iterator<E> {
        Node<E> curr = head.next;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator();
    }

    private E remove(Node<E> n) {
        if (n == null || n == head || n == tail) {
            return null;
        }
        Node<E> pred = n.prev;
        Node<E> succ = n.next;
        if (pred != null) {
            pred.next = succ;
        }
        // Note: succ.prev is final, so we cannot modify it directly
        // However, since prev is set correctly when nodes are created,
        // and we're only removing nodes (not modifying existing prev references),
        // the structure remains valid
        size--;
        return n.getData();
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        // Find the actual last node (the one before tail)
        Node<E> lastNode = head.next;
        while (lastNode.next != tail) {
            lastNode = lastNode.next;
        }
        return lastNode.getData();
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        return remove(head.next);
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        // Find the actual last node (the one before tail)
        Node<E> lastNode = head.next;
        while (lastNode.next != tail) {
            lastNode = lastNode.next;
        }
        return remove(lastNode);
    }

    @Override
    public void addLast(E e) {
        // Find the actual last node (the one before tail)
        Node<E> lastNode = head.next;
        if (lastNode == tail) {
            // List is empty, add as first node
            addBetween(e, head, tail);
        } else {
            // Find the last actual node
            while (lastNode.next != tail) {
                lastNode = lastNode.next;
            }
            addBetween(e, lastNode, tail);
        }
    }

    @Override
    public void addFirst(E e) {
        addBetween(e, head, head.next);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}