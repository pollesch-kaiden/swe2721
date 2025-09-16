package msoe.swe2721.lab4_5.provided;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ZipCodeValidatorTest {

    @DataProvider(name = "constructorDataProvider")
    public Object[][] constructorDP() {
        return new Object[][]{
                {53202, "WI", true},
                {90210, "CA", true},
                {-1, null, false},
                {100000, null, false}
        };
    }

    @Test(dataProvider = "constructorDataProvider", groups = {"provided"})
    public void testIsValidZipcode(int zipcode, String state, boolean expectedReturn) {
        // Arrange
        ZipCodeValidator zcv = ZipCodeValidator.getSingleton();

        // Act
        boolean retv = zcv.isValidZipcode(zipcode);

        // Assert
        assertEquals(retv, expectedReturn);
    }

    @Test(dataProvider = "constructorDataProvider", groups = {"provided"})
    public void testgetStateByZip(int zipcode, String state, boolean expectedReturn) {
        // Arrange
        ZipCodeValidator zcv = ZipCodeValidator.getSingleton();

        // Act
        String retv = zcv.getStateByZip(zipcode);

        // Assert
        assertEquals(retv, state);
    }









}
