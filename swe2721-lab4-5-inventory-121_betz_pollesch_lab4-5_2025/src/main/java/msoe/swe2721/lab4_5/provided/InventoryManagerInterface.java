package msoe.swe2721.lab4_5.provided;

import msoe.swe2721.lab4_5.provided.InventoryItem;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateItemEntryException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidInventoryParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;

import java.security.InvalidParameterException;

public interface InventoryManagerInterface {
    /**
     * This method will add a new item to the store.  The item must have a unique SKU and all other parameters must also be valid.
     *
     * @param name        This is the name of them item.  It must be at least two characters in length after trimming any leading and trailing spaces.
     * @param description This is the description of the item.  This store requires this to be at least 10 characters in length, though no changes are made.
     * @param sku         This is the sku for the item.  It should be unique amongst all sku's and must be a number greater than 0.
     * @param price       This is the price of the item.  It must be positive and less than or equal to $1000 for this store to list.
     * @throws InvalidInventoryParameterException This exception will be thrown if any parameter is out of range.
     * @throws DuplicateItemEntryException                 This exception will be thrown if the SKU already exists within the system.
     * @throws InvalidSKUException                This exception will be thrown is the SKU itself is not a valid SKU.
     */
    void addNewItemToStore(String name, String description, int sku, double price) throws DuplicateItemEntryException, InvalidInventoryParameterException, InvalidSKUException;

    /**
     * This method will add a new item to the store.  The item must have a unique SKU and all other parameters must also be valid.
     *
     * @param name        This is the name of them item.  It must be at least two characters in length after trimming any leading and trailing spaces.
     * @param description This is the description of the item.  This store requires this to be at least 10 characters in length, though no changes are made.
     * @param sku         This is the sku for the item.  It should be unique amongst all sku's and must be a positive number.
     * @param price       This is the price of the item.  It must be positive and less than or equal to $1000 for this store to list.
     * @param initialStock This is the initial stock.  It must be positive.
     * @throws InvalidInventoryParameterException This exception will be thrown if any parameter is out of range.
     * @throws DuplicateItemEntryException                 This exception will be thrown if the SKU already exists within the system.
     * @throws InvalidSKUException                This exception will be thrown is the KSU itself is not a valid SKU.
     */
    void addNewItemToStore(String name, String description, int sku, double price, int initialStock) throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException;

    /**
     * This method will determine if a SKU is present in the system.
     * @param sku This is the sku number.  Must be positive.
     * @return true if the sku is in the system.  False if not.
     * @throws InvalidSKUException will be thrown if the sku is not a valid sku.
     */
    boolean checkForSKUInSystem(int sku) throws InvalidSKUException;

    /**
     * This method will obtain the inventory item associated with a given sku.
     * @param sku This is the inventory sku.  It must be positive.
     * @return The return will be the inventory item or null if the item does not exist.
     * @throws InvalidSKUException will be thrown if the sku is not a valid sku.
     */
    InventoryItem obtainItemBySKU(int sku) throws InvalidSKUException;

    /**
     * This method will add new stock into the inventory.
     * @param sku This is the sku that is to have inventory added.  Must be positive.
     * @param newStockReceived This is the count of the new stock that has been received.  Must be positive.
     * @return The return will be the total stock of the items after the stock has been added.
     * @throws InvalidSKUException This will be thrown if the SKU is invalid.
     * @throws InvalidParameterException  This will be thrown if the new stock received is invalid (i.e. negative)
     */
    int addStock(int sku, int newStockReceived) throws InvalidSKUException, InvalidInventoryParameterException;

    /**
     * This method will return stock that has been sold back to inventory when it is returned.
     * @param sku This is the sku that is to have inventory returned.  Must be positive.
     * @param returnedStockCount This is the count of the new stock that has been received.  Must be positive.
     * @return The return will be the total stock of the items after the stock has been returned.
     * @throws InvalidSKUException This will be thrown if the SKU is invalid.
     * @throws InvalidParameterException  This will be thrown if the new stock received is invalid (i.e. negative)
     */
    int returnStock(int sku, int returnedStockCount) throws InvalidSKUException, InvalidInventoryParameterException;
}
