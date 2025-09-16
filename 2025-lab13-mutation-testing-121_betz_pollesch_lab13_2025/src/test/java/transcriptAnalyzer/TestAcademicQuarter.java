package transcriptAnalyzer;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * This class will test the operation of the Academic Quarter enumeration.
 */
public class TestAcademicQuarter {
    @DataProvider(name = "TermMappingDataProvider")
    public static Object[][] TermMappingDataProvider() {
        return new Object[][]{{AcademicQuarter.FALL, 3}, {AcademicQuarter.WINTER, 4}, {AcademicQuarter.SPRING, 1}, {AcademicQuarter.SUMMER, 2}};
    }

    /**
     * This test verifies the operation of the
     * @param myEnum This is the enumeration value.
     * @param expectedValue This is the value of the enumeration in terms of quality points.
     **/
    @Test(groups = {"AcademicQuarterEnumTests"}, dataProvider = "TermMappingDataProvider")
    public void testGetQualityPoints(AcademicQuarter myEnum, int expectedValue) throws Exception {
        // No setup
        // // No action

        // Just an assert here.
        assertEquals(myEnum.getOrder(), expectedValue);
    }
}