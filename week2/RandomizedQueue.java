import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] items;
    private int[] notNullIndexes;
    private int queueSize = 0;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        notNullIndexes = new int[1];
    }
    
    // is the queue empty?
    public boolean isEmpty()   {
        return queueSize == 0;
    }
    
    // return the number of items on the queue
    public int size()  {
        return queueSize;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        
        if (queueSize == items.length) resize(2 * items.length);
        items[queueSize++] = item;
        
        rebuildIndex();
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        int rInt = notNullIndexes[StdRandom.uniform(queueSize)];

        Item item = items[rInt];
        items[rInt] = null;
        queueSize--;
        
        if (items.length > 4 && queueSize == items.length/4) resize(items.length/2);  
        
        rebuildIndex();
        
        return item;
    }
    
    private void rebuildIndex() {
        int indexInCopy = 0;
        int[] copyIndexes = new int[queueSize];
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null)
                copyIndexes[indexInCopy++] = i;
        }
        notNullIndexes = copyIndexes;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        int rInt = StdRandom.uniform(items.length);
        while (items[rInt] == null)
            rInt = StdRandom.uniform(items.length);
        
        return items[rInt];
    }
    
    // return an iterator over items in order from front to end    
    public Iterator<Item> iterator() { 
        return new RandomQueueIterator(); 
    }  
    
    private class RandomQueueIterator implements Iterator<Item> {
        private int[] shuffleIndexes;
        private int currentIndex = 0;
        
        public RandomQueueIterator() {
            shuffleIndexes = new int[queueSize];
            currentIndex = 0;
        }
        
        public boolean hasNext() {            
            if (currentIndex == 0) {                
                for (int i = 0; i < queueSize; i++)                 
                    shuffleIndexes[i] = i;
                StdRandom.shuffle(shuffleIndexes);
            }
            
            return currentIndex < queueSize && size() >= 0; 
        }
        
        public void remove() { 
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) 
                throw new NoSuchElementException();
            
            return items[shuffleIndexes[currentIndex++]];
        }
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int indexInCopy = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null)
                copy[indexInCopy++] = items[i];
        }
        items = copy;
    }
    
    // unit testing
    public static void main(String[] args)
    {
        RandomizedQueue<Double> testRQueue = new RandomizedQueue<Double>();
        
        int countTest = 10;
        for (int i = 0; i < countTest; i++) 
            testRQueue.enqueue(StdRandom.uniform());
        
        System.out.println("RandomizedQueueue size: " + testRQueue.size());
        
        Iterator<Double> it = testRQueue.iterator();
        
        System.out.println("RandomizedQueue contens:");
        while (it.hasNext()) {
            double d = it.next();
            System.out.println(d);
        }
        
        it = testRQueue.iterator();        
        while (it.hasNext()) {    
            System.out.println("rQueue.sample() = " + testRQueue.sample() + " size = " + testRQueue.size());
            System.out.println("rQueue.dequeue() = " + testRQueue.dequeue() + " size = " + testRQueue.size());            
        }
    }
}