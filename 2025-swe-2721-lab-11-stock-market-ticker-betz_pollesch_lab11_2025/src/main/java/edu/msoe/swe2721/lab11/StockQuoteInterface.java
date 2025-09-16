package edu.msoe.swe2721.lab11;

/**
 * This interface will return pertinent information about a given stock quote.
 * 
 * @author schilling
 *
 */
public interface StockQuoteInterface {

	/**
	 * This will return the stock symbol for the given quote.
	 * 
	 * @return the symbol
	 */
	public abstract String getSymbol();

	/**
	 * This will return the last trading price for the stock.
	 * If the market is open, this is the price that the stock is currently selling for.
	 * If the market is closed, it is the price from the last sale of the stock.
	 * 
	 * @return the lastTrade.
	 */
	public abstract double getLastTradingPrice();

	/**
	 * This will return the previous close on the stock.
	 * This is the amount that the stock sold for at the close of the last trading session,
	 * typically the day before.
	 * 
	 * @return the close
	 */
	public abstract double getPreviousClose();

	/**
	 * This will return the changed value for the given stock versus the previous close.
	 * 
	 * @return The return will be the absolute change in dollars and cents for the given stock.
	 * The value will be given in dollars and cents as a double, with the dollars being
	 * to the left of the decimal point and the cents being to the right.
	 */
	public abstract double getChange();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();

	/**
	 * This method will return the timestamp for the given stock, which is when the quote was received.
	 * @return This is the timestamp of the quote as a string.
	 */
	public abstract String getTimestamp();

}