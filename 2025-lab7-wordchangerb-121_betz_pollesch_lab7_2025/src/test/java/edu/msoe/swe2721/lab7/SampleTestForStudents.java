package edu.msoe.swe2721.lab7;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * This is the initial tests for the word changer class within Lab 5.
 * @author wws
 *
 */
public class SampleTestForStudents {

    private WordChanger wc;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        wc = new WordChanger();
    }

    @Test(groups = {"all", "constructor"})
    /**
     * This method will verify that the constructor is working properly. It will
     * check that an object is created and that the internal state of the object
     * is correct when instantiated.
     */
    public void testDefaultConstructor() {
        // Arrange
        wc = null;

        // Act
        wc = new WordChanger();

        // Assert
        assertNotNull(wc, "Constructor did not create a valid object.");
        assertEquals(wc.synonymList.values().size(), 0);
    }


    @DataProvider(name = "constructorDataProvider")
    public Object[][] constructorDataProvider() {
        return new Object[][]{
                // Parameters: Hashmap synonyms list, whether or not an exception is expected.
                {null, true},                            // Path: 30, 32, 34
                {new HashMap<String, String>(), false}}; // Path: 30, 32, 36, 37
    }


    /**
     * This method will test the constructor which takes in a synonym map as a parameter.
     * @param synList This is the list of synonyms.
     * @param exceptionExpected This will be true if an exception is expected to be thrown.
     * @throws WCException If something goes wrong inside of this test method.  Should never be thrown.
     */
    @Test(groups = {"all", "constructor"}, dataProvider = "constructorDataProvider")
    public void testParameterConstructor(Map<String, String> synList, boolean exceptionExpected) throws WCException {
        // Arrange
        wc = null;

        // Act
        if (exceptionExpected) {
            assertThrows(WCException.class, () -> new WordChanger(synList));
        } else {
            wc = new WordChanger(synList);
            assertSame(wc.synonymList, synList);
        }
    }

    //Where student tests start
    @DataProvider(name = "addSynonymPairDataProvider")
    public Object[][] addSynonymPairDataProvider() {
        return new Object[][]{
                {"word", "synonym", true},
                {"word", "syn", true},
                {"wo", "synonym", true},
                {"word", "s", false},
                {"w", "synonym", false},
                {"word with space", "synonym", false},
                {"word", "synonym with space", false}
        };
    }

    @Test(groups = {"all", "addSynonymPair"}, dataProvider = "addSynonymPairDataProvider")
    public void testAddSynonymPair(String word, String synonym, boolean expected) {
        try {
            boolean result = wc.addSynonymPair(word, synonym);
            assertEquals(result, expected);
        } catch (WCException e) {
            assertFalse(expected);
        }
    }

    @DataProvider(name = "findSynonymDataProvider")
    public Object[][] findSynonymDataProvider() {
        return new Object[][]{
                {"word", "synonym"},
                {"w", null},
                {"word with space", null},
                {"word1", null}
        };
    }

    @Test(groups = {"all", "findSynonym"}, dataProvider = "findSynonymDataProvider")
    public void testFindSynonym(String word, String expected) {
        try {
            wc.addSynonymPair("word", "synonym");
            String result = wc.findSynonym(word);
            assertEquals(result, expected);
        } catch (WCException e) {
            assertNull(expected);
        }
    }

    @Test(groups = {"all", "toLowerCase"})
    public void testToLowerCaseWithUppercase() {
        String result = wc.toLowerCase("TESTING");
        assertEquals(result, "testing");
    }

    @Test(groups = {"all", "toLowerCase"})
    public void testToLowerCaseWithNull() {
        String result = wc.toLowerCase(null);
        assertNull(result);
    }

    @Test(groups = {"all", "determineIfStringIsSpaceFree"})
    public void testDetermineIfStringIsSpaceFreeNoWhitespace() {
        assertTrue(wc.determineIfStringisSpaceFree("Hello"));
    }

    @Test(groups = {"all", "determineIfStringIsSpaceFree"})
    public void testDetermineIfStringIsSpaceFreeWithWhitespace() {
        assertFalse(wc.determineIfStringisSpaceFree("Hello World"));
    }

    @Test(groups = {"all", "determineIfStringIsSpaceFree"})
    public void testDetermineIfStringIsSpaceFreeNull() {
        assertFalse(wc.determineIfStringisSpaceFree(null));
    }

}
