package msoe.swe2721.lab4_5.PROF;

import msoe.swe2721.lab4_5.provided.Customer;
import msoe.swe2721.lab4_5.provided.CustomerManager;
import msoe.swe2721.lab4_5.provided.State;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.testng.Assert.*;

public class ProfessorTestCustomerManager {
    @BeforeMethod(alwaysRun = true)
    public void setUp() {

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

    }

    /**
     * This method will populate the existing customers into the Customer Manager.
     * @param existingCustomers This is the existing customers to use.
     * @return The return will be a new customer manager.
     * @throws NoSuchFieldException May be thrown if there is no such field.
     */
    private CustomerManager createAndPopulateCustomers(Customer[] existingCustomers) throws NoSuchFieldException, IllegalAccessException {
        CustomerManager cm = new CustomerManager();

        // Create Field object
        Field privateField = CustomerManager.class.getDeclaredField("customers");

        // Set the accessibility as true
        privateField.setAccessible(true);

        // Pull out the collection of customers.
        HashMap<Integer, Customer> internalCustomers = (HashMap)privateField.get(cm);

        for (int index = 0; index < existingCustomers.length; index++)
        {
            internalCustomers.put(existingCustomers[index].getId(), existingCustomers[index]);
        }
        return cm;
    }

    /**
     * This method tests the default constructor, makign sure it functions properly.
     */
    @Test(groups="professor")
    public void testDefaultConstructor()
    {
        // Arrange
        CustomerManager cm;
        // Act
        cm = new CustomerManager();
        // Assert
        assertNotNull(cm);
        assertEquals(cm.getCustomerCount(), 0);
    }

