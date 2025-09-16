package edu.msoe.swe2721.lab14;

public class NumericParseException extends Exception {
    public NumericParseException() {
        super();
    }

    public NumericParseException(String message) {
        super(message);
    }

    public NumericParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NumericParseException(Throwable cause) {
        super(cause);
    }

    protected NumericParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
