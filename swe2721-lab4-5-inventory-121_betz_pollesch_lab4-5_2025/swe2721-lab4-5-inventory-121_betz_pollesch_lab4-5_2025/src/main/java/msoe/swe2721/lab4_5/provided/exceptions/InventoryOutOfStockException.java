package msoe.swe2721.lab4_5.provided.exceptions;

/**
 * This exception will be thrown if there is an out of stock condition, meaning that a request is being made for an item which is not currently in stock.
 */
public class InventoryOutOfStockException
        extends InventorySystemBaseException {
    public InventoryOutOfStockException() {
        super();
    }

    public InventoryOutOfStockException(String message) {
        super(message);
    }

    public InventoryOutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryOutOfStockException(Throwable cause) {
        super(cause);
    }

    protected InventoryOutOfStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
