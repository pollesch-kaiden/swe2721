package msoe.swe2721.lab2;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class will implement a circular queue. The user can
 * use this class to store items in an efficient manner.
 * @author schilling
 * @param <E> This is the object type that is to be stored on the queue.
 */
public class CircularQueue<E> implements FixedSizeQueueInterface<E> {
    /**
     * The following variable determines the maximum number of items that can be in the queue.
     */
    private final int capacity;

    /**
     * This array stores the actual data within the queue.
     */
    private E[] dataArray;

    /**
     * This variable holds an integer index which represents the end of the
     * array onto which items are added.
     */
    private int tail = 0;

    /**
     * This variable holds an integer index which represents the location from
     * which items are removed from the queue.
     * The item in the head location is the first that was added to the queue.
     */
    private int head = 0;

    /**
     * This variable represents the number of items placed on the queue at the
     * current time.  It must never exceed the capacity.
     */
    private int currentSize = 0;

    /**
     * This constructor will instantiate a new circular queue of the size given
     * as an attribute.
     *
     * @param maxQueueSize This is the capacity of the circular queue.  It must be greater than 0.
     * @throws IllegalArgumentException An IllegalArgumentException will be thrown if an invalid capacity is passed
     *                                  into the method. The capacity must be greater than zero for a
     *                                  proper queue structure to be built.
     */
    public CircularQueue(int maxQueueSize) throws IllegalArgumentException {
        // Invoke the parent constructor.
        super();
        // Make sure that the max size is valid.
        if (maxQueueSize < 0) {
            throw new IllegalArgumentException("Queue capacity invalid.");
        }
        // Set the capacity based upon max size.
        capacity = maxQueueSize;
        // Allocate space to store the items.
        @SuppressWarnings("unchecked")
        E[] temp = (E[]) new Object[capacity];
        // Set the data array up.
        dataArray = temp;
    }

    /**
     * This method will clear out the queue, emptying everything from the queue.
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        // Create a new storage array.
        @SuppressWarnings("unchecked")
        E[] temp = (E[]) new Object[capacity];
        dataArray = temp;

        currentSize = 0;
        head = 0;
        tail = 0;
    }

    /**
     * {@inheritDoc}
     *
     * @param arg0 element whose presence in this collection is to be ensured
     * @return {@inheritDoc} true if this collection changed as a result of the call
     * @throws IllegalStateException will be thrown if the queue is full.
     * @throws NullPointerException  will be thrown if an attempt to add a null entry is made.
     */
    @Override
    public boolean add(E arg0) throws IllegalStateException, NullPointerException {
        boolean retVal = false;
        // Make sure the item is not null.
        if (arg0 == null) {
            throw new NullPointerException("arg0 can not be null");
        }
        // Also check to see if the queue is full.
        else if (isQueueFull()) {
            // The queue is full. Throw an appropriate exception.
            throw new IllegalStateException("The queue is full.");
        } else {
            // Add the item in.
            this.dataArray[tail] = arg0;
            // Update head, tail, size as is applicable.
            tail = (tail + 1) % capacity;
            this.currentSize++;
            retVal = true;
        }
        return retVal;
    }

    /**
     * This method will add the given item onto the tail of the queue.
     * {@inheritDoc}
     *
     * @throws NullPointerException will be thrown if the argument is null.
     */
    @Override
    public boolean offer(E arg0) throws NullPointerException {
        // Make sure the item is not null.
        if (arg0 == null) {
            throw new NullPointerException("arg0 can not be null");
        }

        boolean retVal = false;
        // Determine if there is space to add the item to the queue.
        if (currentSize < capacity) {
            // Place the item into the array.
            this.dataArray[tail] = arg0;
            // Update head, tail, current size as is applicable.
            tail = (tail + 1) % capacity;
            this.currentSize++;
            retVal = true;
        }
        return retVal;
    }

    /**
     * This method will throw a NoSuchElementException if an attempt is made to obtain the
     * element and the queue itself is empty.
     * {@inheritDoc}
     *
     * @throws NoSuchElementException will be thrown if the queue is empty.
     */
    @Override
    public E element() throws NoSuchElementException {
        // Determine if the queue is empty and throw exception if that is true.
        if (currentSize == 0) {
            throw new NoSuchElementException("Circular queue is empty.");
        } else {
            // Obtain the item from the queue that is there.
            return peek();
        }
    }

