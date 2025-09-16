package edu.msoe.swe2721.taxRateCalculator.PROFESSOR;

import edu.msoe.swe2721.taxRateCalculator.FilingStatus;
import edu.msoe.swe2721.taxRateCalculator.TaxCalculator2023;
import edu.msoe.swe2721.taxRateCalculator.TaxFilingException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class SchillingTaxCalculator2023Test {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        System.out.println("Start test");
    }

    @DataProvider(name = "constructor1Test")
    public Object[][] dataProvFunc() {
        return new Object[][]{
                {"Bob Smith", FilingStatus.SINGLE, 32, false},
                {"Bob Smith", FilingStatus.HEAD_OF_HOUSEHOLD, 32, false},
                {"Bob Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, true},
                {"Bob Smith", FilingStatus.MARRIED_FILING_JOINTLY, 32, true},
                {"B Smith", FilingStatus.SINGLE, 32, true},
                {"B_Smith", FilingStatus.SINGLE, 32, true},
                {"Smith B", FilingStatus.SINGLE, 32, true},
                {"Bob Smith", FilingStatus.SINGLE, -1, true},
                {"Bob Smith", FilingStatus.SINGLE, 0, true},
                {"Bob Smith", FilingStatus.SINGLE, 1, false},
        };
    }

    //Passing the dataProvider to the test method through @Test annotation
    @Test(dataProvider = "constructor1Test", groups = {"wschilling"})
    public void testConstructor(String name, FilingStatus fs, int age, boolean exceptionExpected) throws TaxFilingException {
        if (exceptionExpected) {
            assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, fs, age));
        } else {
            // Arrange and Act
            TaxCalculator2023 tc = new TaxCalculator2023(name, fs, age);

            // Assert
            assertEquals(tc.getFilingStatus(), fs);
            assertEquals(tc.getName(), name);
            assertEquals(tc.getAge(), age);
            assertEquals(tc.getSpouseAge(), 0);
            assertNull(tc.getSpouseName());
        }
    }

    @DataProvider(name = "constructor2Test")
    public Object[][] dataProvFunc2() {
        return new Object[][]{
                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_JOINTLY, 32, 33, false},
                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 33, false},
                {"Bob Smith", "Joan Smith", FilingStatus.SINGLE, 32, 33, true},
                {"Bob Smith", "Joan Smith", FilingStatus.HEAD_OF_HOUSEHOLD, 32, 33, true},

                {"B Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 33, true},
                {"Bob S", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 33, true},
                {"Bob_Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 33, true},
                {"Bob Smith", "J Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 33, true},
                {"Bob Smith", "Joan S", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 33, true},
                {"Bob Smith", "Joan_Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 33, true},

                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, -1, 33, true},
                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 0, 33, true},
                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 1, 33, false},
                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, -1, true},
                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 0, true},
                {"Bob Smith", "Joan Smith", FilingStatus.MARRIED_FILING_SEPARATELY, 32, 1, false},
        };
    }

    //Passing the dataProvider to the test method through @Test annotation
    @Test(dataProvider = "constructor2Test", groups = {"wschilling"})
    public void testConstructor2(String name, String spouseName, FilingStatus fs, int age, int spouseAge, boolean exceptionExpected) throws TaxFilingException {
        if (exceptionExpected) {
            assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, spouseName, fs, age, spouseAge));
        } else {
            // Arrange and Act
            TaxCalculator2023 tc = new TaxCalculator2023(name, spouseName, fs, age, spouseAge);

            // Assert
            assertEquals(tc.getFilingStatus(), fs);
            assertEquals(tc.getName(), name);
            assertEquals(tc.getAge(), age);
            assertEquals(tc.getSpouseAge(), spouseAge);
            assertEquals(tc.getSpouseName(), spouseName);
        }
    }


    @DataProvider(name = "singleDataProvider")
    public Object[][] singleDP() {
        return new Object[][]{
                {"Salley Smith", 28, FilingStatus.SINGLE, 13849, 13850, false, 0, 0, 0},
                {"Salley Smith", 28, FilingStatus.SINGLE, 13850, 13850, true, 0, 0, 0},
                {"Salley Smith", 28, FilingStatus.SINGLE, 13851, 13850, true, 1, 0.1, 0},
                {"Salley Smith", 28, FilingStatus.SINGLE, 19349, 13850, true, 5499, 549.9, 2.8},
                {"Salley Smith", 28, FilingStatus.SINGLE, 24849, 13850, true, 10999, 1099.9, 4.4},
                {"Salley Smith", 28, FilingStatus.SINGLE, 24850, 13850, true, 11000, 1100, 4.4},
                {"Salley Smith", 28, FilingStatus.SINGLE, 24851, 13850, true, 11001, 1100.12, 4.4},
                {"Salley Smith", 28, FilingStatus.SINGLE, 41711.5, 13850, true, 27861.5, 3123.38, 7.5},
                {"Salley Smith", 28, FilingStatus.SINGLE, 58574, 13850, true, 44724, 5146.88, 8.8},
                {"Salley Smith", 28, FilingStatus.SINGLE, 58575, 13850, true, 44725, 5147, 8.8},
                {"Salley Smith", 28, FilingStatus.SINGLE, 58576, 13850, true, 44726, 5147.22, 8.8},
                {"Salley Smith", 28, FilingStatus.SINGLE, 83899, 13850, true, 70049, 10718.28, 12.8},
                {"Salley Smith", 28, FilingStatus.SINGLE, 109224, 13850, true, 95374, 16289.78, 14.9},
                {"Salley Smith", 28, FilingStatus.SINGLE, 109225, 13850, true, 95375, 16290, 14.9},
                {"Salley Smith", 28, FilingStatus.SINGLE, 109226, 13850, true, 95376, 16290.24, 14.9},
                {"Salley Smith", 28, FilingStatus.SINGLE, 195949, 13850, true, 182099, 37103.76, 18.9},
                {"Salley Smith", 28, FilingStatus.SINGLE, 195950, 13850, true, 182100, 37104, 18.9},
                {"Salley Smith", 28, FilingStatus.SINGLE, 195951, 13850, true, 182101, 37104.32, 18.9},
                {"Salley Smith", 28, FilingStatus.SINGLE, 245099, 13850, true, 231249, 52831.68, 21.6},
                {"Salley Smith", 28, FilingStatus.SINGLE, 245100, 13850, true, 231250, 52832, 21.6},
                {"Salley Smith", 28, FilingStatus.SINGLE, 245101, 13850, true, 231251, 52832.35, 21.6},
                {"Salley Smith", 28, FilingStatus.SINGLE, 591974, 13850, true, 578124, 174237.9, 29.4},
                {"Salley Smith", 28, FilingStatus.SINGLE, 591975, 13850, true, 578125, 174238.25, 29.4},
                {"Salley Smith", 28, FilingStatus.SINGLE, 591976, 13850, true, 578126, 174238.62, 29.4},


                {"Salley Smith", 72, FilingStatus.SINGLE, 15699, 15700, false, 0, 0, 0},
                {"Salley Smith", 72, FilingStatus.SINGLE, 15700, 15700, true, 0, 0, 0},
                {"Salley Smith", 72, FilingStatus.SINGLE, 15701, 15700, true, 1, 0.1, 0},
                {"Salley Smith", 72, FilingStatus.SINGLE, 21199, 15700, true, 5499, 549.9, 2.6},
                {"Salley Smith", 72, FilingStatus.SINGLE, 26699, 15700, true, 10999, 1099.9, 4.1},
                {"Salley Smith", 72, FilingStatus.SINGLE, 26700, 15700, true, 11000, 1100, 4.1},
                {"Salley Smith", 72, FilingStatus.SINGLE, 26701, 15700, true, 11001, 1100.12, 4.1},
                {"Salley Smith", 72, FilingStatus.SINGLE, 43561.5, 15700, true, 27861.5, 3123.38, 7.2},
                {"Salley Smith", 72, FilingStatus.SINGLE, 60424, 15700, true, 44724, 5146.88, 8.5},
                {"Salley Smith", 72, FilingStatus.SINGLE, 60425, 15700, true, 44725, 5147, 8.5},
                {"Salley Smith", 72, FilingStatus.SINGLE, 60426, 15700, true, 44726, 5147.22, 8.5},
                {"Salley Smith", 72, FilingStatus.SINGLE, 85749, 15700, true, 70049, 10718.28, 12.5},
                {"Salley Smith", 72, FilingStatus.SINGLE, 111074, 15700, true, 95374, 16289.78, 14.7},
                {"Salley Smith", 72, FilingStatus.SINGLE, 111075, 15700, true, 95375, 16290, 14.7},
                {"Salley Smith", 72, FilingStatus.SINGLE, 111076, 15700, true, 95376, 16290.24, 14.7},
                {"Salley Smith", 72, FilingStatus.SINGLE, 197799, 15700, true, 182099, 37103.76, 18.8},
                {"Salley Smith", 72, FilingStatus.SINGLE, 197800, 15700, true, 182100, 37104, 18.8},
                {"Salley Smith", 72, FilingStatus.SINGLE, 197801, 15700, true, 182101, 37104.32, 18.8},
                {"Salley Smith", 72, FilingStatus.SINGLE, 246949, 15700, true, 231249, 52831.68, 21.4},
                {"Salley Smith", 72, FilingStatus.SINGLE, 246950, 15700, true, 231250, 52832, 21.4},
                {"Salley Smith", 72, FilingStatus.SINGLE, 246951, 15700, true, 231251, 52832.35, 21.4},
                {"Salley Smith", 72, FilingStatus.SINGLE, 593824, 15700, true, 578124, 174237.9, 29.3},
                {"Salley Smith", 72, FilingStatus.SINGLE, 593825, 15700, true, 578125, 174238.25, 29.3},
                {"Salley Smith", 72, FilingStatus.SINGLE, 593826, 15700, true, 578126, 174238.62, 29.3},

                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 20799, 20800, false, 0, 0, 0},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 20800, 20800, true, 0, 0, 0},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 20801, 20800, true, 1, 0.1, 0},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 28649, 20800, true, 7849, 784.9, 2.7},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 36499, 20800, true, 15699, 1569.9, 4.3},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 36500, 20800, true, 15700, 1570, 4.3},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 36501, 20800, true, 15701, 1570.12, 4.3},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 58574, 20800, true, 37774, 4218.88, 7.2},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 80649, 20800, true, 59849, 6867.88, 8.5},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 80650, 20800, true, 59850, 6868, 8.5},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 80651, 20800, true, 59851, 6868.22, 8.5},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 98399, 20800, true, 77599, 10772.78, 10.9},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 116149, 20800, true, 95349, 14677.78, 12.6},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 116150, 20800, true, 95350, 14678, 12.6},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 116151, 20800, true, 95351, 14678.24, 12.6},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 202899, 20800, true, 182099, 35497.76, 17.5},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 202900, 20800, true, 182100, 35498, 17.5},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 202901, 20800, true, 182101, 35498.32, 17.5},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 252049, 20800, true, 231249, 51225.68, 20.3},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 252050, 20800, true, 231250, 51226, 20.3},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 252051, 20800, true, 231251, 51226.35, 20.3},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 598899, 20800, true, 578099, 172623.15, 28.8},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 598900, 20800, true, 578100, 172623.5, 28.8},
                {"Salley Smith", 41, FilingStatus.HEAD_OF_HOUSEHOLD, 598901, 20800, true, 578101, 172623.87, 28.8},

                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 22649, 22650, false, 0, 0, 0},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 22650, 22650, true, 0, 0, 0},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 22651, 22650, true, 1, 0.1, 0},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 30499, 22650, true, 7849, 784.9, 2.6},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 38349, 22650, true, 15699, 1569.9, 4.1},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 38350, 22650, true, 15700, 1570, 4.1},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 38351, 22650, true, 15701, 1570.12, 4.1},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 60424, 22650, true, 37774, 4218.88, 7},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 82499, 22650, true, 59849, 6867.88, 8.3},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 82500, 22650, true, 59850, 6868, 8.3},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 82501, 22650, true, 59851, 6868.22, 8.3},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 100249, 22650, true, 77599, 10772.78, 10.7},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 117999, 22650, true, 95349, 14677.78, 12.4},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 118000, 22650, true, 95350, 14678, 12.4},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 118001, 22650, true, 95351, 14678.24, 12.4},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 204749, 22650, true, 182099, 35497.76, 17.3},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 204750, 22650, true, 182100, 35498, 17.3},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 204751, 22650, true, 182101, 35498.32, 17.3},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 253899, 22650, true, 231249, 51225.68, 20.2},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 253900, 22650, true, 231250, 51226, 20.2},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 253901, 22650, true, 231251, 51226.35, 20.2},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 600749, 22650, true, 578099, 172623.15, 28.7},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 600750, 22650, true, 578100, 172623.5, 28.7},
                {"Georgette Smith", 73, FilingStatus.HEAD_OF_HOUSEHOLD, 600751, 22650, true, 578101, 172623.87, 28.7},


        };
    }


    //Passing the dataProvider to the test method through @Test annotation
    @Test(dataProvider = "singleDataProvider", groups = {"wschilling"})
    public void testSingleSetup(String name, int age, FilingStatus fs, double agi, double stdDeduction, boolean returnRequired, double taxableIncome, double taxDue, double netRate) throws TaxFilingException {
        // Arrange
        TaxCalculator2023 tc = new TaxCalculator2023(name, fs, age);

        // Act
        tc.setAdjustedGrossIncome(agi);

        if (tc.determineFilingNeed() != returnRequired ||
                Math.abs(tc.obtainStandardDeduction() - stdDeduction) > 0.1 ||
                Math.abs(tc.obtainTaxableIncome() - taxableIncome) > 0.1 ||
                Math.abs(tc.getTaxDue() - taxDue) > 0.01 ||
                Math.abs(tc.getNetTaxRate() - netRate) > 0.1) {
            System.err.println("Age" + age + " ");
            System.err.println("AGI: " + agi);
            System.err.println("Filing Status: " + fs);
            System.err.println("Parameter       \tExpected \tActual");
            System.err.println("Stdeduction:    \t" + stdDeduction + "\t" + tc.obtainStandardDeduction());
            System.err.println("Taxable Income: \t" + taxableIncome + "\t" + tc.obtainTaxableIncome());
            System.err.println("ReturnRequired: \t" + returnRequired + "\t" + tc.determineFilingNeed());
            System.err.println("Tax Due:        \t" + taxDue + "\t" + tc.getTaxDue());
            System.err.println("netTaxRate      \t" + netRate + "\t" + tc.getNetTaxRate());
            System.err.flush();
        }

        // Assert
        assertEquals(tc.determineFilingNeed(), returnRequired);
        assertEquals(tc.obtainStandardDeduction(), stdDeduction, 0.1);
        assertEquals(tc.obtainTaxableIncome(), taxableIncome, 0.1);
        assertEquals(tc.getTaxDue(), taxDue, 0.01);
        assertEquals(tc.getNetTaxRate(), netRate, 0.1);
    }


    @DataProvider(name = "marriedDataProvider")
    public Object[][] marriedDP() {
        return new Object[][]{
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 27699, 27700, false, 0, 0, 0},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 27700, 27700, true, 0, 0, 0},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 27701, 27700, true, 1, 0.1, 0},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 38699, 27700, true, 10999, 1099.9, 2.8},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 49699, 27700, true, 21999, 2199.9, 4.4},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 49700, 27700, true, 22000, 2200, 4.4},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 49701, 27700, true, 22001, 2200.12, 4.4},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 83424, 27700, true, 55724, 6246.88, 7.5},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 117149, 27700, true, 89449, 10293.88, 8.8},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 117150, 27700, true, 89450, 10294, 8.8},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 117151, 27700, true, 89451, 10294.22, 8.8},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 167799, 27700, true, 140099, 21436.78, 12.8},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 218449, 27700, true, 190749, 32579.78, 14.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 218450, 27700, true, 190750, 32580, 14.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 218451, 27700, true, 190751, 32580.24, 14.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 391899, 27700, true, 364199, 74207.76, 18.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 391900, 27700, true, 364200, 74208, 18.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 391901, 27700, true, 364201, 74208.32, 18.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 490199, 27700, true, 462499, 105663.68, 21.6},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 490200, 27700, true, 462500, 105664, 21.6},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 490201, 27700, true, 462501, 105664.35, 21.6},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 721449, 27700, true, 693749, 186601.15, 25.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 721450, 27700, true, 693750, 186601.5, 25.9},
                {"Melissa Smith", 27, "Robert Smith", 26, FilingStatus.MARRIED_FILING_JOINTLY, 721451, 27700, true, 693751, 186601.87, 25.9},

                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 29199, 29200, false, 0, 0, 0},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 29200, 29200, true, 0, 0, 0},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 29201, 29200, true, 1, 0.1, 0},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 40199, 29200, true, 10999, 1099.9, 2.7},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 51199, 29200, true, 21999, 2199.9, 4.3},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 51200, 29200, true, 22000, 2200, 4.3},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 51201, 29200, true, 22001, 2200.12, 4.3},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 84924, 29200, true, 55724, 6246.88, 7.4},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 118649, 29200, true, 89449, 10293.88, 8.7},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 118650, 29200, true, 89450, 10294, 8.7},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 118651, 29200, true, 89451, 10294.22, 8.7},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 169299, 29200, true, 140099, 21436.78, 12.7},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 219949, 29200, true, 190749, 32579.78, 14.8},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 219950, 29200, true, 190750, 32580, 14.8},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 219951, 29200, true, 190751, 32580.24, 14.8},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 393399, 29200, true, 364199, 74207.76, 18.9},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 393400, 29200, true, 364200, 74208, 18.9},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 393401, 29200, true, 364201, 74208.32, 18.9},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 491699, 29200, true, 462499, 105663.68, 21.5},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 491700, 29200, true, 462500, 105664, 21.5},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 491701, 29200, true, 462501, 105664.35, 21.5},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 722949, 29200, true, 693749, 186601.15, 25.8},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 722950, 29200, true, 693750, 186601.5, 25.8},
                {"Melissa Smith", 64, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 722951, 29200, true, 693751, 186601.87, 25.8},

                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 30699, 30700, false, 0, 0, 0},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 30700, 30700, true, 0, 0, 0},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 30701, 30700, true, 1, 0.1, 0},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 41699, 30700, true, 10999, 1099.9, 2.6},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 52699, 30700, true, 21999, 2199.9, 4.2},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 52700, 30700, true, 22000, 2200, 4.2},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 52701, 30700, true, 22001, 2200.12, 4.2},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 86424, 30700, true, 55724, 6246.88, 7.2},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 120149, 30700, true, 89449, 10293.88, 8.6},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 120150, 30700, true, 89450, 10294, 8.6},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 120151, 30700, true, 89451, 10294.22, 8.6},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 170799, 30700, true, 140099, 21436.78, 12.6},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 221449, 30700, true, 190749, 32579.78, 14.7},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 221450, 30700, true, 190750, 32580, 14.7},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 221451, 30700, true, 190751, 32580.24, 14.7},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 394899, 30700, true, 364199, 74207.76, 18.8},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 394900, 30700, true, 364200, 74208, 18.8},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 394901, 30700, true, 364201, 74208.32, 18.8},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 493199, 30700, true, 462499, 105663.68, 21.4},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 493200, 30700, true, 462500, 105664, 21.4},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 493201, 30700, true, 462501, 105664.35, 21.4},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 724449, 30700, true, 693749, 186601.15, 25.8},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 724450, 30700, true, 693750, 186601.5, 25.8},
                {"Melissa Smith", 72, "Robert Smith", 66, FilingStatus.MARRIED_FILING_JOINTLY, 724451, 30700, true, 693751, 186601.87, 25.8},

                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 4, 13850, false, 0, 0, 0},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 5, 13850, true, 0, 0, 0},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 6, 13850, true, 0, 0, 0},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 13849, 13850, true, 0, 0, 0},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 13850, 13850, true, 0, 0, 0},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 13851, 13850, true, 1, 0.1, 0},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 19349, 13850, true, 5499, 549.9, 2.8},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 24849, 13850, true, 10999, 1099.9, 4.4},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 24850, 13850, true, 11000, 1100, 4.4},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 24851, 13850, true, 11001, 1100.12, 4.4},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 41711.5, 13850, true, 27861.5, 3123.38, 7.5},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 58574, 13850, true, 44724, 5146.88, 8.8},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 58575, 13850, true, 44725, 5147, 8.8},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 58576, 13850, true, 44726, 5147.22, 8.8},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 83899, 13850, true, 70049, 10718.28, 12.8},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 109224, 13850, true, 95374, 16289.78, 14.9},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 109225, 13850, true, 95375, 16290, 14.9},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 109226, 13850, true, 95376, 16290.24, 14.9},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 195949, 13850, true, 182099, 37103.76, 18.9},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 195950, 13850, true, 182100, 37104, 18.9},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 195951, 13850, true, 182101, 37104.32, 18.9},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 245099, 13850, true, 231249, 52831.68, 21.6},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 245100, 13850, true, 231250, 52832, 21.6},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 245101, 13850, true, 231251, 52832.35, 21.6},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 591974, 13850, true, 578124, 174237.9, 29.4},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 591975, 13850, true, 578125, 174238.25, 29.4},
                {"Melissa Smith", 33, "Robert Smith", 32, FilingStatus.MARRIED_FILING_SEPARATELY, 591976, 13850, true, 578126, 174238.62, 29.4},

        };
    }


    //Passing the dataProvider to the test method through @Test annotation
    @Test(dataProvider = "marriedDataProvider", groups = {"wschilling"})
    public void testMarriedSetup(String name, int age, String spouseName, int spouseAge, FilingStatus fs, double agi, double stdDeduction, boolean returnRequired, double taxableIncome, double taxDue, double netRate) throws TaxFilingException {
        // Arrange
        TaxCalculator2023 tc = new TaxCalculator2023(name, spouseName, fs, age, spouseAge);

        // Act
        tc.setAdjustedGrossIncome(agi);

        if (tc.determineFilingNeed() != returnRequired ||
                Math.abs(tc.obtainStandardDeduction() - stdDeduction) > 0.1 ||
                Math.abs(tc.obtainTaxableIncome() - taxableIncome) > 0.1 ||
                Math.abs(tc.getTaxDue() - taxDue) > 0.01 ||
                Math.abs(tc.getNetTaxRate() - netRate) > 0.1) {
            System.err.println("Age(s)" + age + " " + spouseAge);
            System.err.println("AGI: " + agi);
            System.err.println("Filing Status: " + fs);
            System.err.println("Parameter       \t Expected \t Actual");
            System.err.println("Stdeduction:    \t" + stdDeduction + "\t" + tc.obtainStandardDeduction());
            System.err.println("Taxable Income: \t" + taxableIncome + "\t" + tc.obtainTaxableIncome());
            System.err.println("ReturnRequired: \t" + returnRequired + "\t" + tc.determineFilingNeed());
            System.err.println("Tax Due:        \t" + taxDue + "\t" + tc.getTaxDue());
            System.err.println("netTaxRate      \t" + netRate + "\t" + tc.getNetTaxRate());
            System.err.flush();
        }

        // Assert
        assertEquals(tc.determineFilingNeed(), returnRequired);
        assertEquals(tc.obtainStandardDeduction(), stdDeduction, 0.1);
        assertEquals(tc.obtainTaxableIncome(), taxableIncome, 0.1);
        assertEquals(tc.getTaxDue(), taxDue, 0.01);
        assertEquals(tc.getNetTaxRate(), netRate, 0.1);
    }
}