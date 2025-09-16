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
    private static LinkedList<Order> allOrders = new LinkedList<>();
    private static CustomerManagerInterface cmi;
    private static InventoryManagerInterface imi;

    public OrderManager(CustomerManagerInterface customerManagerInterface, InventoryManagerInterface inventoryManagerInterface) {
        if (customerManagerInterface == null || inventoryManagerInterface == null) {
            throw new InvalidParameterException("CustomerManagerInterface and InventoryManagerInterface cannot be null");
        }
        cmi = customerManagerInterface; // Set customer manager interface
        imi = inventoryManagerInterface; // Set inventory manager interface
    }

    @Override
    public int obtainOrderCount() {
        return allOrders.size(); // Return size of all orders
    }

    @Override
    public Order obtainOrder(int orderSequence) throws InvalidParameterException {
        // Check for invalid order sequence
        if (orderSequence < 0 || orderSequence >= allOrders.size()) {
            throw new InvalidParameterException();
        }
        return allOrders.get(orderSequence); // Return order at index of order sequence
    }

    @Override
    public Order OrderItem(int customerID, int sku, int count) throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException {
        Customer customer = cmi.findCustomerByID(customerID); // Find customer by id
        // Check that customer is valid
        if (customer == null) {
            throw new InvalidCustomerParameterException();
        }
        InventoryItem item = imi.obtainItemBySKU(sku); // Find item by sku
        // Check that sku is valid
        if (item == null) {
            throw new InvalidSKUException();
        }
        // Check that the inventory is in stock
        if (count > item.getStockCount()) {
            throw new InventoryOutOfStockException();
        }
        Order order = new Order(customer, item, count);
        allOrders.add(order);
        return order;
    }

    @Override
    public Order OrderItem(String firstName, String lastName, int sku, int count) throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException {
        Customer customer = cmi.findCustomerByName(firstName, lastName); // Find customer by name
        // Check that customer is valid
        if (customer == null) {
            throw new InvalidCustomerParameterException();
        }
        InventoryItem item = imi.obtainItemBySKU(sku); // Find item by sku
        // Check that sku is valid
        if (item == null) {
            throw new InvalidSKUException();
        }
        // Check that the inventory is in stock
        if (count > item.getStockCount()) {
            throw new InventoryOutOfStockException();
        }
        Order order = new Order(customer, item, count);
        allOrders.add(order);
        return order;
    }
}