    /**
     * This method will obtain the next item from the queue.
     * {@inheritDoc}
     */
    @Override
    public E poll() {
        E retVal = null;
        // Check to see if the storage structure is empty.  If not, return the appropriate element and then remove the item from the queue.
        if (currentSize != 0) {
            // Obtain the element to return.
            retVal = dataArray[head];
            // Remove element from the internal storage
            dataArray[head] = null;
            // Update the size, head, and tail as is applicable.
            head = (head + 1) % capacity;
            currentSize++;
        }
        return retVal;
    }

    /**
     * This method will peek at the head of the queue, but will not make any changes to the head.
     * {@inheritDoc}
     */
    @Override
    public E peek() {
        if (currentSize == 0) {
            return null;
        } else {
            return dataArray[head];
        }
    }

    /**
     * This method will throw a NoSuchElementException if an attempt is made to obtain the element and the queue itself is empty.
     * {@inheritDoc}
     *
     * @throws NoSuchElementException will be thrown if the queue is empty.
     */
    @Override
    public E remove() throws NoSuchElementException {
        // Determine if the queue is empty.
        if (currentSize == 0) {
            // If empty, throw the appropriate exception.
            throw new NoSuchElementException("Circular queue is empty.");
        } else {
            // Obtain the element from the internal storage.
            E retVal = dataArray[head];
            // Reset the internal storage.
            dataArray[head] = null;
            // Update head, size, etc.
            head = (head + 1) % capacity;
            currentSize--;
            return retVal;
        }
    }

    /**
     * This method will indicate if the queue is empty.
     *
     * @return return true if the queue is empty and false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (this.currentSize == 0);
    }

    /**
     * This method will return the size of the circular queue.
     *
     * @return The size of the queue will be returned.
     */
    @Override
    public int size() {
        return this.currentSize;
    }

    /**
     * This method will convert the contents of the queue into an array.
     * The head will be at the start and the tail will be at the end of the array.
     * The array will be the exact size of the number of entries on the queue.
     * {@inheritDoc}
     *
     * @return An array of enqueued items will be returned.
     */
    @Override
    public Object[] toArray() {
        Object[] retVal = new Object[this.capacity];

        for (int index = 0; index < currentSize; index++) {
            int myOffset = (head + index) % this.capacity;
            retVal[currentSize-1 - index] = dataArray[myOffset];
        }
        return retVal;
    }

    /**
     * This method will obtain the capacity of the queue, which is the max number of items that can be enqueued.
     *
     * @return The capacity of the queue will be returned, which is the max number of items the queue can hold.
     */
    @Override
    public int getQueueCapacity() {
        return capacity;
    }

    /**
     * This method will return the remaining space on the queue.
     *
     * @return The number of entries that can still be enqueue will be returned.
     */
    @Override
    public int getRemainingQueueSpace() {
        return capacity - currentSize;
    }

    /**
     * This method will indicate if the queue is full.
     *
     * @return true if the queue can not hold any additional items.  False otherwise.
     */
    @Override
    public boolean isQueueFull() {
        return (currentSize >= capacity);
    }

    /**
     * This method is unsupported and shall throw an UnsupportedOperationException if invoked.
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] arg0) {
        throw new UnsupportedOperationException("Method not yet supported.");
    }

    /**
     * This method is unsupported and shall throw an UnsupportedOperationException if invoked.
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Method not yet supported.");
    }

    /**
     * This method is unsupported and shall throw an UnsupportedOperationException if invoked.
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException("Method not yet supported.");
    }

    /**
     * This method is unsupported and shall throw an UnsupportedOperationException if invoked.
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("Method not yet supported.");
    }

    /**
     * This method is unsupported and shall throw an UnsupportedOperationException if invoked.
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new IllegalArgumentException("Method not yet supported.");
    }

     /**
     * This method will search the queue to determine if a matching element is present in the given queue.
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object arg0) {
        for (int i = 0; i < currentSize; i++) {
            int index = (head + i) % capacity;
            if (dataArray[index].equals(arg0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is unsupported and shall throw an UnsupportedOperationException if invoked.
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> arg0) {
        
        // While I was in here, I threw together this implementation as well.
        // I wanted to do this before I forgot everything about freshman data structures.
        boolean returnValue = true;
        for (Object b : arg0)
        {
           if (this.contains(b)) {
             // DO nothing
           }
           else {
             returnValue = false;
           }
        }
        return returnValue;
    }

    /**
     * This method is unsupported and shall throw an UnsupportedOperationException if invoked.
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> arg0) {
        throw new RuntimeException("Method not yet supported.");
    }
}