    /**
     * This method returns a set of tests that verify the add new customer method.
     * @return An array of objects, (Initial customers in system, First Name, Last Name, age, Address, City, State, Zip, boolean as to whether the test is valid.)
     */
    @DataProvider(name = "addNewCustomer-data-provider")
    public Object[][] constructorDP() throws InvalidCustomerParameterException {
        Customer c1 = new Customer("Bob", "Jones", 22, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
        Customer c2 = new Customer("Theresa", "Jones", 23, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
        // Layout <<Initial Customers>, Fname, LName, age, Address, City, State, Zip, exception generated>
        return new Object[][]{
                {new Customer[]{}, "Katie", "Wonderjones", 25, "12345 N Broadway", "Willoughby", "OH", 44095, false},
                {new Customer[]{}, "K", "Wonderjones", 25, "12345 N Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, null, "Wonderjones", 25, "12345 N Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Katie", "W", 25, "12345 N Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Katie", null, 25, "12345 N Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Katie", "Wonderjones", -1, "12345 N Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Katie", "Wonderjones", 0, "12345 N Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Katie", "Wonderjones", 19, "12345 N Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Katie", "Wonderjones", 21, "12345 N Broadway", "Willoughby", "OH", 44095, false},
                {new Customer[]{}, "Katie", "Wonderjones", 23, "12345 N Broadway", "Willoughby", "OH", 44095, false},
                {new Customer[]{}, "Lauren", "Program", 23, "Main Broadway", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Lauren", "Program", 23, "Broadway", "Willoughby", "OH", 44095, true},
                //{new Customer[]{}, "Lauren", "Program", 23, "1 B", "Willoughby", "OH", 44095, true},
                {new Customer[]{}, "Cee", "Programer", 23, "1 Broad Street", "W", "OH", 44095, true},
                {new Customer[]{}, "Cee", "Programer", 23, "1 Broad Street", "Willoughby Hills", "OHIO", 44095, true},
                {new Customer[]{}, "Cee", "Programer", 23, "1 Broad Street", "Willoughby Hills", "O", 44095, true},
                {new Customer[]{}, "Dea", "Programers", 23, "1 Java Street", "Willoughby Hills", "OH", -1, true},
                {new Customer[]{}, "Dea", "Programers", 23, "1 Java Street", "Willoughby Hills", "OH", 100000, true},
                {new Customer[]{}, "Dea", "Programers", 23, "1 Java Street", "Willoughby Hills", "OH", 44097, true}, // Note that 44097 is not a listed zip code.
                {new Customer[]{}, "Dea", "Programers", 23, "1 Java Street", "Willoughby Hills", "PA", 44095, true}, // City and state do not match.
                {new Customer[]{c1}, "Bob", "Jones", 22, "1025 N Broadway", "Milwaukee", "WI", 53202, true},
                {new Customer[]{c1}, "Bob", "Jones", 22, "1025 N Broadway", "Milwaukee", "WI", 53202, true},
        };
    }

    /**
     * Test the add new customer method.
     * @param existingCustomers This is the array of existing customers.
     * @param firstName First name of the customer to try and add.
     * @param lastName Last name of the customer to try and add.
     * @param age Age of the customer to try and add.
     * @param streetAddress Address of customer to try and add.
     * @param city City of customer to try and add.
     * @param state State of customer to try and add.
     * @param zip Zip of customer to try and add.
     * @param exceptionExpected True if an exception should be thrown.  False OTW.
     * @throws DuplicateCustomerException May be thrown.
     * @throws InvalidCustomerParameterException May be thrown.
     */
    @Test(dataProvider="addNewCustomer-data-provider", groups = {"professor"})
    public void testAddNewCustomer(Customer[] existingCustomers, String firstName, String lastName, int age, String streetAddress,
                                   String city, String state, int zip, boolean exceptionExpected) throws DuplicateCustomerException, InvalidCustomerParameterException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        CustomerManager cm = createAndPopulateCustomers(existingCustomers);

        // Act
        if (exceptionExpected && existingCustomers.length == 0) {
            assertThrows(InvalidCustomerParameterException.class, () -> cm.addNewCustomer(firstName, lastName, age, streetAddress, city, state, zip));
        } else if (exceptionExpected && existingCustomers.length != 0) {
            assertThrows(DuplicateCustomerException.class, () -> cm.addNewCustomer(firstName, lastName, age, streetAddress, city, state, zip));
        } else {
            int id = cm.addNewCustomer(firstName, lastName, age, streetAddress, city, state, zip);
            assertEquals(id, Customer.getSerialNumber());
        }
    }


    @DataProvider(name = "findCustomerByName-data-provider")
    public Object[][] findCustomerDP() throws InvalidCustomerParameterException {
        Customer c1 = new  Customer("Bob", "Jones", 22, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
        Customer c2 = new Customer("Theresa", "Jones", 23, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
        // Layout <<Initial Customers>, Fname, LName, item to be found>
        return new Object[][]{
                {new Customer[]{c1}, "Bob", "Jones", c1},
                {new Customer[]{c1}, " Bob", "Jones", c1},
                {new Customer[]{c1}, "Bob ", "Jones", c1},
                {new Customer[]{c1}, " Bob ", "Jones", c1},

                {new Customer[]{c1}, "Bob", " Jones", c1},
                {new Customer[]{c1}, "Bob", "Jones ", c1},
                {new Customer[]{c1}, " Bob", " Jones ", c1},

                {new Customer[]{c2}, "Bob", "Jones", null},
                {new Customer[]{c1, c2}, "Theresa", "Jones", c2},
                {new Customer[]{c1, c2}, "Indiana", "Jones", null},
                {new Customer[]{c1, c2}, null, "Jones", null},
                {new Customer[]{c1, c2}, "Theresa", null, null},
        };
    }
    @Test(dataProvider="findCustomerByName-data-provider", groups = {"professor"})
    public void testFindCustomerByName(Customer[] existingCustomers, String firstName, String lastName, Customer expectedReturn) throws NoSuchFieldException, IllegalAccessException, InvalidCustomerParameterException {
        // Arrange
        CustomerManager cm = createAndPopulateCustomers(existingCustomers);

        // Act
        Customer c = cm.findCustomerByName(firstName, lastName);

        // Assert
        assertEquals(c, expectedReturn);

    }


    @DataProvider(name = "findCustomerByID-data-provider")
    public Object[][] findCustomerIDDP() throws InvalidCustomerParameterException {
        Customer c1 = new Customer("Bob", "Jones", 22, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
        Customer c2 = new Customer("Theresa", "Jones", 23, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
    // Layout <<Initial Customers>, Fname, LName, item to be found>
    return new Object[][]{
            {new Customer[]{}, c1.getId(), null, false},
            {new Customer[]{c1}, c1.getId(), c1, false},
            {new Customer[]{c2}, c1.getId(), null, false},
            {new Customer[]{c1}, -1,         null, true},
    };
}
    @Test(dataProvider="findCustomerByID-data-provider", groups = {"professor"})
    public void testFindCustomerByID(Customer[] existingCustomers, int id, Customer expectedReturn, boolean exceptionExpected) throws InvalidCustomerParameterException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        CustomerManager cm = createAndPopulateCustomers(existingCustomers);

        if (!exceptionExpected) {
            // Act
            Customer c = cm.findCustomerByID(id);

            // Assert
            assertEquals(c, expectedReturn);
        } else {
            assertThrows(InvalidCustomerParameterException.class, () -> cm.findCustomerByID(id));
            ;
        }
    }

    @DataProvider(name = "getCustomerCount-data-provider")
    public Object[][] getCustomerCountDP() throws InvalidCustomerParameterException {
        Customer c1 = new Customer("Bob", "Jones", 22, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
        Customer c2 = new Customer("Theresa", "Jones", 23, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);
        Customer c3 = new Customer("Veronica", "Jones", 25, "1025 N Broadway", "Milwaukee", State.WISCONSIN, 53202);

        // Layout <<Initial Customers>, Fname, LName, item to be found>
        return new Object[][]{
                {new Customer[]{}, 0},
                {new Customer[]{c1}, 1},
                {new Customer[]{c1, c2}, 2},
                {new Customer[]{c1, c2, c3}, 3},
        };
    }
    @Test(dataProvider="getCustomerCount-data-provider", groups = {"professor"})
    public void testgetCustomerCount(Customer[] existingCustomers, int count) throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        CustomerManager cm = createAndPopulateCustomers(existingCustomers);

        // Act
        int number = cm.getCustomerCount();

        // Assert
        assertEquals(number, count);

    }
}
