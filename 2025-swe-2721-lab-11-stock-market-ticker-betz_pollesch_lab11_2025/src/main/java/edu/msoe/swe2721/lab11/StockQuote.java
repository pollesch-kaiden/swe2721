/**
 * (c) Copyright 2008, Dr. Walter W. Schilling, Jr.
 * ALL RIGHTS RESERVED 
 * Permission to use, copy, modify, and distribute this software for 
 * any purpose and without fee is hereby granted, provided that the above
 * copyright notice appear in all copies and that both the copyright notice
 * and this permission notice appear in supporting documentation, and that 
 * the name of Walter W. Schilling, Jr. not be used in advertising
 * or publicity pertaining to distribution of the software without specific,
 * written prior permission. 
 *
 * THE MATERIAL EMBODIED ON THIS SOFTWARE IS PROVIDED TO YOU "AS-IS"
 * AND WITHOUT WARRANTY OF ANY KIND, EXPRESS, IMPLIED OR OTHERWISE,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY OR
 * FITNESS FOR A PARTICULAR PURPOSE.  IN NO EVENT SHALL MSOE
 * BE LIABLE TO YOU OR ANYONE ELSE FOR ANY DIRECT,
 * SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES OF ANY
 * KIND, OR ANY DAMAGES WHATSOEVER, INCLUDING WITHOUT LIMITATION,
 * LOSS OF PROFIT, LOSS OF USE, SAVINGS OR REVENUE, OR THE CLAIMS OF
 * THIRD PARTIES, WHETHER OR NOT WALTER SCHILLING HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH LOSS, HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, ARISING OUT OF OR IN CONNECTION WITH THE
 * POSSESSION, USE OR PERFORMANCE OF THIS SOFTWARE. 
 * 
 * @version $Rev:: 3                       $:  Revision of last commit
 * @author  $Author:: schilling            $:  Author of last commit
 * $Date:: 2008-10-20 19:51:02 -0#$:  Date of last commit
 * $Log$:
 * 
 * This class contains information about the value of a single stock quote.
 * 
 */

package edu.msoe.swe2721.lab11;

/**
 * @author schilling
 * 
 */
public class StockQuote implements StockQuoteInterface {
	/**
	 * This is the stock symbol for the given stock.
	 */
	private final String symbol;
	/**
	 * This is the last trading price that has been reported b y the exchange.
	 */
	private double lastTradingPrice;
	/**
	 * This is the price of the stock the last time the market closed.
	 */
	private double previousClose;
	/**
	 * This is the change in value since the previous close.
	 */
	private double change;
	/**
	 * This is the timestamp for the given stock quote.
	 */
	private String timestamp;

	/**
	 * @param symbol
	 *            The symbol for the quote.
	 * @param previousClose
	 *            This is the closing value of the stock from the previous trading session.
	 * @param lastTradingPrice
	 *            The last trading value.
	 * @param change
	 *            This is the change in value for the given stock since the
	 *            previous close.
	 * @param timestamp This is the timestamp for the stock quote, i.e. when it was released by the stock exchange.
	 */
	public StockQuote(String symbol, double previousClose, double lastTradingPrice, double change, String timestamp) {
		super();
		this.symbol = symbol;
		this.lastTradingPrice = lastTradingPrice;
		this.previousClose = previousClose;
		this.change = change;
		this.timestamp = timestamp;
	}

	/**
	 * This method will return the timestamp for the given stock, which is when the quote was received.
	 * @return The timestamp string will be returned.
	 */
	public String getTimestamp()
	{
		return this.timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.msoe.se2831.lab6.StockQuoteInterface#getSymbol()
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * This will return the current price for the stock.
	 *
	 * @return the current Price
	 */
	public double getLastTradingPrice() {
		return lastTradingPrice;
	}

	/**
	 * This will return the previous close on the stock.
	 * This is the amount that the stock sold for at the close of the last trading session,
	 * typically the day before.
	 *
	 * @return the close
	 */
	public double getPreviousClose() {
		return previousClose;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.msoe.se2831.lab6.StockQuoteInterface#toString()
	 */
	public String toString() {

		String retVal = " (" + this.symbol + ")" + " Last Trade: " + this.lastTradingPrice + " Prev. Close: " + this.previousClose + "Timestamp: " + this.timestamp;
		return retVal;
	}

	/**
	 * This will return the changed value for the given stock versus the previous close.
	 *
	 * @return The return will be the absolute change in dollars and cents for the given stock.
	 * The value will be given in dollars and cents as a double, with the dollars being
	 * to the left of the decimal point and the cents being to the right.
	 */
	@Override
	public double getChange() {
		return this.change;
	}
}
