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

import static org.testng.Assert.*;

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

    @BeforeMethod (groups = {"all", "student1", "polleschk"})
    public void setUp() {
        customerManager = new CustomerManager();
    }

    @AfterMethod(groups = {"all", "student1", "polleschk"})
    public void tearDown() {
        customerManager = null;
    }

    @DataProvider(name = "validCustomers")
    public Object[][] validCustomers() {
        return new Object[][]{
                {"John", "Doe", 25, "123 Main St", "Milwaukee", "WI", 53202},
                {"Jane", "Smith", 30, "456 Oak Ave", "Willoughby", "OH", 44095}
        };
    }

    @DataProvider(name = "invalidCustomers")
    public Object[][] invalidCustomers() {
        return new Object[][]{
                {"J", "Doe", 25, "123 Main St", "Milwaukee", "WI", 53202}, // First name too short
                {"John", "D", 25, "123 Main St", "Milwaukee", "WI", 53202}, // Last name too short
                {"John", "Doe", 20, "123 Main St", "Milwaukee", "WI", 53202}, // Age below 21
                {"John", "Doe", 25, "Main St", "Milwaukee", "WI", 53202}, // Invalid street
                {"John", "Doe", 25, "123 Main St", "XYZ", "WI", 53202}, // Invalid city
                {"John", "Doe", 25, "123 Main St", "Milwaukee", "XY", 53202}, // Invalid state abbreviation
                {"John", "Doe", 25, "123 Main St", "Milwaukee", "WI", 12345} // Invalid zip code
        };
    }

    @Test(dataProvider = "validCustomers", groups = {"all", "student1", "polleschk"})
    public void testAddNewCustomer_Valid(String firstName, String lastName, int age, String street, String city, String state, int zip) throws Exception {
        int id = customerManager.addNewCustomer(firstName, lastName, age, street, city, state, zip);
        assertNotNull(customerManager.findCustomerByID(id));
    }

    @Test(dataProvider = "invalidCustomers", groups = {"all", "student1", "polleschk"}, expectedExceptions = InvalidCustomerParameterException.class)
    public void testAddNewCustomer_Invalid(String firstName, String lastName, int age, String street, String city, String state, int zip) throws Exception {
        customerManager.addNewCustomer(firstName, lastName, age, street, city, state, zip);
    }

    @Test(dependsOnMethods = "testAddNewCustomer_Valid", groups = {"all", "student1", "polleschk"})
    public void testAddDuplicateCustomer() throws Exception {
        String firstName = "John", lastName = "Doe", street = "123 Main St", city = "Milwaukee", state = "WI";
        int age = 25, zip = 53202;

        customerManager.addNewCustomer(firstName, lastName, age, street, city, state, zip);
        assertThrows(DuplicateCustomerException.class, () ->
                customerManager.addNewCustomer(firstName, lastName, age, street, city, state, zip));
    }

    @Test(groups = {"all", "student1", "polleschk"})
    public void testFindCustomerByName() throws Exception {
        customerManager.addNewCustomer("Alice", "Johnson", 28, "789 Pine St", "Willoughby", "OH", 44095);
        Customer customer = customerManager.findCustomerByName("Alice", "Johnson");
        assertNotNull(customer);
        assertEquals(customer.getFirstName(), "Alice");
        assertEquals(customer.getLastName(), "Johnson");
    }

    @Test(groups = {"all", "student1", "polleschk"}, expectedExceptions = InvalidCustomerParameterException.class)
    public void testFindCustomerByInvalidID() throws InvalidCustomerParameterException {
        customerManager.findCustomerByID(-1);
    }
}
