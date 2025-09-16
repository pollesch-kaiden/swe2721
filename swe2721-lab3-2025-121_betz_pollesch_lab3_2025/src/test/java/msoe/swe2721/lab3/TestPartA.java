package msoe.swe2721.lab3;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class TestPartA {

    private NumericStringConverterPartA nsc;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            nsc = new NumericStringConverterPartA();
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        nsc = null;
    }

    // Reachability - Enters the program but does not
    @Test(groups = { "testa"})
    public void testReachability() {
        // Arrange
        nsc = new NumericStringConverterPartA();

        // Act
        String text = "Hello World";
        String expected = "Hello World";

        // Assert
        assertEquals(nsc.convertNumbersToText(text), expected);
    }

    // Infection
    @Test
    public void testInfection() {
        // Arrange
        NumericStringConverterPartA nsc = new NumericStringConverterPartA();

        // Act
        String text = "The end of class happens at 3";
        String expected = "The end of class happens at tres";

        // Assert
        assertEquals(nsc.convertNumbersToText(text), expected);
    }

    // Propagation
    @Test
    public void testPropagation() {
        // Arrange
        NumericStringConverterPartA nsc = new NumericStringConverterPartA();

        // Act
        String text = "Dr. Smith at 7 has dinner";
        String expected = "Dr. Smith at siete has dinner";

        // Assert
        assertEquals(nsc.convertNumbersToText(text), expected);
    }

    // Revelation - Last test fails on decimal
    @Test
    public void testRevelation() {
        // Arrange
        NumericStringConverterPartA nsc = new NumericStringConverterPartA();

        // Act
        String text = "Pi has a value of 3.14";
        String expected = "Pi has a value of trespuntounocuatro";

        // Assert
        assertEquals(nsc.convertNumbersToText(text), expected);
    }
}
