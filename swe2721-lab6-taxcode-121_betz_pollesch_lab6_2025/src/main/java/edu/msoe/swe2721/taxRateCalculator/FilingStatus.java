package edu.msoe.swe2721.taxRateCalculator;

/**
 * This enumeration defines the Federal Tax Filing Statuses.
 */
public enum FilingStatus {
    /**
     * A single person filing a tax return.
     */
    SINGLE,
    /**
     * A head of household filing an income tax return.
     */
    HEAD_OF_HOUSEHOLD ,
    /**
     * Two married persons filing a return jointly.
     */
    MARRIED_FILING_JOINTLY,
    /**
     * Two married persons who are filing separately.
     */
    MARRIED_FILING_SEPARATELY
}
