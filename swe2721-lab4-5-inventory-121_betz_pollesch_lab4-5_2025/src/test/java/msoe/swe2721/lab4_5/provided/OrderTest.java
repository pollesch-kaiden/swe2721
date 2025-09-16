package msoe.swe2721.lab4_5.provided;

import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class OrderTest {

    /**
     * This is the data provider which will verify whether the constructor is working properly.
     * @return An array of <customer, inventoryitem, count, true/false if exception is expected>
     */
    @DataProvider(name = "constructorDataProvider")
    public Object[][] constructorDP() throws InvalidCustomerParameterException {
        Customer c = new Customer("Bob", "Smith", 25, "125 N. Main", "Ada", State.OHIO, 45810);
        InventoryItem i = new InventoryItem("Ruby Slippers", "These slippers are replicas of those worn by Dorthy in the Wizard of Oz.", 59592, 1, new Money(999.99));
        return new Object[][]{
                {c, i, 1, false},
                {null, i, 1, true},
                {c, null, 1, true},
                {c, i, -1, true}
        };
    }

    /**
     * This will verify the constructor.
     * @param customer This si the customer instance.,
     * @param item This is the item.
     * @param count This is the count.
     * @param exceptionExpected true if the constructor is expected to throw an exception.  False OTW.
     */
    @Test(dataProvider = "constructorDataProvider", groups = {"provided"})
    public void testConstructor(Customer customer, InventoryItem item, int count, boolean exceptionExpected) {
        // Arrange
        Order o = null;

        // Act
        if (exceptionExpected)
        {
            assertThrows(InvalidParameterException.class, ()->new Order(customer, item, count));
        }
        else {
            o = new Order(customer, item, count);
            assertEquals(o.getCustomer(), customer);
            assertEquals(o.getItem(), item);
            assertEquals(o.getCount(), count);
        }
    }

}
