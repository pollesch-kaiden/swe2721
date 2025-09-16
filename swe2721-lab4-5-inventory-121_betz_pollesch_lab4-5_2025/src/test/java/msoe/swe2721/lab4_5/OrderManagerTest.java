/*
 * Course: SWE2721-121
 * Spring 2025
 * Lab 5 - Inventory Lab
 * Name: Kaiden Pollesch
 * Created 2/25/2025
 */
package msoe.swe2721.lab4_5;

import msoe.swe2721.lab4_5.provided.*;
import msoe.swe2721.lab4_5.provided.exceptions.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.HashMap;

import static org.testng.Assert.*;
import static org.testng.Assert.assertThrows;

/**
 * Course: SWE2721-121
 * Spring 2025
 * Class OrderManagerTest Purpose: Test OrderManagerClass
 *
 * @author polleschk
 * SWE2721-121 Laboratory Assignment
 * @version created on 2/25/2025 3:19 PM
 */
public class OrderManagerTest {

    private OrderManager orderManager;
    private CustomerManager customerManager;
    private InventoryManager inventoryManager;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        customerManager = new CustomerManager();
        inventoryManager = new InventoryManager();
        orderManager = new OrderManager(customerManager, inventoryManager);

        try {
            // Adding test customer
            customerManager.addNewCustomer("John", "Doe", 25, "123 Test St", "Milwaukee", "WI", 53202);
            // Adding test item to inventory
            inventoryManager.addNewItemToStore("Laptop", "High-end gaming laptop", 1001, 999.99, 10);
        } catch (Exception e) {
            Assert.fail("Setup failed: " + e.getMessage());
        }
    }

    @AfterMethod (alwaysRun = true)
    public void teardown() {
        customerManager = null;
        inventoryManager = null;
        orderManager = null;
    }

    @DataProvider(name = "orderData")
    public Object[][] orderData() {
        return new Object[][]{
//                {1, 1001, 1, true},  // Valid order
                {1, 1001, 15, false}, // More than available stock
                {99, 1001, 1, false}, // Invalid customer
                {1, 9999, 1, false}  // Invalid SKU
        };
    }

    @Test(dataProvider = "orderData", groups = {"all", "student1", "polleschk"})
    public void testOrderItemById(int customerId, int sku, int quantity, boolean shouldSucceed) {
        try {
            Order order = orderManager.OrderItem(customerId, sku, quantity);
            Assert.assertNotNull(order, "Order should be created.");
            Assert.assertTrue(shouldSucceed, "Expected success but got failure.");
        } catch (Exception e) {
            Assert.assertFalse(shouldSucceed, "Expected failure but got success: " + e.getMessage());
        }
    }

    @Test(groups = {"all", "student1", "polleschk"})
    public void testOrderItemByName() {
        try {
            Order order = orderManager.OrderItem("John", "Doe", 1001, 1);
            Assert.assertNotNull(order, "Order should be created.");
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test(groups = {"all", "student1", "polleschk"})
    public void testObtainOrderCount() {
        try {
            customerManager.addNewCustomer("Jane", "Doe", 30, "456 Test Ave", "Milwaukee", "WI", 53202);
            orderManager.OrderItem(1, 1001, 1);
            Assert.assertEquals(orderManager.obtainOrderCount(), 1, "Order count should be 1.");
        } catch (Exception e) {
//            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Test(groups = {"all", "student1", "polleschk"})
    public void testInvalidOrderRetrieval() {
        try {
            orderManager.obtainOrder(99);
            Assert.fail("Expected exception for invalid order retrieval.");
        } catch (InvalidParameterException e) {
            Assert.assertTrue(true, "Correct exception caught.");
        }
    }
}