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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
    private InventoryManager inventoryManager = null;
    private CustomerManager customerManager;
    private OrderManager orderManager;
    private Customer[] customers = {
            new Customer("Alice", "Johnson", 30, "123 Apple St", "New York", State.NEW_YORK, 10001),
            new Customer("Bob", "Williams", 45, "456 Orange St", "Los Angeles", State.CALIFORNIA, 90001)
    };

    @BeforeMethod(alwaysRun = true)
    public void initialize() throws InvalidSKUException, InvalidInventoryParameterException, DuplicateItemEntryException, DuplicateCustomerException, InvalidCustomerParameterException, NoSuchFieldException, IllegalAccessException {
        inventoryManager = new InventoryManager();
        inventoryManager.addNewItemToStore("Gadget 200", "Description for Gadget 200", 200, 19.99, 5);
        inventoryManager.addNewItemToStore("Gadget 201", "Description for Gadget 201", 201, 29.99, 10);
        inventoryManager.addNewItemToStore("Gadget 202", "Description for Gadget 202", 202, 39.99, 15);
        inventoryManager.addNewItemToStore("Gadget 203", "Description for Gadget 203", 203, 49.99, 20);

        customerManager = new CustomerManager();

        Field privateField = CustomerManager.class.getDeclaredField("customers");
        privateField.setAccessible(true);
        HashMap<Integer, Customer> internalCustomers = (HashMap) privateField.get(customerManager);

        for (Customer customer : customers) {
            internalCustomers.put(customer.getId(), customer);
        }
        orderManager = new OrderManager(customerManager, inventoryManager);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        orderManager = null;
        inventoryManager = null;
        customerManager = null;
    }

    @DataProvider(name = "constructorDataProvider")
    public Object[][] constructorDataProvider() {
        return new Object[][]{
                {new CustomerManager(), new InventoryManager(), null},
                {null, new InventoryManager(), InvalidParameterException.class},
                {new CustomerManager(), null, InvalidParameterException.class}
        };
    }

    @Test(dataProvider = "constructorDataProvider", groups = {"all", "student1", "polleschk"})
    public void testConstructor(CustomerManagerInterface customerManagerInterface, InventoryManagerInterface inventoryManagerInterface, Class exception) {
        if (exception == null) {
            orderManager = new OrderManager(customerManagerInterface, inventoryManagerInterface);
            assertNotNull(orderManager);
        } else {
            assertThrows(exception, () -> new OrderManager(customerManagerInterface, inventoryManagerInterface));
        }
    }

    @DataProvider(name = "orderDataProvider")
    public Object[][] orderDataProvider() {
        return new Object[][]{
                {8, 1, InvalidParameterException.class},
                {9, -1, InvalidParameterException.class},
                {10, 2, InvalidParameterException.class},
                {10, 1, null},
                {10, 1, null},
                {10, 2, null},
                {10, 3, InvalidParameterException.class},
                {10, -1, InvalidParameterException.class},
        };
    }

    @Test(dataProvider = "orderDataProvider", groups = {"all", "student1", "polleschk"})
    public void testOrderDataProvider(int existingOrders, int orderToQuery, Class exceptionExpected) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {
        Order[] orders = new Order[10];
        for (int index = 0; index < existingOrders; index++) {
            orders[index] = orderManager.OrderItem(customers[0].getId(), 203, 1);
        }

        if (exceptionExpected != null) {
            assertEquals(existingOrders, orderManager.obtainOrderCount());
            assertThrows(exceptionExpected, () -> orderManager.obtainOrder(orderToQuery));
        } else {
            assertEquals(existingOrders, orderManager.obtainOrderCount());
            assertEquals(orders[orderToQuery - 1], orderManager.obtainOrder(orderToQuery));
        }
    }

    @DataProvider(name = "itemOrderDataProvider")
    public Object[][] itemOrderDataProvider() {
        return new Object[][]{
                {customers[0].getId(), customers[0], 1, 1, 203, null},
                {-1, null, 1, 1, 203, InvalidCustomerParameterException.class},
                {99999, null, 1, 1, 203, InvalidCustomerParameterException.class},
                {customers[0].getId(), customers[0], 1, 0, 200, InventoryOutOfStockException.class},
                {customers[0].getId(), customers[0], 1, 1, 201, null},
                {customers[0].getId(), customers[0], 2, 1, 201, null},
                {customers[0].getId(), customers[0], 1, 1, -1, InvalidSKUException.class},
                {customers[0].getId(), customers[0], 1, 1, 0, InvalidSKUException.class},
                {customers[0].getId(), customers[0], 1, 1, 199, InvalidSKUException.class},
        };
    }

    @Test(dataProvider = "itemOrderDataProvider", groups = {"all", "student1", "polleschk"})
    public void testItemOrder(int id, Customer customer, int countRequested, int countExpected, int sku, Class exception) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {
        if (exception == null) {
            Order order = orderManager.OrderItem(id, sku, countRequested);
            assertEquals(order.getItem().getItemNumber(), sku);
            assertEquals(order.getCount(), countExpected);
            assertSame(order.getCustomer(), customer);
        } else {
            assertThrows(exception, () -> orderManager.OrderItem(id, sku, countRequested));
        }
    }

    @DataProvider(name = "nameOrderDataProvider")
    public Object[][] nameOrderDataProvider() {
        return new Object[][]{
                {customers[0].getFirstName(), customers[0].getLastName(), customers[0], 1, 1, 203, null},
                {".", customers[0].getLastName(), customers[0], 1, 1, 203, InvalidCustomerParameterException.class},
                {customers[0].getFirstName(), ".", customers[0], 1, 1, 203, InvalidCustomerParameterException.class},
                {"Unknown", customers[0].getLastName(), customers[0], 1, 1, 203, InvalidCustomerParameterException.class},
                {customers[0].getFirstName(), "Unknown", customers[0], 1, 1, 203, InvalidCustomerParameterException.class},
                {customers[0].getFirstName(), customers[0].getLastName(), customers[0], 1, 0, 200, InventoryOutOfStockException.class},
                {customers[0].getFirstName(), customers[0].getLastName(), customers[0], 1, 1, 201, null},
                {customers[0].getFirstName(), customers[0].getLastName(), customers[0], 2, 1, 201, null},
                {customers[0].getFirstName(), customers[0].getLastName(), customers[0], 1, 1, -1, InvalidSKUException.class},
                {customers[0].getFirstName(), customers[0].getLastName(), customers[0], 1, 1, 0, InvalidSKUException.class},
                {customers[0].getFirstName(), customers[0].getLastName(), customers[0], 1, 1, 199, InvalidSKUException.class},
        };
    }

    @Test(dataProvider = "nameOrderDataProvider", groups = {"all", "student1", "polleschk"})
    public void testNameOrder(String firstName, String lastName, Customer customer, int countRequested, int countExpected, int sku, Class exception) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {
        if (exception == null) {
            Order order = orderManager.OrderItem(firstName, lastName, sku, countRequested);
            assertEquals(order.getItem().getItemNumber(), sku);
            assertEquals(order.getCount(), countExpected);
            assertSame(order.getCustomer(), customer);
            assertEquals(orderManager.obtainOrderCount(), 5);
        } else {
            assertThrows(exception, () -> orderManager.OrderItem(firstName, lastName, sku, countRequested));
            assertEquals(orderManager.obtainOrderCount(), 0);
        }
    }
}