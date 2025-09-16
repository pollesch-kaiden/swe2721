package msoe.swe2721.lab4_5.provided;

import java.security.InvalidParameterException;

/**
 * This class will keep track of the inventory for a given item in the store.
 */
public class InventoryItem {
    /**
     * This is the textual name of the given item.
     */
    private final String name;
    /**
     * This is the longer description of the item.
     */
    private final String description;

    /**
     * This is the SKU / item number for the item.  It is unique amongst all items.
     */
    private final int itemNumber;

    /**
     * This is the number of items that are in stock.  Must be positive.
     */
    private int stockCount;

    /**
     * This is the price for the given item.
     */
    private final Money price;


    /**
     * This method will instantiate a new inventory item.
     * @param name             This is the name of the item.  Must be at least 2 characters in length.
     * @param description      This is the description of the item.  Can be blank, but must not be null.
     * @param itemNumber       This is the item number for the item.  It should be unique, though this constructor does not verify that.  It must be positive and not zero.
     * @param initialInventory This is the initial inventory.  Must be positive.
     * @param price            This is the price.  Must be positive.
     * @throws InvalidParameterException This will be thrown if any parameter is invalid.
     */
    public InventoryItem(String name, String description, int itemNumber, int initialInventory, Money price) throws InvalidParameterException {
        if (name == null || name.length() < 2) {
            throw new InvalidParameterException("Item name is invalid.");
        }
        this.name = name;
        if (description == null) {
            throw new InvalidParameterException("Description is null");
        }
        this.description = description;
        if (itemNumber <= 0) {
            throw new InvalidParameterException("Invalid item number");
        }
        this.itemNumber = itemNumber;
        if (initialInventory < 0) {
            throw new InvalidParameterException(("Initial inventory is invalid."));
        }
        this.stockCount = initialInventory;
        if (price.getValue() < 0) {
            throw new InvalidParameterException("Price");
        }
        this.price = price;
    }

    /**
     * Obtain the name of the stock item.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtain the description for the given item.
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return the number for the given item.
     * @return The number for the given item will be returned.
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /**
     * The stock count for the given item will be returned.
     * @return The stock count.
     */
    public int getStockCount() {
        return stockCount;
    }

    /**
     * Return the price for the given item.
     * @return The price for the given item will be returned.
     */
    public Money getPrice() {
        return price;
    }

    /**
     * This method will add stock for the given item.
     * @param newStock This is the stock that is to be added.
     * @throws InvalidParameterException This exception will be thrown if the stock is negative.
     */
    public void addStock(int newStock) throws InvalidParameterException {
        if (newStock < 0) {
            throw new InvalidParameterException("New stock can not be negative.");
        } else {
            this.stockCount += newStock;
        }
    }

    /**
     * This method will sell stock in a given item.
     * @param count This is the number of items that are to be sold.
     * @return The return will be the count that were sold.  If for some reason the entire requested amount can not be fulfilled, this return will be less than the count requested.
     * @throws InvalidParameterException This exception will be thrown if the number of items ordered is negative.
     */
    public int sellStock(int count) throws InvalidParameterException {
        int retVal;
        if (count < 0) {
            throw new InvalidParameterException("Number to be ordered can not be negative.");
        } else if (count <= stockCount) {
            stockCount -= count;
            retVal = count;
        } else {
            retVal = stockCount;
            stockCount = 0;
        }
        return retVal;
    }
}
