import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first = null;
    private Node last = null;
    private int dequeSize = 0;
    
    private class Node {
        private Item value;
        private Node next;
        private Node prev;
    }
    
    // construct an empty deque 
    public Deque() {
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return dequeSize == 0;
    }
    
    // return the number of items on the deque    
    public int size() {
        return dequeSize;
    }
    
    // add the item to the front    
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        
        Node oldFirst = first;
        first = new Node();
        first.value = item;
        first.next = oldFirst;
        first.prev = null;
        
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        
        dequeSize++;
    }
    
    // add the item to the end    
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        
        Node oldlast = last;
        last = new Node();
        last.value = item;
        last.next = null;
        last.prev = oldlast;
        
        if (isEmpty()) first = last;
        else oldlast.next = last;   
        
        dequeSize++;
    }
    
    // remove and return the item from the front    
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        Node oldFirst = first;
        first = oldFirst.next;
       
        dequeSize--;
        
        if (isEmpty()) last = null;
        else first.prev = null;
        
        return oldFirst.value;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        Node oldLast = last;
        last = oldLast.prev;
       
        dequeSize--;
        
        if (isEmpty()) first = null;
        else last.next = null;
        
        return oldLast.value;
    }
    
    // return an iterator over items in order from front to end    
    public Iterator<Item> iterator() { 
        return new StackIterator(); 
    }  
    
    private class StackIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() { 
            return current != null; 
        }
        
        public void remove() { 
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) 
                throw new NoSuchElementException();
            
            Item item = current.value;
            current = current.next;
            return item;
        }
    }
    
    // unit testing
    public static void main(String[] args)  
    {
        Deque<Double> testDeque = new Deque<Double>();
        
        testDeque.addFirst(StdRandom.uniform());
        
        testDeque.addFirst(StdRandom.uniform());
        
        testDeque.addLast(StdRandom.uniform());
        
        testDeque.addLast(StdRandom.uniform());
        
        Iterator<Double> it = testDeque.iterator();
        
        System.out.println("Deque contens:");
        while (it.hasNext()) {
            double d = it.next();
            System.out.println(d);
        }
        
        it = testDeque.iterator();        
        while (it.hasNext() && testDeque.size() > 0) {               
            System.out.println("deque.removeLast() = " + testDeque.removeLast() + ", deque size = " + testDeque.size());
        }
    }
}        

