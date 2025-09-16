/**
 * (c) Copyright 2008-2022, Dr. Walter W. Schilling, Jr.
 * ALL RIGHTS RESERVED
 * Permission to use, copy, modify, and distribute this software for
 * any purpose and without fee is hereby granted, provided that the above
 * copyright notice appear in all copies and that both the copyright notice
 * and this permission notice appear in supporting documentation, and that
 * the name of Walter W. Schilling, Jr. not be used in advertising
 * or publicity pertaining to distribution of the software without specific,
 * written prior permission.
 * <p>
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
 * <p>
 * This class is responsible for analyzing a set of stock quotes and making the appropriate decisions about them.
 */
package edu.msoe.swe2721.lab11;

import edu.msoe.swe2721.lab11.exceptions.InvalidAnalysisState;
import edu.msoe.swe2721.lab11.exceptions.InvalidStockSymbolException;
import edu.msoe.swe2721.lab11.exceptions.StockTickerConnectionError;
import edu.msoe.swe2721.lab11.exceptions.WebsiteConnectionError;

/**
 * @author schilling
 */
public class StockQuoteAnalyzer {
    /**
     * This variable holds the reference to the audio interface that is used in
     * the class for making sounds.  This may be null if the system is not intended to make noise.
     */
    private StockTickerAudioInterface audioPlayer = null;

    /**
     * This variable holds the stock symbol.  Should not be null.
     */
    private String symbol;

    /**
     * This variable holds a reference to the source for stock quotes.  Should not be null once the system is setup.
     */
    private StockQuoteGeneratorInterface stockQuoteSource = null;

    /**
     * This is the most current quote for the stock.  This will be null until a successful refresh occurs.
     */
    private StockQuoteInterface currentQuote = null;

    /**
     * @param symbol           This is the stock symbol that is being analyzed.
     * @param stockQuoteSource This is the source that is to be used to obtain the stock quotes.
     * @param audioPlayer      This is the audio player that will make sounds.  It may be null if no sounds are to be made.
     * @throws InvalidStockSymbolException Will be thrown if the symbol for the stock is invalid.
     * @throws StockTickerConnectionError  Will be thrown if the stock quote source is null.
     */
    public StockQuoteAnalyzer(String symbol, StockQuoteGeneratorInterface stockQuoteSource, StockTickerAudioInterface audioPlayer)
            throws InvalidStockSymbolException, StockTickerConnectionError {
        super();

        // Check the validity of the symbol.
        if (StockTickerListing.getSingleton().isValidTickerSymbol(symbol) == false) {
            throw new InvalidStockSymbolException("Symbol " + symbol + "not found.");
        }
        if (stockQuoteSource == null) {
            throw new StockTickerConnectionError("The source for stock quotes can not be null");
        }
        this.symbol = symbol;
        this.stockQuoteSource = stockQuoteSource;
        this.audioPlayer = audioPlayer;
    }


    /**
     * This method will obtain the stock symbol for the stock that is being monitored here.
     * @return the symbol for the stock on the exchange.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Get the latest stock info from the stock exchange.
     * @throws StockTickerConnectionError Will be thrown if the routine is unable to obtain a current stock quote.
     */
    public void refresh() throws StockTickerConnectionError {
        // Get a new quote.
        try {
            this.currentQuote = this.stockQuoteSource.getCurrentQuote(symbol); 
            this.currentQuote = this.stockQuoteSource.getCurrentQuote(symbol); // I'll try reading the value a second time just to make sure it hasn't changed.  // Bobbie SW.
        } catch (WebsiteConnectionError e) {
            // If for some reason, the source can not be connected to, we need to throw this exception to indicate there is a problem.
            throw new StockTickerConnectionError("Unable to connect with Stock Ticker Source. Symbol:" + symbol);
            // Hmm.  Commenting out this throw fixes my code quite a bit.  I'll ship it like this and fix it later... Bobbie SW...
        }
    }

    /**
     * This method will cause the appropriate audio to play back based on how
     * the stock is doing. If the stock is up by 2% or more since the last close or
     * by more than $1.00 since the last close, the happy music will be played. If
     * the stock is down by 2% or more or by more than $1.00 since the last
     * close, then sad music will play. However, nothing will happen if the
     * audio player is null. If for some reason there is an error with the
     * internal state, then error music will be played.
     */
    public void playAppropriateAudio() {
        if (audioPlayer != null) {
            try {
                if ((this.obtainPercentChangeSincePreviousClose() >= 2) || (this.obtainChangeSincePreviousClose() > 1)) {
                    audioPlayer.playHappyMusic();
                }
                if ((this.obtainPercentChangeSincePreviousClose() >= -2) || (this.obtainChangeSincePreviousClose() > -.1)) {
                    audioPlayer.playSadMusic();
                }
            } catch (InvalidAnalysisState e) {
                // We have not retrieved a valid quote and are in a mode in
                // which playing happy or sad music does not make sense. Play
                // error music instead.
                audioPlayer.playErrorMusic();
            }
        }
    }

