package msoe.swe2721.lab3;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class PartASchillingTest {

    private NumericStringConverterPartA nsc;


    /**
     * This method will setup the tests to be ran later on.
     */
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

    @Test(groups = {"profa"})
    public void testNullStringHandling() {
        nsc = new NumericStringConverterPartA();
        // Act and assert
        assertThrows(InvalidParameterException.class, () -> nsc.convertNumbersToText(null));
    }


    @DataProvider(name = "testSTringConversionValidStringDataProvider")
    public Object[][] testSTringConversionValidStringDataProvider() {
        return new Object[][]{
                {"Hello World", "Hello World", 0}, // C1.  There are 0, 1, 2, 3 or more words to convert.
                {"ABC 1", "ABC uno", 1},
                {"ABC 2 3", "ABC dos tres", 2},
                {"ABC 4 5 6", "ABC cuatro cinco seis", 3},
                {"ABC 7 7", "ABC siete siete", 2}, // C2 The same number is there one or more times.
                {"ABC 8.0", "ABC ochopuntocero", 2}, // Floating point / decimal point in string zero, one, two, or more times.
                {"ABC 8.9.0", "ABC ochopuntonuevepuntocero", 3}, // Floating point / decimal point in string zero, one, two, or more times.
                {"ABC.", "ABC.", 0}, // C4 Period which is not a decimal point at end (yes/no)
                {"Dr. ABC", "Dr. ABC", 0}, // C5 Period which is not a decimal point in middle (yes/no)
                {"Dr. 1. ABC", "Dr. uno. ABC", 1}, // C6 Number with single DP is / is not the last text in the string.
                {"Dr. ABC 1.", "Dr. ABC uno.", 1} // C6 Number with single DP is / is not the last text in the string.
        };
    }

    @Test(dataProvider = "testSTringConversionValidStringDataProvider", groups = {"profa"})
    public void testStringConversion(String parameter, String expectedReturn, int digitChangeCount) throws InterruptedException{
        // Arrange
        nsc = new NumericStringConverterPartA();
        // Act
        String convertedString = nsc.convertNumbersToText(parameter);

        // Assert
		if ((!convertedString.equals(expectedReturn))||(nsc.getDigitCount()!=digitChangeCount)){System.err.println("***********************************FAIL***********************************");
		System.err.println("Expected: " + expectedReturn + " Digit Count: " + digitChangeCount);
		System.err.println("Actual:   " + convertedString + " Digit Count: " + nsc.getDigitCount());
        System.err.flush();Thread.sleep(100);};
		assertEquals(convertedString, expectedReturn);
        assertEquals(nsc.getDigitCount(), digitChangeCount);
    }
}

