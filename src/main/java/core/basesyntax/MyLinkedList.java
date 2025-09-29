package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> nodeByIndexInclude0(int index) {
        if (index >= 0 && index <= size) {
            int currentIndex = 0;
            Node<T> currentNode = head;
            while (currentNode != null && currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
            return currentNode;
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> nodeByIndexExclude0(int index) {
        if (index >= 0 && index < size) {
            int currentIndex = 0;
            Node<T> currentNode = head;
            while (currentNode != null && currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
            return currentNode;
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private int indexByValue(T value) {
        Node<T> currentNode = head;
        int currentIndex = 0;
        while (currentNode != null && currentIndex < size) {
            if (Objects.equals(currentNode.value, value)) {
                return currentIndex;
            }
            currentNode = currentNode.next;
            currentIndex++;
        }
        return -1;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(value);
            tail = head;
        } else {
            tail.next = new Node<>(value);
            tail.next.prev = tail;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) { // 4 2 8 3 5 . insert 1 at pos2 .  2 5
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
        Node<T> currentNode = nodeByIndexInclude0(index);
        Node<T> insertNode;
        if (index == size) {
            add(value);
        } else if (currentNode.equals(head)) {
            insertNode = new Node<>(value);
            insertNode.next = currentNode;
            currentNode.prev = insertNode;
            head = insertNode;
            size++;
        } else {
            insertNode = new Node<>(value);
            insertNode.next = currentNode;
            currentNode.prev.next = insertNode;
            insertNode.prev = currentNode.prev;
            currentNode.prev = insertNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return nodeByIndexExclude0(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = nodeByIndexExclude0(index).value;
        nodeByIndexInclude0(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = nodeByIndexExclude0(index);
        T oldValue = currentNode.value;
        if (size == 1) {
            head = null;
            size--;
            return oldValue;
        }
        if (currentNode.equals(head)) {
            head = currentNode.next;
            currentNode.next.prev = null;
        } else if (currentNode.equals(tail)) {
            tail = currentNode.prev;
            currentNode.prev.next = null;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (indexByValue(object) >= 0 && indexByValue(object) < size) {
            T removedValue = remove(indexByValue(object));
            return Objects.equals(removedValue, object);
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
