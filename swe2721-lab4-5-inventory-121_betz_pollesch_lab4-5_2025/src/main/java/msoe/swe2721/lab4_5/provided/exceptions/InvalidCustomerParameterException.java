package msoe.swe2721.lab4_5.provided.exceptions;
/**
 * This class indicates that there is an exception related to the inventory system that has occurred.
 */

public class InvalidCustomerParameterException extends InventorySystemBaseException {
    public InvalidCustomerParameterException() {
        super();
    }

    public InvalidCustomerParameterException(String message) {
        super(message);
    }

    public InvalidCustomerParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCustomerParameterException(Throwable cause) {
        super(cause);
    }

    protected InvalidCustomerParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
