/*
 * Course: SWE2721-121
 * Spring 2025
 * Lab 5 - Inventory Lab
 * Name: Madison Betz
 * Created 2/25/2025
 */
package msoe.swe2721.lab4_5.provided;

import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;

import java.util.HashMap;
import java.util.Objects;

/**
 * Course: SWE2721-121
 * Spring 2025
 * Class CustomerManager Purpose: Manage the customers
 *
 * @author betzm
 * SWE2721-121 Laboratory Assignment
 * @version created on 2/25/2025 3:21 PM
 */
public class CustomerManager implements CustomerManagerInterface {
    private static final HashMap<Integer, Customer> customers = new HashMap<>();
    private int id = 0;

    @Override
    public int addNewCustomer(String firstName, String lastName, int age, String streetAddress, String city, String state, int zip) throws InvalidCustomerParameterException, DuplicateCustomerException {
        // Check for valid zipcode
        if (!ZipCodeValidator.getSingleton().isValidZipcode(zip)) {
            throw new InvalidCustomerParameterException();
        }
        State stateName = State.valueOfName(state); // Make state string into state object
        id++; // Increment customer id
        // Create and add new customer
        Customer newCustomer = new Customer(firstName, lastName,
                age, streetAddress, city, stateName, zip);
        // Check for duplicate customer by matching first and last name
        for (Customer customer : customers.values()) {
            if (Objects.equals(customer.getFirstName(), firstName)
                    && Objects.equals(customer.getLastName(), lastName)) {
                throw new DuplicateCustomerException();
            }
        }
        customers.put(id, newCustomer); // Add new customer
        return id; // Return new customer id
    }

    @Override
    public Customer findCustomerByName(String firstName, String lastName) {
        Customer foundCustomer = null;
        // Looks through customers to find the matching first name and last name
        for (Customer customer : customers.values()) {
            if (Objects.equals(customer.getFirstName(), firstName)
                    && Objects.equals(customer.getLastName(), lastName)) {
                // Set the matching customer equal to found customer
                foundCustomer = customer;
            }
        }
        return foundCustomer;
    }

    @Override
    public Customer findCustomerByID(int id) throws InvalidCustomerParameterException {
        // Checks for valid id
        if (id <= 0) {
            throw new InvalidCustomerParameterException();
        }
        // Loop through customers to find the matching id
        for (Customer customer : customers.values()) {
            if (customer.getId() == id) {
                // Returns found customer
                return customer;
            }
        }
        throw new InvalidCustomerParameterException(); // If no matching id is found, throw exception;
    }

    @Override
    public int getCustomerCount() {
        return customers.size(); // Return the number of customers stored
    }

    /**
     * Clear the customer list.
     */
    public void clearCustomers() {
        customers.clear();
    }
}