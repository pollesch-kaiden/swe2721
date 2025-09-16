package msoe.swe2721.lab4_5.PROF;

import msoe.swe2721.lab4_5.provided.*;
import msoe.swe2721.lab4_5.provided.exceptions.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static org.testng.Assert.*;

public class ProfessorTestOrderManager {
    // This is the inventory manager to use in tests.
    private InventoryManager im = null;

    private CustomerManager cm;

    private OrderManager omi;

    private Customer[] definedCustomers = {
            new Customer("Robert","Smith",32,"123456789 Main Street","Milwaukee",State.WISCONSIN,53202),
            new Customer("Isabella","Rnaught",28,"987654321 Countdown Street","Detroit",State.MICHIGAN,48211)
    };

    public ProfessorTestOrderManager() throws InvalidCustomerParameterException {
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws InvalidSKUException, InvalidInventoryParameterException, DuplicateItemEntryException, DuplicateCustomerException, InvalidCustomerParameterException, NoSuchFieldException, IllegalAccessException {
        im = new InventoryManager();
        im.addNewItemToStore("Widget 100", "One the zeroth day of Christmas, my true love sent to me 0 widget in a pear tree.", 100, 9.99, 0);
        im.addNewItemToStore("Widget 101", "One the first day of Christmas, my true love sent to me 1 widget in a pear tree.", 101, 9.99, 1);
        im.addNewItemToStore("Widget 110", "One the tenth day of Christmas, my true love sent to me 10 widget in a pear tree.", 110, 9.99, 10);
        im.addNewItemToStore("Widget 120", "One the twentieth day of Christmas, my true love sent to me 20 widgets in a pear tree.", 120, 18.88, 20);

        cm = new CustomerManager();

        // Create Field object
        Field privateField = CustomerManager.class.getDeclaredField("customers");

        // Set the accessibility as true
        privateField.setAccessible(true);

        // Pull out the collection of customers.
        HashMap<Integer, Customer> internalCustomers = (HashMap)privateField.get(cm);

        for (int index = 0; index < definedCustomers.length; index++)
        {
            internalCustomers.put(definedCustomers[index].getId(), definedCustomers[index]);
        }
        omi = new OrderManager(cm, im);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        omi = null;
        im = null;
        cm = null;
    }

    /**
     * This method returns a set of tests that verify the add new customer method.
     *
     * @return An array of objects, (Initial customers in system, First Name, Last Name, age, Address, City, State, Zip, boolean as to whether the test is valid.)
     */
    @DataProvider(name = "constructorC30C31DP")
    public Object[][] constructorDP() {
        return new Object[][]{
                {new CustomerManager(), new InventoryManager(), null},
                {null, new InventoryManager(), InvalidParameterException.class},
                {new CustomerManager(), null, InvalidParameterException.class}
        };
    }

    /**
     *
     */
    @Test(dataProvider = "constructorC30C31DP", groups = {"professor"})
    public void testConstructor(CustomerManagerInterface cmi, InventoryManagerInterface imi, Class exception) {
        if (exception == null) {
            omi = new OrderManager(cmi, imi);
            assertNotNull(omi);
        } else {
            assertThrows(exception, () -> new OrderManager(cmi, imi));
        }
    }


    /**
     * This method will test the obtain order count method.
     * @return An array of objects, (# of orders placed, orderSequenceNumber, ExceptionThrown)
     */
    @DataProvider(name = "obtainOrderC32C33DP")
    public Object[][] obtainOrderC32C33DP() {
        return new Object[][]{
                {0, 1, InvalidParameterException.class},
                {1, -1, InvalidParameterException.class },
                {1, 2, InvalidParameterException.class},
                {1, 1, null},
                {2, 1, null},
                {2, 2, null},
                {2, 3, InvalidParameterException.class},
                {2, -1, InvalidParameterException.class},
        };
    }

    /**
     *
     */
    @Test(dataProvider = "obtainOrderC32C33DP", groups = {"professor"})
    public void testobtainOrderC32C33DP(int existingOrders, int orderToQuery, Class exceptionExpected) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {

        Order orders[] = new Order[10];
        // Arrange
        for (int index = 0; index < existingOrders; index++)
        {
             orders[index] = omi.OrderItem(definedCustomers[0].getId(), 120, 1);
        }

        if (exceptionExpected!=null) {
            assertEquals(existingOrders, omi.obtainOrderCount());
            assertThrows(exceptionExpected, () -> omi.obtainOrder(orderToQuery));
        }
        else {
            assertEquals(existingOrders, omi.obtainOrderCount());
            assertEquals(orders[orderToQuery-1], omi.obtainOrder(orderToQuery));
        }
    }

    /**
     * @return An array of objects, (id, customer Instance, number ordered, sku, ExceptionThrown)
     */
    @DataProvider(name = "orderItemsDPC34C35C36C37C38")
    public Object[][] orderItemDP() {
        return new Object[][]{
                // Customer id, stock ordered, sku,
                {definedCustomers[0].getId(), definedCustomers[0], 1, 1, 120, null },
                {-1, null, 1, 1, 120, InvalidCustomerParameterException.class },
                {36768, null, 1, 1, 120, InvalidCustomerParameterException.class },
                {definedCustomers[0].getId(), definedCustomers[0], 1, 0, 100, InventoryOutOfStockException.class },
                {definedCustomers[0].getId(), definedCustomers[0], 1, 1, 101, null },
                {definedCustomers[0].getId(), definedCustomers[0], 2, 1, 101, null },
                {definedCustomers[0].getId(), definedCustomers[0], 1, 1, -1, InvalidSKUException.class },
                {definedCustomers[0].getId(), definedCustomers[0], 1, 1, 0, InvalidSKUException.class },
                {definedCustomers[0].getId(), definedCustomers[0], 1, 1, 99, InvalidSKUException.class },
        };
    }

    /**
     *
     */
    @Test(dataProvider = "orderItemsDPC34C35C36C37C38", groups = {"professor"})
    public void testOrderItem1(int id, Customer customer, int countRequested, int countExpected, int sku, Class exception) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {
        if (exception == null) {
            // Arrange is done in setup.

            // Act
            Order o = omi.OrderItem(id, sku, countRequested);

            // Assert
            assertEquals(o.getItem().getItemNumber(), sku);
            assertEquals(o.getCount(), countExpected);
            assertSame(o.getCustomer(), customer);
        } else {
            assertThrows(exception, ()->omi.OrderItem(id, sku, countRequested));
        }
    }

    /**
     * @return An array of objects, (fname, lname, customer Instance, number ordered, sku, ExceptionThrown)
     */
    @DataProvider(name = "orderItemsDPC39C40C41C42C36C37C38")
    public Object[][] orderItemDP2() {
        return new Object[][]{
                // Customer id, stock ordered, sku,
                {definedCustomers[0].getFirstName(), definedCustomers[0].getLastName(), definedCustomers[0], 1, 1, 120, null },
                {".", definedCustomers[0].getLastName(), definedCustomers[0], 1, 1, 120, InvalidCustomerParameterException.class },
                {definedCustomers[0].getFirstName(), ".", definedCustomers[0], 1, 1, 120, InvalidCustomerParameterException.class },
                {"Nottobefound", definedCustomers[0].getLastName(), definedCustomers[0], 1, 1, 120, InvalidCustomerParameterException.class },
                {definedCustomers[0].getFirstName(), "Lastmanedoesntexist", definedCustomers[0], 1, 1, 120, InvalidCustomerParameterException.class },
                {definedCustomers[0].getFirstName(), definedCustomers[0].getLastName(), definedCustomers[0], 1, 0, 100, InventoryOutOfStockException.class },
                {definedCustomers[0].getFirstName(), definedCustomers[0].getLastName(), definedCustomers[0], 1, 1, 101, null },
                {definedCustomers[0].getFirstName(), definedCustomers[0].getLastName(), definedCustomers[0], 2, 1, 101, null },
                {definedCustomers[0].getFirstName(), definedCustomers[0].getLastName(), definedCustomers[0], 1, 1, -1, InvalidSKUException.class },
                {definedCustomers[0].getFirstName(), definedCustomers[0].getLastName(), definedCustomers[0], 1, 1, 0, InvalidSKUException.class },
                {definedCustomers[0].getFirstName(), definedCustomers[0].getLastName(), definedCustomers[0], 1, 1, 99, InvalidSKUException.class },
        };
    }

    /**
     *
     */
    @Test(dataProvider = "orderItemsDPC39C40C41C42C36C37C38", groups = {"professor"})
    public void testOrderItem2(String fname, String lname,  Customer customer, int countRequested, int countExpected, int sku, Class exception) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {
        if (exception == null) {
            // Arrange is done in setup.

            // Act
            Order o = omi.OrderItem(fname, lname, sku, countRequested);

            // Assert
            assertEquals(o.getItem().getItemNumber(), sku);
            assertEquals(o.getCount(), countExpected);
            assertSame(o.getCustomer(), customer);
            assertEquals(omi.obtainOrderCount(), 1);
        } else {
            assertThrows(exception, ()->omi.OrderItem(fname, lname, sku, countRequested));
            assertEquals(omi.obtainOrderCount(), 0);
        }
    }


}
