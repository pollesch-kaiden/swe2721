package msoe.swe2721.lab4_5.provided.exceptions;

/**
 * This exception will be thrown if there is a problem with a paremeter passed into the inventory system.
 */
public class InvalidInventoryParameterException extends InventorySystemBaseException {
    public InvalidInventoryParameterException() {
        super();
    }

    public InvalidInventoryParameterException(String message) {
        super(message);
    }

    public InvalidInventoryParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInventoryParameterException(Throwable cause) {
        super(cause);
    }

    protected InvalidInventoryParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
