/*
 * Course: SWE2721-121
 * Spring 2025
 * Lab 5 - Inventory Lab
 * Name: Madison Betz
 * Created 2/25/2025
 */
package msoe.swe2721.lab4_5;

import msoe.swe2721.lab4_5.provided.InventoryItem;
import msoe.swe2721.lab4_5.provided.InventoryManager;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateItemEntryException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidInventoryParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Course: SWE2721-121
 * Spring 2025
 * Class InventoryManagerTest Purpose: Test InventoryManager class
 *
 * @author betzm
 * SWE2721-121 Laboratory Assignment
 * @version created on 2/25/2025 3:18 PM
 */
public class InventoryManagerTest {
    private InventoryManager manager;
    private static final String VALID_NAME = "Test Item";
    private static final String VALID_DESCRIPTION = "This is a valid description for testing";
    private static final int VALID_SKU = 12345;
    private static final double VALID_PRICE = 19.99;
    private static final int VALID_STOCK = 10;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        manager = new InventoryManager();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        manager = null;
    }

    @DataProvider(name = "invalidNamesData")
    public Object[][] invalidNamesData() {
        return new Object[][] {
                {null, "Null name should be invalid"},
                {"", "Empty name should be invalid"},
                {"A", "Single character name should be invalid"},
                {"  ", "Whitespace name should be invalid"}
        };
    }

    @DataProvider(name = "invalidDescriptionsData")
    public Object[][] invalidDescriptionsData() {
        return new Object[][] {
                {null, "Null description should be invalid"},
                {"", "Empty description should be invalid"},
                {"Too short", "Description less than 10 chars should be invalid"},
                {"         ", "Whitespace description should be invalid"}
        };
    }

    @DataProvider(name = "invalidSKUData")
    public Object[][] invalidSKUData() {
        return new Object[][] {
                {0, "Zero SKU should be invalid"},
                {-1, "Negative SKU should be invalid"},
                {-1000, "Large negative SKU should be invalid"}
        };
    }

    @DataProvider(name = "invalidPriceData")
    public Object[][] invalidPriceData() {
        return new Object[][] {
                {0, "Zero price should be invalid"},
                {-1, "Negative price should be invalid"},
                {1001, "Price over 1000 should be invalid"},
                {2000, "Large price should be invalid"}
        };
    }

    @DataProvider(name = "invalidStockData")
    public Object[][] invalidStockData() {
        return new Object[][] {
                {-1, "Negative stock should be invalid"},
                {-100, "Large negative stock should be invalid"}
        };
    }

    @Test(groups = {"all", "student2"})
    public void testAddNewItemToStore_ValidParameters() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE);
        Assert.assertTrue(manager.checkForSKUInSystem(VALID_SKU));
    }

    @Test(expectedExceptions = DuplicateItemEntryException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStore_DuplicateSKU() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE);
        manager.addNewItemToStore("Another Item", "Another valid description for testing", VALID_SKU, 29.99);
    }

    @Test(dataProvider = "invalidNamesData",
            expectedExceptions = InvalidInventoryParameterException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStore_InvalidName(String invalidName, String message) throws Exception {
        manager.addNewItemToStore(invalidName, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE);
    }

    @Test(dataProvider = "invalidDescriptionsData",
            expectedExceptions = InvalidInventoryParameterException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStore_InvalidDescription(String invalidDesc, String message) throws Exception {
        manager.addNewItemToStore(VALID_NAME, invalidDesc, VALID_SKU, VALID_PRICE);
    }

    @Test(dataProvider = "invalidSKUData",
            expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStore_InvalidSKU(int invalidSKU, String message) throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, invalidSKU, VALID_PRICE);
    }

    @Test(dataProvider = "invalidPriceData",
            expectedExceptions = InvalidInventoryParameterException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStore_InvalidPrice(double invalidPrice, String message) throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, invalidPrice);
    }

    @Test(groups = {"all", "student2"})
    public void testAddNewItemToStoreWithStock_ValidParameters() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE, VALID_STOCK);
        Assert.assertTrue(manager.checkForSKUInSystem(VALID_SKU));
        InventoryItem item = manager.obtainItemBySKU(VALID_SKU);
        Assert.assertEquals(item.getStockCount(), VALID_STOCK);
    }

    @Test(expectedExceptions = DuplicateItemEntryException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStoreWithStock_DuplicateSKU() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE, VALID_STOCK);
        manager.addNewItemToStore("Another Item", "Another valid description for testing", VALID_SKU, 29.99, 5);
    }

    @Test(dataProvider = "invalidSKUData",
            expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStoreWithStock_InvalidSKU(int invalidSKU, String message) throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, invalidSKU, VALID_PRICE, VALID_STOCK);
    }

    @Test(dataProvider = "invalidStockData",
            expectedExceptions = InvalidInventoryParameterException.class,
            groups = {"all", "student2"})
    public void testAddNewItemToStoreWithStock_InvalidStock(int invalidStock, String message) throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE, invalidStock);
    }

    @Test(groups = {"all", "student2"})
    public void testCheckForSKUInSystem_ExistingSKU() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE);
        Assert.assertTrue(manager.checkForSKUInSystem(VALID_SKU));
    }

    @Test(groups = {"all", "student2"})
    public void testCheckForSKUInSystem_NonExistingSKU() throws Exception {
        Assert.assertFalse(manager.checkForSKUInSystem(VALID_SKU));
    }

    @Test(dataProvider = "invalidSKUData",
            expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testCheckForSKUInSystem_InvalidSKU(int invalidSKU, String message) throws Exception {
        manager.checkForSKUInSystem(invalidSKU);
    }

    @Test(groups = {"all", "student2"})
    public void testObtainItemBySKU_ExistingSKU() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE);
        InventoryItem item = manager.obtainItemBySKU(VALID_SKU);
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getName(), VALID_NAME);
    }

    @Test(groups = {"all", "student2"})
    public void testObtainItemBySKU_NonExistingSKU() throws Exception {
        InventoryItem item = manager.obtainItemBySKU(VALID_SKU);
        Assert.assertNull(item);
    }

    @Test(dataProvider = "invalidSKUData",
            expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testObtainItemBySKU_InvalidSKU(int invalidSKU, String message) throws Exception {
        manager.obtainItemBySKU(invalidSKU);
    }

    @Test(groups = {"all", "student2"})
    public void testAddStock_ValidParameters() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE, VALID_STOCK);
        int newTotal = manager.addStock(VALID_SKU, 5);
        Assert.assertEquals(newTotal, VALID_STOCK + 5);
    }

    @Test(dataProvider = "invalidSKUData",
            expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testAddStock_InvalidSKU(int invalidSKU, String message) throws Exception {
        manager.addStock(invalidSKU, 5);
    }

    @Test(expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testAddStock_NonExistingSKU() throws Exception {
        // This test detects a bug in the implementation - not part of 'provided' since it fails
        manager.addStock(VALID_SKU, 5);
    }

    @Test(dataProvider = "invalidStockData",
            expectedExceptions = InvalidInventoryParameterException.class,
            groups = {"all", "student2"})
    public void testAddStock_InvalidStockAmount(int invalidStock, String message) throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE);
        manager.addStock(VALID_SKU, invalidStock);
    }

    @Test(groups = {"all", "student2"})
    public void testReturnStock_ValidParameters() throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE, VALID_STOCK);
        int newTotal = manager.returnStock(VALID_SKU, 5);
        Assert.assertEquals(newTotal, VALID_STOCK + 5);
    }

    @Test(dataProvider = "invalidSKUData",
            expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testReturnStock_InvalidSKU(int invalidSKU, String message) throws Exception {
        manager.returnStock(invalidSKU, 5);
    }

    @Test(expectedExceptions = InvalidSKUException.class,
            groups = {"all", "student2"})
    public void testReturnStock_NonExistingSKU() throws Exception {
        // This test detects a bug in the implementation
        manager.returnStock(VALID_SKU, 5);
    }

    @Test(dataProvider = "invalidStockData",
            expectedExceptions = InvalidInventoryParameterException.class,
            groups = {"all", "student2"})
    public void testReturnStock_InvalidStockAmount(int invalidStock, String message) throws Exception {
        manager.addNewItemToStore(VALID_NAME, VALID_DESCRIPTION, VALID_SKU, VALID_PRICE);
        manager.returnStock(VALID_SKU, invalidStock);
    }
}