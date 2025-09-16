package msoe.swe2721.lab4_5.provided;

import java.security.InvalidParameterException;

/**
 * This class will keep track of an order for a single item.
 */
public class Order {
    /**
     * This is the customer for the given order.
     */
    private final Customer customer;
    /**
     * This is the inventory item.
     */
    private final InventoryItem item;
    /**
     * This is the count for the given order.
     */
    private final int count;

    /**
     * This is the only constructor.  It will create an order mapping the customer to a given quantify of items.
     * @param customer This is the customer.  Can not be null or an exception will be thrown.
     * @param item This is the item.  Can not be null or an exception will be thrown.
     * @param count This is the count.  Can not be negative or an exception will be thrown.
     * @throws InvalidParameterException This exception will be thrown if any parameter is invalid.
     */
    public Order(Customer customer, InventoryItem item, int count) throws InvalidParameterException {
        if (customer==null || item == null || count < 0)
        {
            throw new InvalidParameterException("Invalid order parameter.");
        }
        this.customer = customer;
        this.item = item;
        this.count = count;
    }

    /**
     * Obtain the customer.
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Obtain the item.
     * @return The item.
     */
    public InventoryItem getItem() {
        return item;
    }

    /**
     * Obtain the count.
     * @return The count.
     */
    public int getCount() {
        return count;
    }
}
