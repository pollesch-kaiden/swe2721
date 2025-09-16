package Homework3_1;/*
 * Course: SWE2721-121
 * Spring 2025
 * Homework 3-1
 * Name: Kaiden Pollesch
 * Created: 2/6/2025
 */

import static org.junit.Assert.*;
import org.junit.Test;
import java.security.InvalidParameterException;

public class FindLastEvenNumberTest {

    // a. Fail to reach the bug
    @Test
    public void testFailToReachBug() {
        // Arrange
        int[] array = {1, 3, 5, 7, 9}; // No even numbers

        // Act
        int result = findLastEvenNumber(array);

        // Assert
        assertEquals(-1, result);
    }

    // b. Reach the bug and cause infection but not propagate
    @Test
    public void testReachBugCauseInfectionNoPropagation() {
        // Arrange
        int[] array = {2}; // Single even number

        // Act
        int result = findLastEvenNumber(array);

        // Assert
        assertEquals(0, result); // Correct, even though bug exists
    }

    // c. Reach the bug, cause infection, and propagate
    @Test
    public void testReachBugCauseInfectionPropagate() {
        // Arrange
        int[] array = {2, 4}; // Two even numbers, last even at index 1

        // Act
        int result = findLastEvenNumber(array);

        // Assert
        assertEquals(1, result); // Will fail because the loop misses last index
    }

    // d. Reach the bug, cause infection, propagate, and reveal
    @Test
    public void testReachBugCauseInfectionPropagateReveal() {
        // Arrange
        int[] array = {1, 3, 5, 4}; // Last even number at the last index

        // Act
        int result = findLastEvenNumber(array);

        // Assert
        assertEquals(3, result); // Will fail and reveal the bug
    }

    // Simulated method for testing
    public int findLastEvenNumber(int[] array) throws InvalidParameterException {
        int retVal = -1;
        if (array == null) {
            throw new InvalidParameterException();
        }
        for (int i = 0; i < array.length - 1; i++) { // Bug: should be i < array.length
            if (array[i] % 2 == 0) {
                retVal = i;
            }
        }
        return retVal;
    }
}
