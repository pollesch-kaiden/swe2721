/*
 * Course: SWE2721
 * Spring 2025
 * Lab 6 - Taxcode
 * Name: Kaiden Pollesch
 * Created 3/11/2025
 */
package edu.msoe.swe2721.taxRateCalculator;

import edu.msoe.swe2721.taxRateCalculator.FilingStatus;
import edu.msoe.swe2721.taxRateCalculator.TaxCalculator2023;
import edu.msoe.swe2721.taxRateCalculator.TaxFilingException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Course: SWE2721
 * Spring 2025
 * Class TaxCalculatorTest Purpose: Test the methods in TaxCalculator2023.java
 *
 * @author polleschk
 * SWE2721 Laboratory Assignment
 * @version created on 3/11/2025 3:23 PM
 */
public class TaxCalculatorTest {

    @BeforeMethod(alwaysRun = true, groups = {"all", "student", "polleschk"})
    public void setUp() {
        System.out.println("Start test");
    }

    @DataProvider(name = "singleDataProvider")
    public Object[][] singleDP() {
        return new Object[][]{
            {"Beth", 33, FilingStatus.SINGLE, 125000, 13850, true, 111150, 20076.00, 16.1},
            {"Bart", 72, FilingStatus.SINGLE, 15699, 15700, false, 0, 0, 0},
        };
    }

    @DataProvider(name = "marriedJointlyDataProvider")
    public Object[][] marriedJointlyDP() {
        return new Object[][]{
            {"Larry", 30, "Joan", 28, FilingStatus.MARRIED_FILING_JOINTLY, 60000, 27700, true, 32300, 3436.00, 5.7}
        };
    }

    @Test(dataProvider = "singleDataProvider", groups = {"all", "student", "polleschk"})
    public void testSingleSetup(String name, int age, FilingStatus fs, double agi, double stdDeduction, boolean returnRequired, double taxableIncome, double taxDue, double netRate) throws TaxFilingException {
        // Arrange
        TaxCalculator2023 tc = new TaxCalculator2023(name, fs, age);

        // Act
        tc.setAdjustedGrossIncome(agi);

        // Assert
        assertEquals(tc.determineFilingNeed(), returnRequired);
        assertEquals(tc.obtainStandardDeduction(), stdDeduction, 0.1);
        assertEquals(tc.obtainTaxableIncome(), taxableIncome, 0.1);
        assertEquals(tc.getTaxDue(), taxDue, 0.01);
        assertEquals(tc.getNetTaxRate(), netRate, 0.1);
    }

    @Test(dataProvider = "marriedJointlyDataProvider", groups = {"all", "student", "polleschk"})
    public void testMarriedJointlySetup(String name, int age, String spouseName, int spouseAge, FilingStatus fs, double agi, double stdDeduction, boolean returnRequired, double taxableIncome, double taxDue, double netRate) throws TaxFilingException {
        // Arrange
        TaxCalculator2023 tc = new TaxCalculator2023(name, spouseName, fs, age, spouseAge);

        // Act
        tc.setAdjustedGrossIncome(agi);

        // Assert
        assertEquals(tc.determineFilingNeed(), returnRequired);
        assertEquals(tc.obtainStandardDeduction(), stdDeduction, 0.1);
        assertEquals(tc.obtainTaxableIncome(), taxableIncome, 0.1);
        assertEquals(tc.getTaxDue(), taxDue, 0.01);
        assertEquals(tc.getNetTaxRate(), netRate, 0.1);
    }

    @Test(groups = {"all", "student", "polleschk"})
    public void testConstructorInvalidName() {
        String invalidName = "Invalid_Name"; // Name with an underscore should trigger exception
        FilingStatus fs = FilingStatus.SINGLE;
        int age = 30;

        assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(invalidName, fs, age));
    }

    @Test(groups = {"all", "student", "polleschk"})
    public void testConstructorInvalidAge() {
        String name = "ValidName";
        FilingStatus fs = FilingStatus.SINGLE;
        int invalidAge = -1; // Negative age should trigger exception

        assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, fs, invalidAge));
    }

    @Test(groups = {"all", "student", "polleschk"})
    public void testConstructorInvalidFilingStatus() {
        String name = "ValidName";
        FilingStatus invalidFs = null; // Null filing status should trigger exception
        int age = 30;

        assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, invalidFs, age));
    }

    @Test(groups = {"all", "student", "polleschk"})
    public void testMarriedFilingStatusRequiresSpouse() {
        String name = "Bob Smith";
        FilingStatus fs = FilingStatus.MARRIED_FILING_JOINTLY;
        int age = 32;

        // Test without spouse information
        assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, fs, age));

        // Test with spouse information
        String spouseName = "Joan Smith";
        int spouseAge = 33;
        try {
            new TaxCalculator2023(name, spouseName, fs, age, spouseAge);
        } catch (TaxFilingException e) {
            fail("TaxFilingException was thrown");
        }
    }

    @Test(groups = {"all", "student", "polleschk"})
    public void testStandardDeductionMarriedFilingJointly() throws TaxFilingException {
        String name = "John Doe";
        String spouseName = "Jane Doe";
        FilingStatus fs = FilingStatus.MARRIED_FILING_JOINTLY;
        int age = 64;
        int spouseAge = 66;

        TaxCalculator2023 tc = new TaxCalculator2023(name, spouseName, fs, age, spouseAge);
        double expectedDeduction = 29200.0;
        assertEquals(tc.obtainStandardDeduction(), expectedDeduction, 0.1);
    }

    @Test(groups = {"all", "student", "polleschk"})
    public void testInvalidAge() {
        String name = "John Doe";
        FilingStatus fs = FilingStatus.SINGLE;
        int invalidAge = 0; // Age is 0, should trigger exception

        assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, fs, invalidAge));
    }

    @Test(groups = {"all", "student", "polleschk"})
    public void testNameValidation() {
        FilingStatus fs = FilingStatus.SINGLE;
        int age = 32;

        // Test with invalid first name
        String invalidFirstName = "J";
        String lastName = "Doe";
        assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(invalidFirstName + " " + lastName, fs, age));

        // Test with invalid last name
        String firstName = "John";
        String invalidLastName = "D";
        assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(firstName + " " + invalidLastName, fs, age));

        // Test with both names valid
        String validFirstName = "John";
        String validLastName = "Doe";
        try {
            new TaxCalculator2023(validFirstName + " " + validLastName, fs, age);
        } catch (TaxFilingException e) {
            fail("TaxFilingException was thrown");
        }
    }


}