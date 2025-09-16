/*
 * Course: SWE2721-121
 * Spring 2025
 * Lab 5 - Inventory Lab
 * Name: Kaiden Pollesch
 * Created 2/25/2025
 */
package msoe.swe2721.lab4_5.provided;

import msoe.swe2721.lab4_5.provided.exceptions.DuplicateItemEntryException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidInventoryParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;

import java.util.HashMap;
import java.util.Map;

/**
 * Course: SWE2721-121
 * Spring 2025
 * Class InventoryManager Purpose: Manage the inventory
 *
 * @author polleschk
 * SWE2721-121 Laboratory Assignment
 * @version created on 2/25/2025 3:22 PM
 */
public class InventoryManager implements InventoryManagerInterface {
    private static final int MIN_DISC_LENGTH = 10;
    private static final int MAX_PRICE = 1000;
    private final Map<Integer, InventoryItem> stock = new HashMap<>();

    @Override
    public void addNewItemToStore(String name, String description, int sku, double price) throws DuplicateItemEntryException, InvalidInventoryParameterException, InvalidSKUException {
        this.addNewItemToStore(name, description, sku, price, 0);
    }

    @Override
    public void addNewItemToStore(String name, String description, int sku, double price, int initialStock) throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        if (stock.containsKey(sku)) {
            throw new DuplicateItemEntryException("SKU already exists");
        }
        if (sku <= 0) {
            throw new InvalidSKUException("Invalid SKU");
        }
        if (name == null || name.trim().length() < 2) {
            throw new InvalidInventoryParameterException("Invalid item name");
        }
        if (description == null || description.trim().length() < MIN_DISC_LENGTH) {
            throw new InvalidInventoryParameterException("Invalid item description");
        }
        if (price <= 0 || price > MAX_PRICE) {
            throw new InvalidInventoryParameterException("Invalid price");
        }
        if (initialStock < 0) {
            throw new InvalidInventoryParameterException("Invalid initial stock");
        }
        Money money = new Money(price);
        InventoryItem item = new InventoryItem(name, description, sku, initialStock, money);
        stock.put(sku, item);
    }

    @Override
    public boolean checkForSKUInSystem(int sku) throws InvalidSKUException {
        if (sku <= 0) {
            throw new InvalidSKUException("Invalid SKU");
        }
        return stock.containsKey(sku);
    }

    @Override
    public InventoryItem obtainItemBySKU(int sku) throws InvalidSKUException {
        if (sku <= 0) {
            throw new InvalidSKUException("Invalid SKU");
        }
        return stock.get(sku);
    }

    @Override
    public int addStock(int sku, int newStockReceived) throws InvalidSKUException, InvalidInventoryParameterException {
        if (sku <= 0) {
            throw new InvalidSKUException("Invalid SKU");
        }
        if (newStockReceived < 0) {
            throw new InvalidInventoryParameterException("Invalid stock count");
        }
        InventoryItem item = stock.get(sku);
        if (item == null) { // Bug fix 1: changed from if (item == null) to if (item != null)
            throw new InvalidSKUException("SKU not found");
        }
        item.addStock(newStockReceived);
        return item.getStockCount();
    }

    @Override
    public int returnStock(int sku, int returnedStockCount) throws InvalidSKUException, InvalidInventoryParameterException {
        if (sku <= 0) {
            throw new InvalidSKUException("Invalid SKU");
        }
        if (returnedStockCount < 0) {
            throw new InvalidInventoryParameterException("Invalid stock count");
        }
        InventoryItem item = stock.get(sku);
        if (item == null) { // Bug fix 2: changed from if (item == null) to if (item != null)
            throw new InvalidSKUException("SKU not found");
        }
        item.addStock(returnedStockCount);
        return item.getStockCount();
    }
}