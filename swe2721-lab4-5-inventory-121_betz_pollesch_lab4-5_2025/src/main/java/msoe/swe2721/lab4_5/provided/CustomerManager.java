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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final Map<String, State> stateMap = new HashMap<>();
    private static final List<Integer> validZipCodes = Arrays.asList(53202, 44095); // Add valid zip codes here
    private HashMap<Integer, Customer> customers;

    static {
        for (State s : State.values()) {
            stateMap.put(s.name().substring(0, 2), s);
        }
    }

    /**
     * Constructor for CustomerManager
     */
    public CustomerManager() {
        this.customers = new HashMap<>();
    }

    /**
     * add a new customer to the system
     * @param firstName This is the first name of the customer.  It must be at least 2 characters in length.  Leading and trailing white space will be removed.
     * @param lastName This is the last name of the customer.  It must be at least two characters in length.  Leading and trailing whitespace will be removed.
     * @param age           This is the age of the customer.  Because this system may sell alcohol, customers must be age 21 to be allowed in the system.
     * @param streetAddress This is the street address.  It must contain at least two words separated by at least one whitespace item, representing the house number and the street.
     * @param city          This is the city.  It must be at least three characters in length. @see <a href="https://finance.yahoo.com/news/shortest-city-name-every-state-171933616.html?guccounter=1&guce_referrer=aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbS8&guce_referrer_sig=AQAAAGVW_6OP2L8liu3nJheAwdIYH3ppog3EDgQccTn_1aR5Tj2iVdrsZHy7MGd31vgvPyBFrUFE3n2gYN15kJNoCpgOtQLhQVH5IBpPe78XDWUD1bIorUi1wPDSUOb81CEUuh5GW129hwLzd_yBeGn6wNBh-bja9HDwXqFDGL5uTo_W">...</a>
     * @param stateStr The state must be exactly 2 characters in length and must be a standard USPS abbreviation.
     * @param zip The zipcode must be a numerically valid zip code.
     * @return The ID of the customer that was added.
     * @throws DuplicateCustomerException If the customer already exists.
     * @throws InvalidCustomerParameterException If the customer parameters are invalid
     */
    public int addNewCustomer(String firstName, String lastName, int age, String streetAddress, String city, String stateStr, int zip) throws DuplicateCustomerException, InvalidCustomerParameterException {
        if (firstName == null || lastName == null || firstName.length() < 2 || lastName.length() < 2 || age < 21 || streetAddress.length() < 5 || city.length() < 2 || stateStr.length() != 2) {
            throw new InvalidCustomerParameterException("Invalid customer details provided.");
        }
        if (!validZipCodes.contains(zip)) {
            throw new InvalidCustomerParameterException("Invalid zip code.");
        }

        if (!isCityStateMatch(city, stateStr)) {
            throw new InvalidCustomerParameterException("City and state do not match.");
        }


        State state = stateMap.get(stateStr.toUpperCase());
        if (state == null) {
            throw new InvalidCustomerParameterException("Invalid state abbreviation.");
        }

        for (Customer customer : customers.values()) {
            if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName) && customer.getStreetAddress().equals(streetAddress) && customer.getCity().equals(city) && customer.getState().equals(state) && customer.getZip() == zip) {
                throw new DuplicateCustomerException("Customer already exists.");
            }
        }
        Customer newCustomer = new Customer(firstName, lastName, age, streetAddress, city, state, zip);
        if (customers.containsKey(newCustomer.getId())) {
            throw new DuplicateCustomerException("Customer already exists.");
        }

        customers.put(newCustomer.getId(), newCustomer);
        return newCustomer.getId();
    }

    private boolean isCityStateMatch(String city, String state) {
        Map<String, String> validCityStatePairs = new HashMap<>();
        validCityStatePairs.put("Milwaukee", "WI");
        validCityStatePairs.put("Willoughby Hills", "OH");
        validCityStatePairs.put("Willoughby", "OH");

        return state.equals(validCityStatePairs.get(city));
    }
    /**
     * find a customer by using their first and last name
     * @param firstName This is the first name that is to be found.   Leading and trailing whitespace will be removed.
     * @param lastName This is the last name that is to be found.   Leading and trailing whitespace will be removed.
     * @return The customer that was found or null if the customer was not found.
     * @throws InvalidCustomerParameterException If the customer name is invalid.
     */
    public Customer findCustomerByName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            for (Customer customer : customers.values()) {
                if (customer.getFirstName().equalsIgnoreCase(firstName.trim()) && customer.getLastName().equalsIgnoreCase(lastName.trim())) {
                    return customer;
                }
            }
        }
        return null;
    }

    /**
     * find a customer by using their ID
     * @param id This is the ID for the customer.
     * @return The customer that was found or null if the customer was not found.
     * @throws InvalidCustomerParameterException If the customer ID is invalid.
     */
    public Customer findCustomerByID(int id) throws InvalidCustomerParameterException {
        if (id < 0) {
            throw new InvalidCustomerParameterException("Invalid customer ID.");
        }
        return customers.get(id);
    }

    public int getCustomerCount() {
        return customers.size();
    }
}