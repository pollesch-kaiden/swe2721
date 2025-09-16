package msoe.swe2721.lab4_5.provided.exceptions;

/**
 * This exception indicates that an item is trying to be added to the system when the given item already exists.
 */
public class DuplicateItemEntryException extends InventorySystemBaseException {
    public DuplicateItemEntryException() {
        super();
    }

    public DuplicateItemEntryException(String message) {
        super(message);
    }

    public DuplicateItemEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateItemEntryException(Throwable cause) {
        super(cause);
    }

    protected DuplicateItemEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
