/*
 * Course: SWE2721-121
 * Spring 2025
 * Lab 5 - Inventory Lab
 * Name: Madison Betz
 * Created 2/25/2025
 */
package msoe.swe2721.lab4_5.provided;

import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;
import msoe.swe2721.lab4_5.provided.exceptions.InventoryOutOfStockException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Course: SWE2721-121
 * Spring 2025
 * Class OrderManager Purpose: Manage the orders to subtract from the inventory
 *
 * @author betzm
 * SWE2721-121 Laboratory Assignment
 * @version created on 2/25/2025 3:23 PM
 */
public class OrderManager implements OrderManagerInterface {
    private static CustomerManagerInterface cmi;
    private static InventoryManagerInterface imi;
    private HashMap<Integer, Order> orders;
    private int orderSequence;    


    /**
     * Constructor for OrderManager
     * @param customerManagerInterface This is the customer manager.
     * @param inventoryManagerInterface This is the inventory manager.
     */
    public OrderManager(CustomerManagerInterface customerManagerInterface, InventoryManagerInterface inventoryManagerInterface) {
        if (customerManagerInterface == null || inventoryManagerInterface == null) {
            throw new InvalidParameterException("CustomerManagerInterface and InventoryManagerInterface cannot be null");
        }
        cmi = customerManagerInterface; // Set customer manager interface
        imi = inventoryManagerInterface; // Set inventory manager interface
        this.orders = new HashMap<>();
        this.orderSequence = 1;
    }

    /**
     * Order an item for a customer.
     * @param customerId This is the id of the customer.
     * @param sku        This is the sku of the item that is to be ordered.
     * @param quantity      This is the number of items that are to be ordered.
     * @return The order that was placed.
     * @throws InventoryOutOfStockException If the item is out of stock.
     * @throws InvalidSKUException If the SKU is invalid.
     * @throws InvalidCustomerParameterException If the customer is invalid.
     */
    public Order OrderItem(int customerId, int sku, int quantity) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {
        Customer customer = cmi.findCustomerByID(customerId);
        if (customer == null) {
            throw new InvalidCustomerParameterException("Customer not found.");
        }
        InventoryItem item = imi.obtainItemBySKU(sku);
        if (item == null || item.getStockCount() < quantity) {
            throw new InventoryOutOfStockException("Item out of stock.");
        }

        item.sellStock(quantity);
        Order order = new Order(customer, item, quantity);
        orders.put(orderSequence, order);
        orderSequence++;
        return order;
    }

    /**
     * Order an item for a customer.
     * @param firstName This is the first name of the customer.
     * @param lastName  This is the last name for the given customer.
     * @param sku       This is the sku of the item that is to be ordered.
     * @param quantity     This is the number of items that are to be ordered.
     * @return The order that was placed.
     * @throws InventoryOutOfStockException If the item is out of stock.
     * @throws InvalidSKUException If the SKU is invalid.
     * @throws InvalidCustomerParameterException If the customer is invalid.
     */
    public Order OrderItem(String firstName, String lastName, int sku, int quantity) throws InventoryOutOfStockException, InvalidSKUException, InvalidCustomerParameterException {
        Customer customer = cmi.findCustomerByName(firstName, lastName);
        if (customer == null) {
            throw new InvalidCustomerParameterException("Customer not found.");
        }
        return OrderItem(customer.getId(), sku, quantity);
    }

    /**
     * Get the number of orders that have been placed.
     * @return The number of orders that have been placed.
     */
    public int obtainOrderCount() {
        return orders.size();
    }

    /**
     * Get an order by the order number.
     * @param orderNumber This is the order number.
     * @return The order that was found.
     * @throws InvalidParameterException If the order number is invalid.
     */
    public Order obtainOrder(int orderNumber) throws InvalidParameterException {
        if (!orders.containsKey(orderNumber)) {
            throw new InvalidParameterException("Order not found.");
        }
        return orders.get(orderNumber);
    }
}