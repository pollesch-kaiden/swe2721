package msoe.swe2721.lab4_5.provided;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class InventoryItemTest {

    /**
     * This is the data provider which will verify whether the constructor is working properly.
     * @return An array of <item name, description, itemnumber, inventory, price, exceptionexpected>
     */
    @DataProvider(name = "constructorDataProvider")
    public Object[][] constructorDP() {
        return new Object[][]{
                {"Allen Wrench", "A type of wrench named after allen used in Ikea sets", 1001, 1, new Money(1.99), false},
                {null, "A type of wrench named after allen used in Ikea sets", 1001, 1, new Money(1.99), true},
                {"", "A type of wrench named after allen used in Ikea sets", 1001, 1, new Money(1.99), true},
                {"Allen Wrench", null, 1001, 1, new Money(1.99), true},
                {"Allen Wrench", "A type of wrench named after allen used in Ikea sets", -1, 1, new Money(1.99), true},
                {"Allen Wrench", "A type of wrench named after allen used in Ikea sets", 1001, -1, new Money(1.99), true},
                {"Allen Wrench", "A type of wrench named after allen used in Ikea sets", 1001, 1, new Money(-1.00), true},
        };
    }

    @Test(dataProvider = "constructorDataProvider", groups = {"provided"})
    public void testConstructor(String name, String description, int itemNumber, int initialInventory, Money price, boolean exceptionExpected) throws InvalidParameterException {

        // Arrange
        Order o = null;

        // Act
        if (exceptionExpected)
        {
            assertThrows(InvalidParameterException.class, ()->new InventoryItem(name, description, itemNumber, initialInventory, price));
        }
        else {
            InventoryItem item = new InventoryItem(name, description, itemNumber, initialInventory, price);
            assertEquals(item.getName(), name);
            assertEquals(item.getDescription(), description);
            assertEquals(item.getItemNumber(), itemNumber);
            assertEquals(item.getStockCount(), initialInventory);
            assertEquals(item.getPrice(), price);;
        }
    }

    /**
     * This test will veryify that tthe appropriate exception is thrown when adding stock./
     */
    @Test(groups = {"provided"})
    public void testAddStockExceptionThrowing() {
        // Arrange
        InventoryItem i = new InventoryItem("Fidget Bits", "Things to fidget with", 12345, 1, new Money(1.99));

        // Act
        assertThrows(InvalidParameterException.class, ()->i.addStock(-1));

        // Assert
    }

    /**
     * This method will verify the behavior of nthe add stock method.
     */
    @Test( groups = {"provided"})
    public void testAddStock() {
        // Arrange
        InventoryItem i = new InventoryItem("Fidget Bits", "Things to fidget with", 12345, 1, new Money(1.99));

        // Act
        i.addStock(10);

        // Assert
        assertEquals(i.getStockCount(), 11);
    }

    /**
     * This method will verify the behavior of sell stock method when it throws an exception.
     */
    @Test( groups = {"provided"})

    public void testSellStockExceptionThrowing() {
        // Arrange
        InventoryItem i = new InventoryItem("Fidget Bits", "Things to fidget with", 12345, 1, new Money(1.99));

        // Act
        assertThrows(InvalidParameterException.class, ()->i.sellStock(-1));

        // Assert
    }


    /**
     * This method will verify the behavior of sell stock method when it operates normally and there is plenty of stock.
     */
    @Test( groups = {"provided"})
    public void testSellStock1() {
        // Arrange
        InventoryItem i = new InventoryItem("Fidget Bits", "Things to fidget with", 12345, 10, new Money(1.99));

        // Act
        int soldStock = i.sellStock(5);

        // Assert
        assertEquals(i.getStockCount(), 5);
        assertEquals(soldStock, 5);
    }

    /**
     * This method will verify the behavior of sell stock method when it operates normally and there is exactly the right amount of stock.
     */
    @Test( groups = {"provided"})

    public void testSellStock2() {
        // Arrange
        InventoryItem i = new InventoryItem("Fidget Bits", "Things to fidget with", 12345, 10, new Money(1.99));

        // Act
        int soldStock = i.sellStock(10);

        // Assert
        assertEquals(i.getStockCount(), 0);
        assertEquals(soldStock, 10);
    }

    /**
     * This method will verify the behavior of sell stock method when it operates normally and there is not as much stock as is requested.
     */
    @Test( groups = {"provided"})

    public void testSellStock3() {
        // Arrange
        InventoryItem i = new InventoryItem("Fidget Bits", "Things to fidget with", 12345, 10, new Money(1.99));

        // Act
        int soldStock = i.sellStock(11);

        // Assert
        assertEquals(i.getStockCount(), 0);
        assertEquals(soldStock, 10);
    }
}
