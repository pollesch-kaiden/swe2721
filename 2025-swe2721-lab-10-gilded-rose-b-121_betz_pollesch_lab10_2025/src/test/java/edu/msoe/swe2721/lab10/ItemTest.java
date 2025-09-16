package edu.msoe.swe2721.lab10;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 * Course: SWE2721-121
 * Spring 2025
 * Class ItemTest Purpose: Test the Item.java Class
 *
 * @author polleschk
 * SWE2721-121 Laboratory Assignment
 * @version created on 4/15/2025 3:14 PM
 */
public class ItemTest {

    private Item item;
    private RefactoredItem refactoredItem;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        // Initialize resources or objects if needed
        item = null;
        refactoredItem = null;
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        // Clean up resources or objects if needed
        item = null;
        refactoredItem = null;
    }

    @DataProvider(name = "itemTestCases")
    public Object[][] createTestData() {
        return new Object[][] {
                // {name, initialSellIn, initialQuality, expectedSellIn, expectedQuality}
                {"Regular Item", 10, 20, 9, 19},
                {"Aged Brie", 10, 20, 9, 21},
                {"Backstage passes to a TAFKAL80ETC concert", 15, 20, 14, 21},
                {"Backstage passes to a TAFKAL80ETC concert", 10, 20, 9, 22},
                {"Backstage passes to a TAFKAL80ETC concert", 5, 20, 4, 23},
                {"Backstage passes to a TAFKAL80ETC concert", 0, 20, -1, 0},
                {"Sulfuras, Hand of Ragnaros", 10, 80, 10, 80},
                {"Regular Item", 0, 20, -1, 18},
                {"Aged Brie", 0, 20, -1, 22},
                {"Regular Item", 10, 0, 9, 0},
                {"Aged Brie", 10, 50, 9, 50},
                {"Backstage passes to a TAFKAL80ETC concert", 10, 50, 9, 50}
        };
    }

    @Test(dataProvider = "itemTestCases", groups = {"all", "student", "polleschk"})
    public void testItemUpdateQuality(String name, int sellIn, int quality,
                                      int expectedSellIn, int expectedQuality) {
        item = new Item(name, sellIn, quality);
        item.updateQuality();
        assertEquals(item.getSellIn(), expectedSellIn);
        assertEquals(item.getQuality(), expectedQuality);
    }

    @Test(dataProvider = "itemTestCases", groups = {"all", "student", "polleschk"})
    public void testRefactoredItemUpdateQuality(String name, int sellIn, int quality,
                                                int expectedSellIn, int expectedQuality) {
        refactoredItem = new RefactoredItem(name, sellIn, quality);
        refactoredItem.updateQuality();
        assertEquals(refactoredItem.getSellIn(), expectedSellIn);
        assertEquals(refactoredItem.getQuality(), expectedQuality);
    }
}