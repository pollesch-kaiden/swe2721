package msoe.swe2721.lab4_5.PROF;

import msoe.swe2721.lab4_5.provided.*;
import msoe.swe2721.lab4_5.provided.exceptions.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ProfessorTestInventoryManager {
    // This is the inventory manager to use in tests.
    private InventoryManager im = null;
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        im = new InventoryManager();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

    }

    /**
     * Using reflection thius method will populate the existing inventory into the Inventory Manager.
     * @param inventoryItems This is the existing inventory to use.
     * @return The return will be a new InventoryManager.
     * @throws NoSuchFieldException
     */
    private InventoryManager createAndPopulateInventory(InventoryItem[] inventoryItems) throws NoSuchFieldException, IllegalAccessException {
        InventoryManager im = new InventoryManager();

        // Create Field object
        Field privateField = InventoryManager.class.getDeclaredField("stock");

        // Set the accessibility as true
        privateField.setAccessible(true);

        // Pull out the collection of customers.
        HashMap<Integer, InventoryItem> internalCustomers = (HashMap)privateField.get(im);

        for (int index = 0; index < inventoryItems.length; index++)
        {
            internalCustomers.put(inventoryItems[index].getItemNumber(), inventoryItems[index]);
        }
        return im;
    }

    /**
     * This method returns a set of tests that verify the add new customer method.
     * @return An array of objects, (Initial customers in system, First Name, Last Name, age, Address, City, State, Zip, boolean as to whether the test is valid.)
     */
    @DataProvider(name = "addNewInventoryToStore-data-provider")
    public Object[][] addNewInventoryDP() {
        InventoryItem ii1 = new InventoryItem("Paperclip", "Ernies Paperclips", 1010, 1, new Money(0.99));
        InventoryItem ii2 = new InventoryItem("Soap", "Dove Safe Soap", 5020, 100, new Money(10.00));
        // Layout <<Initial Customers>, Fname, LName, age, Address, City, State, Zip, exception generated>
        return new Object[][]{
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 29.99, null},
                {new InventoryItem[]{}, "D", "A full book of DevOps", 10001, 29.99, InvalidInventoryParameterException.class}, // C20
                {new InventoryItem[]{}, "DevOpsBook", "A", 10001, 29.99, InvalidInventoryParameterException.class},            // C21
                {new InventoryItem[]{}, "DevOpsBook", "ABCDEFGHI", 10001, 29.99, InvalidInventoryParameterException.class},
                {new InventoryItem[]{}, "DevOpsBook", "ABCDEFGHIJ", 10001, 29.99, null},
                {new InventoryItem[]{}, "DevOpsBook", "ABCDEFGHIJK", 10001, 29.99, null}, // End C21
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, -1, InvalidInventoryParameterException.class}, // C22
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, -0.01, InvalidInventoryParameterException.class}, // C22
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 0.0, null}, // C22
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 0.01, null}, // C22
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 999.99, null}, // C22
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 1000, null}, // C22
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 1000.01, InvalidInventoryParameterException.class}, // C22
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", -1, 29.99, InvalidSKUException.class}, // C23
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 0, 29.99, InvalidSKUException.class}, // C23
                {new InventoryItem[]{ii1, ii2}, "DevOpsBook", "A full book of DevOps", 10001, 29.99, null}, // C24
                {new InventoryItem[]{ii1, ii2}, "DevOpsBook", "A full book of DevOps", 1010, 29.99, DuplicateItemEntryException.class},
        };
    }

    /**
      */
    @Test(dataProvider="addNewInventoryToStore-data-provider", groups = {"professor"})
    public void testAddNewInventory(InventoryItem[] inventoryItems, String name, String description, int sku, double price, Class exception) throws InvalidSKUException, InvalidInventoryParameterException, DuplicateItemEntryException, NoSuchFieldException, IllegalAccessException {
        im = createAndPopulateInventory(inventoryItems);

        // Act
        if (exception== null) {
            im.addNewItemToStore(name, description, sku, price);
            // Assert

            assertEquals(im.checkForSKUInSystem(sku), true);
            assertEquals(im.obtainItemBySKU(sku).getStockCount(), 0);
            assertEquals(im.obtainItemBySKU(sku).getDescription(), description);
            assertEquals(im.obtainItemBySKU(sku).getName(), name);
            assertEquals(im.obtainItemBySKU(sku).getPrice().getValue(), price);
            assertEquals(im.obtainItemBySKU(sku).getItemNumber(), sku);
        }
        else {
            assertThrows(exception,          ()->   im.addNewItemToStore(name, description, sku, price, 1));
        }
    }

    /**
     */
    @Test(dataProvider="addNewInventoryToStore-data-provider", groups = {"professor"})
    public void testAddNewInventoryWithCount(InventoryItem[] inventoryItems, String name, String description, int sku, double price, Class exception) throws InvalidSKUException, InvalidInventoryParameterException, DuplicateItemEntryException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        im = createAndPopulateInventory(inventoryItems);
        // Act
        if (exception== null) {
            im.addNewItemToStore(name, description, sku, price, 5);
            // Assert

            assertEquals(im.checkForSKUInSystem(sku), true);
            assertEquals(im.obtainItemBySKU(sku).getStockCount(), 5);
            assertEquals(im.obtainItemBySKU(sku).getDescription(), description);
            assertEquals(im.obtainItemBySKU(sku).getName(), name);
            assertEquals(im.obtainItemBySKU(sku).getPrice().getValue(), price);
            assertEquals(im.obtainItemBySKU(sku).getItemNumber(), sku);
        }
        else {
            assertThrows(exception,          ()->   im.addNewItemToStore(name, description, sku, price, 1));
        }
    }

    /**
     * This method returns a set of tests that verify the add new customer method.
     * @return An array of objects, (Initial customers in system, First Name, Last Name, age, Address, City, State, Zip, boolean as to whether the test is valid.)
     */
    @DataProvider(name = "addNewInventoryToStoreC25-data-provider")
    public Object[][] addNewInventoryDPC25() {
        // Layout <<Initial Customers>, Fname, LName, age, Address, City, State, Zip, exception generated>
        return new Object[][]{
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 29.99, 0, null},
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 29.99, 1, null},
                {new InventoryItem[]{}, "DevOpsBook", "A full book of DevOps", 10001, 29.99, -1, InvalidInventoryParameterException.class},
        };
    }

    /**
     */
    @Test(dataProvider="addNewInventoryToStoreC25-data-provider", groups = {"professor"})
    public void testAddNewInventoryWithCountC25(InventoryItem[] inventoryItems, String name, String description, int sku, double price, int count, Class exception) throws InvalidSKUException, InvalidInventoryParameterException, DuplicateItemEntryException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        im = createAndPopulateInventory(inventoryItems);

        // Act
        if (exception== null) {
            im.addNewItemToStore(name, description, sku, price, count);
            // Assert

            assertEquals(im.checkForSKUInSystem(sku), true);
            assertEquals(im.obtainItemBySKU(sku).getStockCount(), count);
            assertEquals(im.obtainItemBySKU(sku).getDescription(), description);
            assertEquals(im.obtainItemBySKU(sku).getName(), name);
            assertEquals(im.obtainItemBySKU(sku).getPrice().getValue(), price);
            assertEquals(im.obtainItemBySKU(sku).getItemNumber(), sku);
        }
        else {
            assertThrows(exception,          ()->   im.addNewItemToStore(name, description, sku, price, count));
        }
    }



    /**
     * This method returns a set of tests that verify the add new customer method.
     * @return An array of objects, (Initial customers in system, First Name, Last Name, age, Address, City, State, Zip, boolean as to whether the test is valid.)
     */
    @DataProvider(name = "skuChecksC23C24-data-provider")
    public Object[][] checkSKUYDP() {
        InventoryItem ii1 = new InventoryItem("Paperclip", "Ernies Paperclips", 1010, 1, new Money(0.99));
        InventoryItem ii2 = new InventoryItem("Soap", "Dove Safe Soap", 5020, 100, new Money(10.00));

        // Layout <<Initial Customers>, Fname, LName, age, Address, City, State, Zip, exception generated>
        return new Object[][]{
                {new InventoryItem[]{ii1, ii2}, 10001,  false,  null}, // C24 coverage
                {new InventoryItem[]{ii1, ii2}, 1010,  true,  null},
                {new InventoryItem[]{ii1, ii2}, 0,  false,  InvalidSKUException.class},
                {new InventoryItem[]{ii1, ii2}, -1,  false,  InvalidSKUException.class},
        };
    }

    @Test(dataProvider="skuChecksC23C24-data-provider", groups = {"professor"})
    public void testcheckForSKUInSystem(InventoryItem[] inventoryItems, int sku, boolean present,  Class exception) throws InvalidSKUException, InvalidInventoryParameterException, DuplicateItemEntryException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        im = createAndPopulateInventory(inventoryItems);
        // Act
        if (exception== null) {
            // Act and assert here.
            assertEquals(im.checkForSKUInSystem(sku), present);
        }
        else {
            assertThrows(exception, ()->   im.checkForSKUInSystem(sku));
        }
    }

    @Test(dataProvider="skuChecksC23C24-data-provider", groups = {"professor"})
    public void testobtainItemBySKU(InventoryItem[] inventoryItems, int sku, boolean present,  Class exception) throws InvalidSKUException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        im = createAndPopulateInventory(inventoryItems);

        // Act
        if (exception== null) {
            // Act and assert here.
            assertEquals(im.obtainItemBySKU(sku)!=null, present);
        }
        else {
            assertThrows(exception, ()->   im.obtainItemBySKU(sku));
        }
    }



    @DataProvider(name = "stockTestsC23C24C26C27DP")
    public Object[][] c23c24c27c26DP() {
        InventoryItem ii1 = new InventoryItem("Paperclip", "Ernies Paperclips", 1010, 1, new Money(0.99));
        InventoryItem ii2 = new InventoryItem("Soap", "Dove Safe Soap", 5020, 100, new Money(10.00));
        InventoryItem ii3 = new InventoryItem("Soap", "Dove Safe Soap", 5050, 0, new Money(10.00));

        // Layout <<Initial Customers>, Fname, LName, age, Address, City, State, Zip, exception generated>
        return new Object[][]{
                {new InventoryItem[]{ii1, ii2, ii3}, 5050,  1, null}, // C26 coverage
                {new InventoryItem[]{ii1, ii2, ii3}, 1010,  1, null}, // C26 coverage
                {new InventoryItem[]{ii1, ii2, ii3}, 5020,  1, null}, // C26 coverage

                {new InventoryItem[]{ii1, ii2, ii3}, 5020,  -1, InvalidParameterException.class}, // C27 coverage
                {new InventoryItem[]{ii1, ii2, ii3}, 5020,  0, null}, // C27 coverage

                {new InventoryItem[]{ii1, ii2}, 1010, 0, null}, // C23 Coverage
                {new InventoryItem[]{ii1, ii2}, 0,  0,  InvalidSKUException.class},
                {new InventoryItem[]{ii1, ii2}, -1,  0,  InvalidSKUException.class},

                {new InventoryItem[]{ii1, ii2}, 10001, 10, InvalidSKUException.class}, // C24 coverage
                {new InventoryItem[]{ii1, ii2}, 1010,  10, null},
        };
    }



    @Test(dataProvider="stockTestsC23C24C26C27DP", groups = {"professor"})
    public void testAddSTock(InventoryItem[] inventoryItems, int sku, int newStock, Class exception) throws InvalidSKUException, InvalidInventoryParameterException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        im = createAndPopulateInventory(inventoryItems);

        // Act
        if (exception== null) {
            InventoryItem iv = im.obtainItemBySKU(sku);

            int initialSTockCount = iv.getStockCount();
            im.addStock(sku, newStock);

            assertEquals(iv.getStockCount(), initialSTockCount+newStock);
        }
        else {
            assertThrows(exception, ()->   im.addStock(sku, newStock));
        }
    }

    @Test(dataProvider="stockTestsC23C24C26C27DP", groups = {"professor"})
    public void testReturnSTock(InventoryItem[] inventoryItems, int sku, int newStock, Class exception) throws InvalidSKUException, InvalidInventoryParameterException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        im = createAndPopulateInventory(inventoryItems);

        // Act
        if (exception== null) {
            InventoryItem iv = im.obtainItemBySKU(sku);

            int initialSTockCount = iv.getStockCount();
            im.returnStock(sku, newStock);

            assertEquals(iv.getStockCount(), initialSTockCount+newStock);
        }
        else {
            assertThrows(exception, ()->   im.returnStock(sku, newStock));
        }
    }








}
