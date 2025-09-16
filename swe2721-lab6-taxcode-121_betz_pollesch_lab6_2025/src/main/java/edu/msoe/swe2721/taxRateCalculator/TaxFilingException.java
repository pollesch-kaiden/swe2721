package edu.msoe.swe2721.taxRateCalculator;

public class TaxFilingException extends Exception {
    public TaxFilingException() {
        super();
    }

    public TaxFilingException(String message) {
        super(message);
    }

    public TaxFilingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaxFilingException(Throwable cause) {
        super(cause);
    }

    protected TaxFilingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