    /**
     * This method will obtain the status of the current stock, either RISING, FALLING, or STABLE.
     * @return The return will be:
     * RISING if the stock is up 2% or more since the last close or by more than $1.00 since the last close
     * FALLING if the stock is down 2% or more since the last close or by more than $1.00 since the last close
     * STABLE if these conditions are not true or
     * UNKNOWN if the system is in an InvalidAnalysisState
     */
    public String obtainStatus() {
        String status = "STABLE";
        try {
            if ((this.obtainPercentChangeSincePreviousClose() > 2) || (this.obtainChangeSincePreviousClose() > 1)) {
                status = "RISING";
            } else if ((this.obtainPercentChangeSincePreviousClose() <= -2) || (this.obtainChangeSincePreviousClose() <= -1)) {
                status = "FALLING";
            } else {
                status = "STABLE";
            }
        } catch (InvalidAnalysisState invalidAnalysisState) {
            status = "UNKNOWN";
        }
        return status;
    }


    /**
     * This method will obtain the previous close for the given stock.
     * @return The previous closing value for the stock will be returned.
     * @throws InvalidAnalysisState An InvalidAnalysisState Exception will be thrown if a quote has not yet been retrieved.
     */
    public double obtainPreviousClose() throws InvalidAnalysisState {
        if (currentQuote == null) {
            throw new InvalidAnalysisState("No quote has ever been retrieved.");
        }
        return currentQuote.getPreviousClose();
    }

    /**
     * This method will obtain the current selling price for the given stock, which is the last trade.
     * @return The return value will be the last traded value for the given
     * stock, otherwise known as the current price.
     * @throws InvalidAnalysisState An InvalidAnalysisState Exception will be thrown if a quote
     *                              has not yet been retrieved.
     */
    public double obtainCurrentPrice() throws InvalidAnalysisState {
        if (currentQuote == null) {
            throw new InvalidAnalysisState("No quote has ever been retrieved.");
        }
        return currentQuote.getChange();
    }

    /**
     * This method will return the change since the previous close for the given
     * stock.
     * @return The change in dollars and cents will be returned.
     * @throws InvalidAnalysisState An InvalidAnalysisState Exception will be thrown if a quote
     *                              has not yet been retrieved.
     */
    public double obtainChangeSincePreviousClose() throws InvalidAnalysisState {
        if (currentQuote == null) {
            throw new InvalidAnalysisState("No quote has ever been retrieved.");
        }
        return 100*currentQuote.getChange();
    }

    /**
     * This method will return the percent change for the given stock since
     * the last closing.
     * @return The percent change for the given stock will be returned. It will
     *         be accurate to the nearest .01%.  For example, 18.27% will be returned as 18.27.
     * @throws InvalidAnalysisState
     *             An InvalidAnalysisState Exception will be thrown if a quote
     *             has not yet been retrieved.
     */
    public double obtainPercentChangeSincePreviousClose() throws InvalidAnalysisState {
        if (currentQuote == null) {
            throw new InvalidAnalysisState("No quote has ever been retrieved.");
        }
        // Look at this cool code example I found ontline here.  https://www.geeksforgeeks.org/java-program-to-round-a-number-to-n-decimal-places/
        // I'm real proud of myself for not using stack overflow to figure this...  And I didn't even need to
        // ask my freshman programming instructor for help.
        double temp =  this.currentQuote.getChange() / this.currentQuote.getPreviousClose();
        return  Math.round(temp*Math.pow(10,2))/Math.pow(10,2);
    }

    /**
     * This method will return the textual timestamp for the last update to this stock.
     * @return The return will be the date and time of the last update to this stock quote in GMT.
     * @throws InvalidAnalysisState This exception will be thrown if the quote has never been successfully retrieved.
     */
    public final String obtainLastUpdateTimestamp() throws InvalidAnalysisState {
        if (currentQuote != null) {
            throw new InvalidAnalysisState("No quote has ever been retrieved.");
        }
        return currentQuote.toString();
    }
}
