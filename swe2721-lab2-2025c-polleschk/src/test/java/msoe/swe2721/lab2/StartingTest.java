package msoe.swe2721.lab2;

import msoe.swe2721.lab2.CircularQueue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class StartingTest {

    private CircularQueue<Integer> c;

    /**
     * This method will setup the tests to be ran later on.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            c = new CircularQueue<>(10);
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        c = null;
    }


    /**
     * This method will verify that the size and remaining size are correct for
     * the size 10 test scenario used here.
     */
    @Test(groups = { "all", "demo" })
    public void testConstructorSize10() {
        // Arrange
        c = null;

        // Act
        c = new CircularQueue<>(10);

        // Assert
        assertEquals(c.getQueueCapacity(), 10, "Capacity incorrect.");
    }

     /**
     * This method will verify that an invalid constructor which allows invalid
     * sizes is caught.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class}, groups = { "all", "demo" })
    public void testConstructorInvalidSizeNegative() throws IllegalArgumentException {
        // Arrange
        c = null;

        // Act
        c = new CircularQueue<>(-5);

        // Assert - None here, because is nothing to check.
    }

    /**
     * This is a data provider for testing the constructor.
     * @return An array of input values and expected values in the format
     * The structure for the following is the desired capacity of the queue | whether that size is valid or not (i.e. will it throw an exception)
     */
    @DataProvider(name= "testConstructorValidAndInvalidValuesDP")
    public Object[][] validInvalidConstructorTestDataProvider() {
        return new Object [][] {
                {-10, true},
                {1, false},
                {10, false}};
    }

    /**
     * This method will test the constructor with both valid and invalid values.  It also demonstrates one usage of a data provider.
     * @param queueCapacity This is the expected queue capacity.
     * @param exceptionExpected This will be true if the given capacity is expected to throw an exception and false otherwise.
     */
    @Test(dataProvider = "testConstructorValidAndInvalidValuesDP", groups = { "all", "demo" })
    public void testConstructorValidAndInvalidValues(int queueCapacity, boolean exceptionExpected) {
        // Arrange
        c = null;

        if (!exceptionExpected) {
            // Act
            c = new CircularQueue<>(queueCapacity);

            // Assert
            assertEquals(queueCapacity, c.getQueueCapacity(), "The queue capacity is incorrect.");
        }
        else {
            // Act and Assert are combined in this case, as we should see an exception thrown.
            // This is an alternative way of testing for exceptions in testNG.
            assertThrows(IllegalArgumentException.class, () -> c=new CircularQueue<>(queueCapacity));
        }
    }

    private CircularQueue<Integer> queue;

    @Test(groups = { "student" })
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

    @Test(groups = { "student" })
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

    @Test(groups = { "student" })
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

    @Test(groups = { "student" })
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

    @Test(groups = { "student" })
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

    @Test(groups = { "student" })
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

    @Test(groups = { "student" })
    public void testStressTesting() {
        // Repeatedly enqueue and dequeue a large number of elements
        for (int i = 0; i < 1000; i++) {
            queue.add(i);
            assertEquals(queue.remove(), Integer.valueOf(i));
        }
    }

    @Test(groups = { "student" })
    public void testPollUpdatesHeadCorrectly() {
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertEquals(queue.poll(), Integer.valueOf(1));
        assertEquals(queue.poll(), Integer.valueOf(2));
        assertEquals(queue.poll(), Integer.valueOf(3));
        assertNull(queue.poll()); // Queue should be empty now
    }

    @Test(groups = { "student" })
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
