/*
 * Course:
 * Spring/Fall
 * MyTests
 * Name: polleschk
 * Created 2/12/2025
 */
package msoe.swe2721.lab2;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

import static org.testng.Assert.*;

/**
 * Course
 * Spring/Fall
 * Class MyTests Purpose: Test class to test the CircularQueue.java class and find the bugs within the code
 *
 * @author polleschk
 * SWE2410-121 Laboratory Assignment #2 Page 2 of 3
 * @version created on 2/12/2025 9:08 PM
 */
public class MyTests {

    private CircularQueue<Integer> queue;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        queue = new CircularQueue<>(5);
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testBasicEnqueueDequeue() {

        // Enqueue 10
        queue.add(10);
        // Enqueue 20
        queue.add(20);
        // Dequeue (expected to remove 10)
        assertEquals(queue.remove(), Integer.valueOf(10));
        // Peek (expected to see 20)
        assertEquals(queue.peek(), Integer.valueOf(20));
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testFillingQueueToCapacity() {
        // Enqueue 1, 2, 3, 4, 5
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        // Attempt to enqueue 6 (should fail — queue is full)
        assertThrows(IllegalStateException.class, () -> queue.add(6));
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testDequeueFromFullQueueAndEnqueueToTestWrapAround() {
        // Precondition: Queue is [1, 2, 3, 4, 5]
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        // Dequeue twice (removes 1, then 2)
        assertEquals(queue.remove(), Integer.valueOf(1));
        assertEquals(queue.remove(), Integer.valueOf(2));
        // Enqueue 6 (wraps around to index 0)
        queue.add(6);
        // Enqueue 7 (wraps around to index 1)
        queue.add(7);
        // Check final state
        assertEquals(queue.peek(), Integer.valueOf(3));
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testDequeueUntilEmptyAndCheckStates() {
        // Precondition: Queue is [3, 4, 5, 6, 7]
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);
        queue.add(7);
        // Dequeue all elements one by one
        assertEquals(queue.remove(), Integer.valueOf(3));
        assertEquals(queue.remove(), Integer.valueOf(4));
        assertEquals(queue.remove(), Integer.valueOf(5));
        assertEquals(queue.remove(), Integer.valueOf(6));
        assertEquals(queue.remove(), Integer.valueOf(7));
        // Check final state
        assertTrue(queue.isEmpty());
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testEdgeConditions() {
        // Dequeue on empty queue: Should indicate “Queue is empty”
        assertThrows(NoSuchElementException.class, () -> queue.remove());
        // Enqueue on full queue: Should indicate “Queue is full”
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        assertThrows(IllegalStateException.class, () -> queue.add(6));
        // Peek on empty queue: Should indicate “Queue is empty”
        queue.clear();
        assertNull(queue.peek());
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testRandomizedOperations() {
        // Perform a random series of enqueues and dequeues
        queue.add(1);
        queue.add(2);
        assertEquals(queue.remove(), Integer.valueOf(1));
        queue.add(3);
        assertEquals(queue.remove(), Integer.valueOf(2));
        queue.add(4);
        queue.add(5);
        assertEquals(queue.remove(), Integer.valueOf(3));
        assertEquals(queue.remove(), Integer.valueOf(4));
        assertEquals(queue.remove(), Integer.valueOf(5));
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testStressTesting() {
        // Repeatedly enqueue and dequeue a large number of elements
        for (int i = 0; i < 1000; i++) {
            queue.add(i);
            assertEquals(queue.remove(), Integer.valueOf(i));
        }
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testPollUpdatesHeadCorrectly() {
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertEquals(queue.poll(), Integer.valueOf(1));
        assertEquals(queue.poll(), Integer.valueOf(2));
        assertEquals(queue.poll(), Integer.valueOf(3));
        assertNull(queue.poll()); // Queue should be empty now
    }

    @Test(groups = { "all", "student", "polleschk"})
    public void testClearResetsHeadAndTail() {
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.clear();
        assertTrue(queue.isEmpty());
        queue.add(4);
        assertEquals(queue.poll(), Integer.valueOf(4)); // Ensure queue works after clear
    }
}