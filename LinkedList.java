/*
  Author: Vishnu Varadhan

  Date: 3/10/2024

  Name: LinkedList.java

  Purpose: Implements a generic LinkedList that supports basic operations such as add, remove,
               and findMin, with iteration capability. This class also adheres to the Stack interface,
               offering methods like pop, peek, push. 
*/



import java.util.Comparator;
import java.util.Iterator;  
import java.util.NoSuchElementException;
import java.lang.StringBuilder;


public class LinkedList<T> implements Iterable<T>, Stack<T>{
    private Node head; 
    private Node tail;
    private int size;


    private class LLIterator implements Iterator<T>{
        private Node nextNode;

        public LLIterator(Node head){
            nextNode = head;
        }

        public boolean hasNext(){
            return nextNode != null;
        }

        public T next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T item = nextNode.getData();
            nextNode = nextNode.getNext();
            return item;
        }

        public void remove(){
            System.out.println("Not Supported");
        }

    }

    private class Node{
        private Node next;
        private T value;

        public Node(T item){
            next = null;
            value = item;   
        }

        public T getData(){
            return value;
        }

        public void setNext(Node n){
            next = n;
        }

        public Node getNext(){
            return next;
        }
    }

    public LinkedList(){
        head = null;
        size = 0;
        tail = null;
    }

    public int size(){
        return size;
    }

    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public String toString(){
        if(head == null){
            return "{}";
        }else{
            StringBuilder linkedList = new StringBuilder();
            linkedList.append("{");
            Node currentNode = head;
            while(currentNode != null){
                linkedList.append(currentNode.value); 
                if(currentNode.getNext() != null) {
                    linkedList.append(", ");
                }
                currentNode = currentNode.getNext();
            }
            linkedList.append("}");
            return linkedList.toString();
        }
    }

    //adds item to start of list
    public void add(T item){
        Node newHead = new Node(item);
        newHead.setNext(head);
        head = newHead;
        if(size == 0){
        tail = newHead;
        }
        size++;
    }

    public boolean contains(Object o){
        Node currentNode = head;
        while(currentNode != null){
            if(o.equals(currentNode.getData())){
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    public T get(int index){
        Node currentNode = head;
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }else if(index == size()-1){
            return getLast();
        }else if(index == 0){
            return getFirst();
        }else{
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getData();
    }
    }

    public T remove(){
        Node firstNode = head;
        if(firstNode == null){
            tail = null;
            head = null;
            throw new NoSuchElementException("Cannot remove from an empty list.");
        }
        head = firstNode.getNext();
        size--;
        return firstNode.getData();
    }


    public void add(int index, T item) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }else if(index == 0){
            add(item);
            return;
        }else if (index == size()){
            addLast(item);
            return;
        }
        else {
            Node nodeToAdd = new Node(item);
            Node currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }
            nodeToAdd.setNext(currentNode.getNext());
            currentNode.setNext(nodeToAdd);
        }
        size++;
    }

    public void addLast(T item){
        Node nodeToAdd = new Node(item);
        if(size == 0){
            tail = nodeToAdd;
            head = nodeToAdd;
            size++;
        }else{
        tail.setNext(nodeToAdd);
        tail = nodeToAdd;
        size++;
        }
    }

    public T remove(int index){
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }else if(index == 0){
            return remove();
        }else if(index == size()-1){
            return removeLast(); 
        }else{
        Node currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.getNext();
        }
        T value = currentNode.getNext().getData();
        currentNode.setNext(currentNode.getNext().getNext());
        size--;
        return value;
    }
    }

    public T removeLast(){
        Node currNode = head;
        for (int i = 0; i < size()-2; i++) {
            currNode = currNode.getNext();
        }
        Node prevTail = currNode.getNext();
        currNode.setNext(null);
        tail = currNode;
        size--;
        return prevTail.getData();
    }




    public boolean equals(Object o) {
        if (this == o){return true;}

        if (!(o instanceof LinkedList)){return false;}
        
        LinkedList<T> other = (LinkedList<T>) o;
        
        if (this.size() != other.size()){return false;}
        
        Node currentThisNode = this.head;
        Node currentOtherNode = other.head;
        while (currentThisNode != null && currentOtherNode != null) { 
            if (!currentThisNode.getData().equals(currentOtherNode.getData())) {
                return false;
            }
            currentThisNode = currentThisNode.getNext();
            currentOtherNode = currentOtherNode.getNext();
        }
        return true;
    }

    public Iterator iterator(){
        return new LLIterator(head);
    }

    public T getFirst(){
        if(head == null){
            throw new NoSuchElementException("No such Element");
        }
        return head.getData();
    }

    public T getLast(){
        return tail.getData();
    }


    //for queue to keep constant. Remove from front, add to end. 
    public void offer(T item){
        addLast(item);
    }


    public T poll(){
        return remove();
    }

    public T peek(){
        //Cool ternary operator I just learned . if else statement in one line. condition ? if true return this : if false return this. 
        return head == null ? null : head.getData();
    }


    //For reflection 2, finds and returns min val in jobs
    public T findMin(Comparator<T> comparator) {
        if (size == 0) {
            return null; 
        }
    
        T min = head.value; 
        Node current = head.next; 
    
        while (current != null) {
            if (comparator.compare(current.value, min) < 0) {
                min = current.value;
            }
            current = current.next;
        }
        return min;
    }

    //For reflection 2, finds and removes min val in jobs
    public T removeMin(Comparator<T> comparator) {
        if (size == 0) {
            return null; 
        }
    
        Node current = head;
        Node minPrev = null; 
        Node minNode = head; 
        T min = head.value; 
    
        while (current.next != null) {
            if (comparator.compare(current.next.value, min) < 0) {
                min = current.next.value;
                minPrev = current;
                minNode = current.next;
            }
            current = current.next;
        }
    

        if (minNode == head) {
            head = head.next; 
            if (head == null) { 
                tail = null;
            }
        } else {
            minPrev.next = minNode.next;
           
            if (minNode == tail) {
                tail = minPrev;
            }
        }
    
        size--;
        return min;
    }

    public T pop(){
       return remove();
    }

    public void push(T item){
        add(item);
    }

}