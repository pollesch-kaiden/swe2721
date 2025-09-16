/*
 * Course: SWE2721-121
 * Spring 2025
 * Lab 4 - Inventory Lab
 * Name: Kaiden Pollesch
 * Created 2/25/2025
 */
package msoe.swe2721.lab4_5;

import msoe.swe2721.lab4_5.provided.Customer;
import msoe.swe2721.lab4_5.provided.CustomerManager;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Course: SWE2721-121
 * Spring 2025
 * Class CustomerManagerTest Purpose: Test CustomerManagerClass
 *
 * @author polleschk
 * SWE2721-121 Laboratory Assignment
 * @version created on 2/25/2025 3:17 PM
 */
public class CustomerManagerTest {
    private CustomerManager customerManager;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        customerManager = new CustomerManager();
        customerManager.clearCustomers();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        customerManager = null;
    }

    @DataProvider(name = "customerData")
    public Object[][] customerData() {
        return new Object[][] {
            {"Jane", "Smith", 25, "456 Elm St", "Madison", "WI", 53703, 1},
            {"John", "Doe", 30, "123 Main St", "Milwaukee", "WI", 53202, 1},
            {"Bob", "Johnson", 40, "789 Oak St", "Green Bay", "WI", 54301, 1}
        };
    }

    @Test(dataProvider = "customerData", groups = {"all", "student1", "polleschk"})
    public void testAddNewCustomer(String firstName, String lastName, int age, String streetAddress, String city, String state, int zip, int expectedId) throws InvalidCustomerParameterException, DuplicateCustomerException {
        int id = customerManager.addNewCustomer(firstName, lastName, age, streetAddress, city, state, zip);
        assertEquals(id, expectedId);
    }
    @Test(expectedExceptions = InvalidCustomerParameterException.class, groups = {"all", "student1", "polleschk"})
    public void testAddNewCustomerInvalidZip() throws InvalidCustomerParameterException, DuplicateCustomerException {
        customerManager.addNewCustomer("Jane", "Doe", 25, "456 Elm St", "Madison", "Wisconsin", 99999);
    }

    @Test(expectedExceptions = DuplicateCustomerException.class, groups = {"all", "student1", "polleschk"})
    public void testAddDuplicateCustomer() throws InvalidCustomerParameterException, DuplicateCustomerException {
        customerManager.addNewCustomer("John", "Doe", 30, "123 Main St", "Milwaukee", "Wisconsin", 53202);
        customerManager.addNewCustomer("John", "Doe", 30, "123 Main St", "Milwaukee", "Wisconsin", 53202);
    }

    @Test (groups = {"all", "student1", "polleschk"})
    public void testFindCustomerByName() throws InvalidCustomerParameterException, DuplicateCustomerException {
//        customerManager.addNewCustomer("John", "Doe", 30, "123 Main St", "Milwaukee", "Wisconsin", 53202);
        customerManager.addNewCustomer("Jane", "Doe", 29, "124 Main St", "Milwaukee", "Wisconsin", 53202);
        Customer customer = customerManager.findCustomerByName("Jane", "Doe");
        assertNotNull(customer);
        assertEquals(customer.getFirstName(), "Jane");
        assertEquals(customer.getLastName(), "Doe");
    }

    @Test (groups = {"all", "student1", "polleschk"})
    public void testFindCustomerByID() throws InvalidCustomerParameterException, DuplicateCustomerException {
        int id1 = customerManager.addNewCustomer("John", "Doe", 30, "123 Main St", "Milwaukee", "Wisconsin", 53202);
        int id2 = customerManager.addNewCustomer("Jane", "Doe", 29, "124 Main St", "Milwaukee", "Wisconsin", 53202);
        int id3 = customerManager.addNewCustomer("LON", "Doe", 54, "124 Main St", "Milwaukee", "Wisconsin", 53202);
        System.out.println(id1);
        Customer customer = customerManager.findCustomerByID(id1);
        assertNotNull(customer);
        assertEquals(customer.getId(), id1);
    }

    @Test(expectedExceptions = InvalidCustomerParameterException.class, groups = {"all", "student1", "polleschk"})
    public void testFindCustomerByIDInvalid() throws InvalidCustomerParameterException {
        customerManager.findCustomerByID(-1);
    }

    @Test (groups = {"all", "student1", "polleschk"})
    public void testGetCustomerCount() throws InvalidCustomerParameterException, DuplicateCustomerException {
        assertEquals(customerManager.getCustomerCount(), 0);
        customerManager.addNewCustomer("John", "Doe", 30, "123 Main St", "Milwaukee", "Wisconsin", 53202);
        customerManager.addNewCustomer("Jane", "Doe", 29, "124 Main St", "Milwaukee", "Wisconsin", 53202);
        assertEquals(customerManager.getCustomerCount(), 2);
    }
}