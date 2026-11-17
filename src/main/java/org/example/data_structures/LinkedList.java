package org.example.data_structures;

import org.example.model.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.Objects;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Adds a new node of non-null type T.
    public void add(T item) {
        Objects.requireNonNull(item, "Items added cannot be of type `null`.");

        Node<T> newNode = new Node<>(item);

        if(head == null){
            head = tail = newNode;
        } else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Gets and returns a node by index.
    public T get(int index) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("The index supplied (" + index + ") is out of bounds.");
        }

        Node<T> node = head;
        for(int i = 0; i < index; i++) node = node.next;

        return node.data;
    }

    /*
       Finds and returns the first node that matches the supplied filter.
       Example:
            filter = guest -> guest.getId() == id
            filter.test(node.data), where node.data is now 'guest'
            find returns Object T if the test returns true
    */
    public T find(Predicate<T> filter) {
        for(Node<T> node = head; node != null; node = node.next){
            if(filter.test(node.data)) return node.data;
        }
        return null;
    }

    // Removes a node that has the item of type T.
    public boolean remove(T item) {
        if(item == null) return false;

        // save a reference to the previous node when
        // removing the target node to link everything back up
        Node<T> previous = null;

        for(Node<T> current = head; current != null; previous = current, current = current.next){
            if(!current.data.equals(item)) continue;

            // removing first node (node = head)
            if(previous == null){
                head = current.next;
            } else {
                // set the previous node's .next to reference the
                // node AFTER the removed one
                previous.next = current.next;
            }

            if(current == tail) tail = previous;

            size--;
            return true;
        }
        return false;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if(current == null){
                    throw new NoSuchElementException("There are no more elements in this linked list.");
                }

                T data = current.data;
                current = current.next;

                return data;
            }
        };
    }
}
