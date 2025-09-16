package msoe.swe2721.lab4_5.provided;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

import static org.testng.Assert.*;


public class MoneyTest {


    @BeforeMethod(alwaysRun = true)
    public void setUp() {

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

    }


    @DataProvider(name = "constructorDataProvider")
    public Object[][] constructorDP() {
        return new Object[][]{
                {1.25, false},
                {-1.25, false},
                {Integer.MAX_VALUE, false},
                {Integer.MAX_VALUE-0.01, false},
                {Integer.MAX_VALUE+0.01, true},
                {Integer.MIN_VALUE, false},
                {Integer.MIN_VALUE+0.01, false},
                {Integer.MIN_VALUE-0.01, true}
        };
    }

    @Test(dataProvider = "constructorDataProvider", groups = {"provided"})
    public void testBasicConstructor(double value, boolean ExceptionExpected) {
        // Arrange
        // None
        if (!ExceptionExpected) {
            // Act
            Money m = new Money(value);

            // Assert
            assertEquals(m.getCents(), (100*value)%100);
            assertEquals(m.getDollars(), (int)value);
            assertEquals(m.getValue(), value, 0.01);
        }
        else {
            assertThrows(InvalidParameterException.class, ()->new Money(value));
        }
    }

    @DataProvider(name = "DP2")
    public Object[][] AltconstructorDP() {
        return new Object[][]{
                {1, 25, false},
                {-1, 25, false},
                {Integer.MAX_VALUE, 0, false},
                {Integer.MIN_VALUE, 0, false},
                {0, -101, true},
                {0, 101, true}
        };
    }

    @Test(dataProvider = "DP2", groups = {"provided"})
    public void testSecondaryConstructor(int dollars, int cents,  boolean ExceptionExpected) {
        // Arrange
        // None
        if (!ExceptionExpected) {
            // Act
            Money m = new Money(dollars, cents);

            // Assert
            assertEquals(m.getCents(), cents);
            assertEquals(m.getDollars(), dollars);
            assertEquals(m.getValue(), dollars+(cents/100.0), 0.01);
        }
        else {
            assertThrows(InvalidParameterException.class, ()->new Money(dollars, cents));
        }
    }

    @Test(groups = {"provided"})
    public void testValueConstructor() {
        // Arrange
        // None
        // Act
        Money m = new Money(1, 25);

        // Assert
        assertEquals(m.getCents(), 25);
        assertEquals(m.getDollars(), 1);
        assertEquals(m.getValue(), 1.25, 0.01);
    }

    @Test(groups = {"provided"})
    public void testCopyLikeConstructor() {
        // Arrange
        Money base = new Money(2.59);
        // None
        // Act
        Money m = new Money(base);

        // Assert
        assertEquals(m.getCents(), 59);
        assertEquals(m.getDollars(), 2);
        assertEquals(m.getValue(), 2.59, 0.01);
    }


    @DataProvider(name = "addDataProvider")
    public Object[][] addDataProvider() {
        return new Object[][]{
                {0, 0, 1, 50, 1, 50},
                {1, 99, 1, 02, 3, 01},
                {1, 02, 1, 99, 3, 01},

        };
    }

    @Test(dataProvider = "addDataProvider", groups = {"provided"})
    public void testAdd(int startDollars, int startCents, int addDollars, int addCents, int resultDollars,
                        int resultCents) {
        // Arrange
        Money im = new Money(startDollars, startCents);
        Money am = new Money(addDollars, addCents);

        // Act
        im.add(am);

        // Assert
        assertEquals(im.getDollars(), resultDollars);
        assertEquals(im.getCents(), resultCents);
        assertEquals(im.getValue(), resultDollars+resultCents/100.0, 0.01);
    }

    @DataProvider(name = "subtractDataProvider")
    public Object[][] subtractDP() {
        return new Object[][]{
                {0, 0, 1, 50, -1, -50},
                {2, 99, 1, 02, 1, 97},
                {1, 02, 1, 99, 0, -97},
                {1, 00, 0, 95, 0, 5},
                {-1, -99, 1, 05, -3, -04}

        };
    }

    @Test(dataProvider = "subtractDataProvider", groups = {"provided"})
    public void testSubtract(int startDollars, int startCents, int subtractDollars, int subtractCents, int resultDollars,
                        int resultCents) {
        // Arrange
        Money im = new Money(startDollars, startCents);
        Money am = new Money(subtractDollars, subtractCents);

        // Act
        im.subtract(am);

        // Assert
        assertEquals(im.getDollars(), resultDollars);
        assertEquals(im.getCents(), resultCents);
        assertEquals(im.getValue(), resultDollars+resultCents/100.0, 0.01);
    }

    @DataProvider(name = "equalsDataProvider")
    public Object[][] equalsDP() {
        Money m1 = new Money(1.25);
        Money m2 = new Money(1, 25);
        Money m3=new Money (5.25);
        return new Object[][]{
                {m1, m1, true},
                {m1, m2, true},
                {m1, null, false},
                {m1, "$1.25", false},
                {m1, m3, false}
        };
    }

    @Test(dataProvider = "equalsDataProvider", groups = {"provided"})
    public void testequals(Money m1, Object m2, boolean expectedReturn) {
        // Arrange
        // Nothing.

        // Act
        boolean value = m1.equals(m2);

        // Assert
        assertEquals(value, expectedReturn);
    }


}
