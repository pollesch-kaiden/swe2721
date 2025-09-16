package msoe.swe2721.lab4_5.provided.exceptions;

/**
 * This is the base exception class for this sytem.  All exceptions used in this class are derived from here.
 */
public class InventorySystemBaseException extends Exception {
    public InventorySystemBaseException() {
        super();
    }

    public InventorySystemBaseException(String message) {
        super(message);
    }

    public InventorySystemBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventorySystemBaseException(Throwable cause) {
        super(cause);
    }

    protected InventorySystemBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
