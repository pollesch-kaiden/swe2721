package msoe.swe2721.lab4_5.provided;

import msoe.swe2721.lab4_5.provided.Customer;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;

/**
 * This interface defines the method that are necessary for a customer manager.  A class realizing this interface must have this behavior.
 * The realized class keeps track of the valid customers within the system.
 */
public interface CustomerManagerInterface {
    /**
     * This method will add a new customer to the system.  The customers name must be unique to be added into the system.
     * @param firstName This is the first name of the customer.  It must be at least 2 characters in length.  Leading and trailing white space will be removed.
     * @param lastName This is the last name of the customer.  It must be at least two characters in length.  Leading and trailing whitespace will be removed.
     * @param age           This is the age of the customer.  Because this system may sell alcohol, customers must be age 21 to be allowed in the system.
     * @param streetAddress This is the street address.  It must contain at least two words separated by at least one whitespace item, representing the house number and the street.
     * @param city          This is the city.  It must be at least three characters in length. @see <a href="https://finance.yahoo.com/news/shortest-city-name-every-state-171933616.html?guccounter=1&guce_referrer=aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbS8&guce_referrer_sig=AQAAAGVW_6OP2L8liu3nJheAwdIYH3ppog3EDgQccTn_1aR5Tj2iVdrsZHy7MGd31vgvPyBFrUFE3n2gYN15kJNoCpgOtQLhQVH5IBpPe78XDWUD1bIorUi1wPDSUOb81CEUuh5GW129hwLzd_yBeGn6wNBh-bja9HDwXqFDGL5uTo_W">...</a>
     * @param state The state must be exactly 2 characters in length and must be a standard USPS abbreviation.
     * @param zip The zipcode must be a numerically valid zip code.
     * @return The return will be the id for the given customer.
     * @throws InvalidCustomerParameterException This exception will be thrown if one of the customer parameters is invalid.  This includes an invalid first or last name, a customer that is too young to be in the system, an invalid street address, an invalid city, an invalid state, and invalid zipcode, or a zipcode that does not match the given state.
     * @throws DuplicateCustomerException This exception will be thrown if the customer already exists in the system and is not unique by first and last name.
     */
    int addNewCustomer(String firstName, String lastName, int age, String streetAddress, String city, String state, int zip) throws InvalidCustomerParameterException, DuplicateCustomerException;

    /**
     * This method will search the hashmap of customers to find the customer by name.
     * @param firstName This is the first name that is to be found.   Leading and trailing whitespace will be removed.
     * @param lastName This is the last name that is to be found.   Leading and trailing whitespace will be removed.
     * @return The return will be the matching customer or null if no match is found.
     */
    Customer findCustomerByName(String firstName, String lastName) throws InvalidCustomerParameterException;

    /**
     * This method will return the customer instance based upon ID.
     * @param id This is the ID for the customer.
     * @return The return will be the customer ID.
     * @throws InvalidCustomerParameterException This will be thrown if an invalid id is passed in as a parameter.
     */
    Customer findCustomerByID(int id) throws InvalidCustomerParameterException;

    /**
     * This method will count the number of customers in the system.
     * @return The number of customers that exist will be returned.
     */
    public int getCustomerCount();
}
