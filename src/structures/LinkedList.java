/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

/**
 *
 * @author andresimery
 */
public class LinkedList<T> {
    private Node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
        }
        size++;
    }

    public T removeFirst() {
        if (head == null) {
            return null;
        }
        T data = head.getData();
        head = head.getNext();
        size--;
        return data;
    }

    public T remove(T data) {
        if (head == null) {
            return null; // Lista vacía, no hay nada que eliminar
        }

        if (head.getData().equals(data)) {
            T removedData = head.getData();
            head = head.getNext();
            size--;
            return removedData;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(data)) {
                T removedData = current.getNext().getData();
                current.setNext(current.getNext().getNext());
                size--;
                return removedData;
            }
            current = current.getNext();
        }

        return null; // No se encontró el dato en la lista
    }



    public boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        return size;
    }

    public Node<T> getHead() {
        return head;
    }
}

