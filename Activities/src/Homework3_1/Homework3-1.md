# Homework 3-1

1. The following code has a bug present in it.Given this, define a test case in AAA format which will:
   1. Fail to reach the bug 
   2. Reach the bug and cause infection but not propagate. 
   3. Reach the bug, cause infection, and propagate. 
   4. Reach the bug, cause infection, propagate, and reveal.
```java
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
```

2. How are faults and failures related to testing and debugging? Which do you observe when executing tests and which do you find when you identify the root cause during debugging?
   - Faults are the underlying defects or bugs in the code. Failures are the incorrect behaviors or outputs that occur when the code is executed due to these faults. When executing tests, you observe failures as the tests produce unexpected results. During debugging, you identify the root cause of these failures, which are the faults in the code.