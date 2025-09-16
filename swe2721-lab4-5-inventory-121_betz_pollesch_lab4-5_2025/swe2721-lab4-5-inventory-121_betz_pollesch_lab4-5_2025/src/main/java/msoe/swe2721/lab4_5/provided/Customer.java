package msoe.swe2721.lab4_5.provided;

import java.security.InvalidParameterException;

/**
 * This class represents a customer for the given distribution system.
 */
public class Customer {
    /**
     * This is the serial number for the last instantiated customer.  It will be incremented each time a new Customer appears.
     */
    private static int serialNumber = 0;

    /**
     * This method will return the serial number, which is the numer for the last instantiated object.
     * @return
     */
    public static int getSerialNumber()
    {
        return serialNumber;
    }
    /**
     * This is the unique ID for this customer.
     */
    private int id;

    /**
     * This is the first name for the customer.  Only text and internal spaces are permitted.  No leading or trailing spaces are allowed in the string.
     */
    private final String firstName;

    /**
     * This is the last name for the customer.  Only text and internal spaces are permitted.  No leading or trailing spaces are allowed in the string.
     */
    private final String lastName;

    /**
     * This is the age of the customer.
     */
    private final int age;

    /**
     * This is the street address for the customer.  Must include a number and street name at a minimum.
     */
    private final String streetAddress;
    /**
     * This is the city for the customer.  Only text is stored.  White space is removed from leading and trailing positions.
     */
    private final String city;

    /**
     * This is the state.
     */
    private final State state;
    /**
     * This is the zip code for the given customer.
     */
    private final int zip;

    /**
     * This method exists for testing purposes to reset the serial number.  This is not to be used in production code.
     */
    static void resetSerialNumber()
    {
        serialNumber = 0;
    }

    /**
     * This method exists for testing.  It will allow the id to be reset.
     * @param newID The ID that is to be set.
     */
    void setID(int newID)
    {
        this.id=newID;
    }


    /**
     * This method will instantiate a new instance of a customer.
     *
     * @param firstName     This is the first name of the customer.  It must be at least 2 characters in length.  Leading and trailing white space will be removed.
     * @param lastName      This is the last name of the customer.  It must be at least two characters in length.  Leading and trailing whitespace will be removed.
     * @param age           This is the age of the customer.  It must be a number greater than or equal to zero.
     * @param streetAddress This si the street address.  It must contain at least two words separated by at least one whitespace item, representing the house number and the street.
     * @param city          This is the city.  It must be at least two characters in length.  Whitespace will be trimmed.
     * @param state         This si the state for the address.
     * @param zip           This is the zip code.  It must be a value greater than 00501 (the first valid zip code) to 99950 (the last valid zip code) How about 12345, a unique ZIP Code for General Electric in Schenectady, NY. @See <a href="https://facts.usps.com/42000-zip-codes/">...</a>
     * @throws InvalidParameterException This exception will be thrown if any parameter is invalid.
     */
    public Customer(String firstName, String lastName, int age, String streetAddress, String city, State state, int zip) throws InvalidParameterException {
        super();
        this.firstName = firstName.trim();
        if (this.firstName.length() < 2) {
            throw new InvalidParameterException("First name is too short");
        }

        this.lastName = lastName.trim();
        if (this.lastName.length() < 2) {
            throw new InvalidParameterException("Last name is too short");
        }

        this.age = age;
        if (this.age < 0) {
            throw new InvalidParameterException("Invalid age entered.");
        }
        this.streetAddress = streetAddress.trim();
        if (!this.streetAddress.matches("\\d+\\s+([0-9a-zA-Z\\p{Punct}]+|[0-9a-zA-Z\\p{Punct}]+\\s[0-9a-zA-Z\\p{Punct}]+)+")) {
            throw new InvalidParameterException("Invalid street address");
        }


        this.city = city.trim();
        if (this.city.length() < 2) {
            throw new InvalidParameterException("City is invalid");
        }

        this.state = state;

        this.zip = zip;
        if (this.zip < 501 || this.zip > 99950) {
            throw new InvalidParameterException("The zip code is not valid.");
        }
        // Assign the ID to the instance.
        id = ++serialNumber;
    }

    /**
     * Obtain the customer's unique ID.
     * @return The customer's unique id.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtain the customer's first name.
     * @return the customer's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Obtain the customer's last name.
     * @return The customer's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Obtain the customer's age.
     * @return The customer's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Obtain the customer's street address.
     * @return The customer's street address.
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Obtain the customer's city.
     * @return the customer's city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Obtain the customer's state.
     * @return the state.
     */
    public State getState() {
        return state;
    }

    /**
     * Obtain the customer's zip.
     * @return the customer's zipcode.
     */
    public int getZip() {
        return zip;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id *
                firstName.hashCode() *
                lastName.hashCode() *
                age *
                streetAddress.hashCode() *
                city.hashCode() *
                state.hashCode() *
                zip;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else return this.hashCode() == obj.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(System.lineSeparator());
        sb.append(firstName);
        sb.append(" ");
        sb.append(lastName);
        sb.append(System.lineSeparator());
        sb.append(streetAddress);
        sb.append(System.lineSeparator());
        sb.append(city);
        sb.append(", ");
        sb.append(state.getAbbreviation());
        sb.append(" ");
        sb.append(zip);
        return sb.toString();
    }
}
