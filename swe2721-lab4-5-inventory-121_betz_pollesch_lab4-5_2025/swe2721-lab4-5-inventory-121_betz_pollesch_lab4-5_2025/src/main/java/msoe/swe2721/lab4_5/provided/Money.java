package msoe.swe2721.lab4_5.provided;

import java.security.InvalidParameterException;

/**
 * This class will kep track of money.  It stores dollars and cents for a monetary transaction.
 */
public class Money {
    /**
     * A number of dollars
     */
    private int dollars;

    /**
     * A number of cents
     */
    private int cents;

    /**
     * Constructor creates a Money object using the amount of money in dollars and cents represented with a decimal number
     *
     * @param amount The monetary amount.
     */
    public Money(double amount) throws InvalidParameterException {
        if (amount > Integer.MAX_VALUE || amount < Integer.MIN_VALUE) {
            throw new InvalidParameterException("Value can not be represented by class.");
        }

        long allCents = Math.round(amount * 100);
        dollars = (int) (allCents / 100);
        cents = (int) (allCents % 100);
    }

    /**
     * This will construct a given currency value with the given dollar and cents amounts.
     *
     * @param dollars This is the dollar amount.
     * @param cents This is the cent amount.
     * @throws InvalidParameterException This will be thrown if the cents is greater than or equal to 100 or less than or equal to -100.
     */
    public Money(int dollars, int cents) throws InvalidParameterException {
        if (cents >= 100 || cents <= -100) {
            throw new InvalidParameterException("The cents value is incorrect.");
        }
        this.dollars = dollars;
        this.cents = cents;
    }

    /**
     * This is effective a copy constructor, instantiating a new instance based upon the first instance.
     *
     * @param otherObject This is the object to be copied.
     */
    public Money(Money otherObject) {
        dollars = otherObject.dollars;
        cents = otherObject.cents;
    }

    public int getDollars() {
        return dollars;
    }

    public int getCents() {
        return cents;
    }

    public double getValue() {
        return dollars + (cents / 100.0);
    }

    /**
     * This will add a monetary amount to the currently existing money.
     *
     * @param otherAmount This is the amount that is to be added.
     */
    public void add(Money otherAmount) {
        this.cents += otherAmount.cents;
        this.dollars += otherAmount.dollars;

        if (this.cents >= 100) {
            this.cents -= 100;
            this.dollars++;
        }
    }

    /**
     * Subtracts the parameter Money object from the calling Money
     * object and returns the difference.
     *
     * @param otherAmount the amount of money to subtract
     */
    public void subtract(Money otherAmount) {
        if (this.getValue() > otherAmount.getValue()) {
            this.cents = this.cents - otherAmount.cents;
            this.dollars = this.dollars - otherAmount.dollars;
            if (this.cents < 0) {
                this.dollars--;
                this.cents = 100 + this.cents;
            }
        } else {
            this.dollars = this.dollars - otherAmount.dollars;
            this.cents = this.cents - otherAmount.cents;
            if (this.cents <= -100) {
                this.cents += 100;
                this.dollars--;
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return String.format("$%d.%2d", dollars, Math.abs(cents));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return 100 * this.dollars + this.cents;
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
}