package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Index: " + position + ", Size: " + size);
        }
        Node<E> curr = head;
        for (int i = 0; i < position; i++) {
            curr = curr.getNext();
        }
        return curr.getElement();
    }

    @Override
    public void add(int position, E e) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Index: " + position + ", Size: " + size);
        }
        if (position == 0) {
            addFirst(e);
        } else {
            Node<E> curr = head;
            for (int i = 0; i < position - 1; i++) {
                curr = curr.getNext();
            }
            Node<E> newNode = new Node<>(e, curr.getNext());
            curr.setNext(newNode);
            size++;
        }
    }


    @Override
    public void addFirst(E e) {
        head = new Node<>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            Node<E> curr = head;
            while (curr.getNext() != null) {
                curr = curr.getNext();
            }
            curr.setNext(newNode);
        }
        size++;
    }

    @Override
    public E remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Index: " + position + ", Size: " + size);
        }
        if (position == 0) {
            return removeFirst();
        } else {
            Node<E> curr = head;
            for (int i = 0; i < position - 1; i++) {
                curr = curr.getNext();
            }
            Node<E> toRemove = curr.getNext();
            curr.setNext(toRemove.getNext());
            size--;
            return toRemove.getElement();
        }
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E data = head.getElement();
        head = head.getNext();
        size--;
        return data;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            E data = head.getElement();
            head = null;
            size--;
            return data;
        }
        Node<E> curr = head;
        while (curr.getNext().getNext() != null) {
            curr = curr.getNext();
        }
        E data = curr.getNext().getElement();
        curr.setNext(null);
        size--;
        return data;
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    /**
     * Merges two sorted linked lists into one sorted linked list.
     * Assumes both lists are sorted in ascending order.
     * Requires E to be Comparable.
     */
    public SinglyLinkedList<E> sortedMerge(SinglyLinkedList<E> other) {
        if (!(head == null || head.getElement() instanceof Comparable)) {
            throw new UnsupportedOperationException("Elements must be Comparable for sorted merge");
        }
        SinglyLinkedList<E> result = new SinglyLinkedList<>();
        Node<E> curr1 = this.head;
        Node<E> curr2 = other.head;
        
        @SuppressWarnings("unchecked")
        Comparable<E> comp1 = curr1 != null ? (Comparable<E>) curr1.getElement() : null;
        @SuppressWarnings("unchecked")
        Comparable<E> comp2 = curr2 != null ? (Comparable<E>) curr2.getElement() : null;
        
        while (curr1 != null && curr2 != null) {
            @SuppressWarnings("unchecked")
            Comparable<E> c1 = (Comparable<E>) curr1.getElement();
            @SuppressWarnings("unchecked")
            Comparable<E> c2 = (Comparable<E>) curr2.getElement();
            
            if (c1.compareTo(curr2.getElement()) <= 0) {
                result.addLast(curr1.getElement());
                curr1 = curr1.getNext();
            } else {
                result.addLast(curr2.getElement());
                curr2 = curr2.getNext();
            }
        }
        
        // Append remaining elements
        while (curr1 != null) {
            result.addLast(curr1.getElement());
            curr1 = curr1.getNext();
        }
        while (curr2 != null) {
            result.addLast(curr2.getElement());
            curr2 = curr2.getNext();
        }
        
        return result;
    }

    /**
     * Reverses the linked list in place without creating a copy.
     */
    public void reverse() {
        if (isEmpty() || size == 1) {
            return;
        }
        Node<E> prev = null;
        Node<E> curr = head;
        Node<E> next = null;
        
        while (curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        head = prev;
    }

    /**
     * Creates a deep copy of the linked list.
     */
    public SinglyLinkedList<E> clone() {
        SinglyLinkedList<E> cloned = new SinglyLinkedList<>();
        Node<E> curr = head;
        while (curr != null) {
            cloned.addLast(curr.getElement());
            curr = curr.getNext();
        }
        return cloned;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

    }
}
