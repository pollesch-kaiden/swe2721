package msoe.swe2721.lab2;

import java.util.Queue;

/**
 * This interface defines a FixedSizeQueue. In essence, this is a circular queue
 * in which the number of elements in the queue must be decided at instantiation
 * time.
 * 
 * @author wws
 * 
 * @param <E> This is the data type that is to be stored on the queue.
 */
public interface FixedSizeQueueInterface<E> extends Queue<E> {
	/**
	 * This method will obtain the maximum size for the queue, namely the number
	 * of elements that can be held on the queue without it overflowing.
	 * 
	 * @return The maximum size of the queue will be returned.
	 */
	public int getQueueCapacity();

	/**
	 * This method will return the remaining queue space, or the number of
	 * elements that can be added to the queue without it overflowing.
	 * 
	 * @return The number of items that can be added to the queue will be
	 *         returned.  It is essentially capacity - size.
	 */
	public int getRemainingQueueSpace();

	/**
	 * This method will indicate whether the given queue is full or not.
	 * 
	 * @return The method will return true if the queue is full and false
	 *         otherwise.
	 */
	public boolean isQueueFull();

}
