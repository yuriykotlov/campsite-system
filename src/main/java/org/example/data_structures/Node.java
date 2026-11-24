package org.example.data_structures;

import java.io.Serializable;

public class Node<T> implements Serializable {
    T data = null;
    Node<T> next = null;

    Node(T data){
        this.data = data;
    }
}
