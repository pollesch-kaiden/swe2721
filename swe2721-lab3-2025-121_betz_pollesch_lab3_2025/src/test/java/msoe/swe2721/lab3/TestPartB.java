/*
 * Course: SWE2410-121
 * Spring 2024-2025
 * File header contains class TestPartB
 * Name: betzm
 * Created 2/11/2025
 */
package msoe.swe2721.lab3;

import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

/**
 * Course SWE2410-121
 * Spring 2024-2025
 * Class TestPartB Purpose: Test cases for NumericStringConverterPartB
 *
 * @author betzm
 * @version created on 2/11/2025 3:38 PM
 */
public class TestPartB {
    private NumericStringConverterPartB nsc;
    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        nsc = new NumericStringConverterPartB();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        nsc = null;
    }

    @DataProvider(name = "testbDataProvider")
    public Object[][] testString() {
        return new Object[][] {
                {"Pi has a value of trespuntounocuatro.", "Pi has a value of 3.14.", -4},
                {"Hello World", "Hello World", 0},
                {"ABC unodostres", "ABC 123", -3},
                {"The end of class happens at tres.", "The end of class happens at 3.", -1},
                {"Dr. James is the tresrd in a long line of Drs.", "Dr. James is the 3rd in a long line of Drs.", -1},
                {"The end of class happens at trespuntocero.", "The end of class happens at 3.0.", -3}
        };
    }

//    @Test (dataProvider = "testbDataProvider", groups = {"testb"})
//    public void testConvertTextToNumbers_singleWord(String parameter, String expected, int expectedCount) {
//        NumericStringConverterPartB converter = new NumericStringConverterPartB();
//        String result = converter.convertTextToNumbers("one");
//        assertEquals(result, "1");
//        assertEquals(converter.getDigitCount(), 1);
//    }
    @Test (dataProvider = "testbDataProvider", groups = {"testb"})
    public void testConvertTextToNumbers_multipleWords(String parameter, String expected, int expectedCount) {
        nsc = new NumericStringConverterPartB();

        String convertedString = nsc.convertTextToNumbers(parameter);

        assertEquals(convertedString, expected);
        assertEquals(nsc.getDigitCount(), expectedCount);
    }
}
