package msoe.swe2721.lab4_5.provided.exceptions;

/**
 * This exception class represents an exception caused by a duplicate customer being present in a given system.
 */
public class DuplicateCustomerException extends InventorySystemBaseException {
    public DuplicateCustomerException() {
        super();
    }

    public DuplicateCustomerException(String message) {
        super(message);
    }

    public DuplicateCustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCustomerException(Throwable cause) {
        super(cause);
    }

    protected DuplicateCustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
