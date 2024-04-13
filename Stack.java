/*
  Author: Professor Lage

  Date: 4/2/2024

  Name: Stack.java

  Purpose: Implements a stack interface for our custom LinkedList class
*/


public interface Stack<T> {

    /**
     * Returns the number of items in the stack.
     * 
     * @return the number of items in the stack.
     */
    public int size();

    /**
     * Returns the item on the top of the stack.
     * 
     * @return the item on the top of the stack.
     */
    public T peek();

    /**
     * Returns and removes the item on the top of the stack.
     * 
     * @return the item on the top of the stack.
     */
    public T pop();

    /**
     * Adds the given {@code item} to the top of the stack.
     * 
     * @param item the item to be added.
     */
    public void push(T item);

    public boolean isEmpty();
}