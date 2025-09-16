package msoe.swe2721.lab4_5.provided;

import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;
import msoe.swe2721.lab4_5.provided.exceptions.InventoryOutOfStockException;

import java.security.InvalidParameterException;

/**
 * This interface will defint the methods necessary for an order manager.
 */
public interface OrderManagerInterface {
    /**
     * This method will return the count of all orders that have been placed.
     *
     * @return The count of all orders that have been placed will be returned.
     */
    int obtainOrderCount();

    /**
     * This will obtain the order from the system.
     *
     * @param orderSequence This is the sequence number for the order.  The first order that is placed is 1.
     * @return The order will be returned.
     * @throws InvalidParameterException This will be thrown if the order sequence number is too large for the given system.
     */
    Order obtainOrder(int orderSequence) throws InvalidParameterException;

    /**
     * This method will order an item for a given customer.  The order will be returned, and it will also be placed on the master list within this class of orders that have been created.
     *
     * @param customerID This is the id of the customer.
     * @param sku        This is the sku of the item that is to be ordered.
     * @param count      This is the number of items that are to be ordered.
     * @return The order object will be returned.  The order will consist of the items that have been ordered - if there is enough in stock - or the number that can be supplied if there are not enough in stock to fulfill the entire order.
     * @throws InvalidCustomerParameterException This will be thrown if the customer is not in the system or the ID is invalid.
     * @throws InventoryOutOfStockException      This exception will be thrown if there is no stock for the given item.
     * @throws InvalidSKUException               This will be thrown if the SKU itself is invalid or is not in the system.
     */
    Order OrderItem(int customerID, int sku, int count) throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException;

    /**
     * This method will order an item for a given customer.  The order will be returned, and it will also be placed on the master list within this class of orders that have been created.
     *
     * @param firstName This is the first name of the customer.
     * @param lastName  This is the last name for the given customer.
     * @param sku       This is the sku of the item that is to be ordered.
     * @param count     This is the number of items that are to be ordered.
     * @return The order object will be returned.  The order will consist of the items that have been ordered - if there is enough in stock - or the number that can be supplied if there are not enough in stock to fulfill the entire order.
     * @throws InvalidCustomerParameterException This will be thrown if the customer is not in the system or the ID is invalid.
     * @throws InventoryOutOfStockException      This exception will be thrown if there is no stock for the given item.
     * @throws InvalidSKUException               This will be thrown if the SKU itself is invalid or is not in the system.
     */
    Order OrderItem(String firstName, String lastName, int sku, int count) throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException;
}
