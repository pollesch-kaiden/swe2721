package msoe.swe2721.lab4_5.provided;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

import static org.testng.Assert.*;

public class CustomerTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        Customer.resetSerialNumber();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Customer.resetSerialNumber();
    }

    @DataProvider(name = "constructor-data-provider")
    public Object[][] constructorDP() {
        return new Object[][]{
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, false},
                {"B", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, true},
                {"Bob", "J", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, true},
                {"Bob", "Jones", -1, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, true},
                {"Bob", "Jones", 21, "", "Milwaukee", State.WISCONSIN, 53202, true},
                {"Bob", "Jones", 21, "1025", "Milwaukee", State.WISCONSIN, 53202, true},
                {"Bob", "Jones", 21, "Broadway", "Milwaukee", State.WISCONSIN, 53202, true},
                {"Bob", "Jones", 21, "1025 N. Broadway", "M", State.WISCONSIN, 53202, true},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 0, true},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, -1, true},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 500, true},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 501, false},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 502, false},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 99949, false},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 99950, false},
                {"Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 99951, true}};
    }

    @Test(dataProvider = "constructor-data-provider", groups = {"provided"})
    public void testConstructor(String firstName, String lastName, int age, String streetAddress, String city, State state, int zip, boolean exceptionExpected) {
        // Arrange
        Customer c;

        // Act
        if (exceptionExpected) {
            assertThrows(InvalidParameterException.class, () -> new Customer(firstName, lastName, age, streetAddress, city, state, zip));
        } else {
            c = new Customer(firstName, lastName, age, streetAddress, city, state, zip);
            // Assert
            assertEquals(c.getFirstName(), firstName.trim());
            assertEquals(c.getLastName(), lastName.trim());
            assertEquals(c.getAge(), age);
            assertEquals(c.getStreetAddress(), streetAddress.trim());
            assertEquals(c.getCity(), city);
            assertEquals(c.getState(), state);
            assertEquals(c.getZip(), zip);
        }
    }


    @DataProvider(name = "equals-data-provider")
    public Object[][] equalsDP() {
        return new Object[][]{
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, true},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        " Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, true},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", " Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, true},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", "Jones", 21, "1025 N. Broadway ", "Milwaukee", State.WISCONSIN, 53202, true},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee ", State.WISCONSIN, 53202, true},

                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bobby", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, false},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        " Bob", "Joneson", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, false},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", " Jones", 22, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202, false},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", "Jones", 21, "1025 S. Broadway ", "Milwaukee", State.WISCONSIN, 53202, false},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", "Jones", 21, "1025 N. Broadway", "South Milwaukee", State.WISCONSIN, 53202, false},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee ", State.WASHINGTON, 53202, false},
                {new Customer("Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee", State.WISCONSIN, 53202),
                        "Bob", "Jones", 21, "1025 N. Broadway", "Milwaukee ", State.WISCONSIN, 63202, false},
        };
    }

    @Test(dataProvider = "equals-data-provider", groups = {"provided"})
    public void testEquals(Customer c, String firstName, String lastName, int age, String streetAddress, String city, State state, int zip, boolean identical) {
        // Arrange
        Customer newc = new Customer(firstName, lastName, age, streetAddress, city, state, zip);
        newc.setID(c.getId());

        // Act
        boolean actualValue = newc.equals(c);

        assertEquals(actualValue, identical);
    }

    @Test(dataProvider = "equals-data-provider", groups = {"provided"})
    public void testHashCode(Customer c, String firstName, String lastName, int age, String streetAddress, String city, State state, int zip, boolean identical) {
        // Arrange
        Customer newc = new Customer(firstName, lastName, age, streetAddress, city, state, zip);
        // Force the id to be the same.
        newc.setID(c.getId());

        // Act
        int hashcode = newc.hashCode();

        if (identical) {
            assertEquals(hashcode, c.hashCode());
        } else {
            assertNotEquals(hashcode, c.hashCode());
        }
    }

    @Test(groups={"provided"})
    public void testToString()
    {
        // Arrange
        Customer c = new Customer("Bob", "Smith", 35, "12345 Main", "Lake Wobegon", State.MINNESOTA, 53210);

        // Act
        String s = c.toString();

        // Assert
        assertEquals(s, "1" + System.lineSeparator() + "Bob Smith" + System.lineSeparator() +  "12345 Main" + System.lineSeparator() + "Lake Wobegon, MN 53210");
    }
}