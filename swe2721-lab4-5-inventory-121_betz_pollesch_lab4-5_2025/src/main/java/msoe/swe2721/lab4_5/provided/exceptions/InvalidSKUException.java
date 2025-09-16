package msoe.swe2721.lab4_5.provided.exceptions;

/**
 * This exception will be thrown if there is an invalid SKU parameter.
 */
public class InvalidSKUException extends InventorySystemBaseException {
    public InvalidSKUException() {
        super();
    }

    public InvalidSKUException(String message) {
        super(message);
    }

    public InvalidSKUException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSKUException(Throwable cause) {
        super(cause);
    }

    protected InvalidSKUException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
