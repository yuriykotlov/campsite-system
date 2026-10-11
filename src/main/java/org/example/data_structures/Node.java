package org.example.data_structures;

public class Node<T> {
    T data = null;
    Node<T> next = null;

    Node(T data){
        this.data = data;
    }
}
