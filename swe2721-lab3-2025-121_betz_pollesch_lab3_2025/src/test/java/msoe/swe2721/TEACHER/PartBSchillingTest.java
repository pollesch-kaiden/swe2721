package msoe.swe2721.lab3;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

import static org.testng.Assert.*;

public class PartBSchillingTest {

    private NumericStringConverterPartB nsc;


    /**
     * This method will setup the tests to be ran later on.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            nsc = new NumericStringConverterPartB();
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        nsc = null;
    }

    @Test(groups = {"profb"})
    public void testNullStringHandling() {
        nsc = new NumericStringConverterPartB();
        // Act and assert
        assertThrows(InvalidParameterException.class, () -> nsc.convertTextToNumbers(null));
    }


    @DataProvider(name = "testSTringConversionValidStringDataProvider")
    public Object[][] testSTringConversionValidStringDataProvider() {
        return new Object[][]{
                {"Hello World", "Hello World", 0}, // C1.  There are 0, 1, 2, 3 or more words to convert.
                {"ABC 1", "ABC uno", -1},
                {"ABC 2 3", "ABC dos tres", -2},
                {"ABC 4 5 6", "ABC cuatro cinco seis", -3},
                {"ABC 7 7", "ABC siete siete", -2}, // C2 The same number is there one or more times.
                {"ABC 8.0", "ABC ochopuntocero", -2}, // Floating point / decimal point in string zero, one, two, or more times.
                {"ABC 8.9.0", "ABC ochopuntonuevepuntocero", -3}, // Floating point / decimal point in string zero, one, two, or more times.
                {"ABC.", "ABC.", 0}, // C4 Period which is not a decimal point at end (yes/no)
                {"Dr. ABC", "Dr. ABC", 0}, // C5 Period which is not a decimal point in middle (yes/no)
                {"Dr. 1. ABC", "Dr. uno. ABC", -1}, // C6 Number with single DP is / is not the last text in the string.
                {"Dr. ABC 1.", "Dr. ABC uno.", -1} // C6 Number with single DP is / is not the last text in the string.
        };
    }

    @Test(dataProvider = "testSTringConversionValidStringDataProvider", groups = {"profb"})
    public void testStringConversion(String expectedReturn, String parameter, int digitChangeCount) throws InterruptedException {
        // Arrange
        nsc = new NumericStringConverterPartB();
        // Act
        String convertedString = nsc.convertTextToNumbers(parameter);

        // Assert
		
		if ((!convertedString.equals(expectedReturn))||(nsc.getDigitCount()!=digitChangeCount)){System.err.println("***********************************FAIL***********************************");
		System.err.println("Expected: " + expectedReturn + " Digit Count: " + digitChangeCount);
		System.err.println("Actual:   " + convertedString + " Digit Count: " + nsc.getDigitCount());
        System.err.flush();Thread.sleep(100);}
		assertEquals(convertedString, expectedReturn);
        assertEquals(nsc.getDigitCount(), digitChangeCount);
    }
}

