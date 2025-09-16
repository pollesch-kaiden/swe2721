package edu.msoe.swe2721.lab14;

public class TriangleConstructionException extends Exception {
    public TriangleConstructionException() {
        super();
    }

    public TriangleConstructionException(String message) {
        super(message);
    }

    public TriangleConstructionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TriangleConstructionException(Throwable cause) {
        super(cause);
    }

    protected TriangleConstructionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
