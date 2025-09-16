/**
 * (c) Copyright 2008-2020, Dr. Walter W. Schilling, Jr.
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
 * This class will obtain a stock quote from FCS by interpreting a JSON interface and manipulating it into a standard format.
 */

package edu.msoe.swe2721.lab11;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import edu.msoe.swe2721.lab11.exceptions.WebsiteConnectionError;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;

/**
 * This class will handle downloading stocks from the FCSAPI source.
 */
public class FCSAPIStockDownloader implements StockQuoteGeneratorInterface, Runnable {
    /**
     * This variable will be true so long as the thread is to continue running.
     */
    private boolean keepGoing = true;

    /**
     * This is the refresh rate, or how often this will go out to the service to get a new update.
     */
    private int refreshRate = 60;

    /**
     * This is a hashmap of all of the symbols as they are kept here.
     */
    private HashMap<String, StockQuoteInterface> recentQuotes = new HashMap<String, StockQuoteInterface>();

    /**
     * This is the API key to access the stock source.
     */
    private String apiKey;

    /**
     * This method will set the API key that is to be used to access the service.
     * @param apiKey This is the textual API key.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * This method will set the refresh rate for the given stock quote.
     * @param refreshRate This si the refresh rate in seconds.
     */
    public void setRefreshRate(int refreshRate)
    {
        this.refreshRate = refreshRate;
    }


    /*
     * (non-Javadoc)
     *
     * @see msoe.StockQuoteGeneratorInterface#getCurrentQuote()
     */
    @Override
    public StockQuoteInterface getCurrentQuote(String symbol) throws WebsiteConnectionError {
        StockQuoteInterface response = recentQuotes.get(symbol);

        if (response == null) {
            throw new WebsiteConnectionError("Unable to obtain quote for " + symbol);
        }
        return response;
    }

    /**
     * Default constructor.
     */
    public FCSAPIStockDownloader() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see msoe.StockQuoteGeneratorInterface#createNewInstance()
     */
    @Override
    public StockQuoteGeneratorInterface createNewInstance(String symbol) {
		/**
		 * We are adding a new symbol.  Add the symbol, but leave the recent quote null until it is actually retrieved.
		 */
		this.recentQuotes.put(symbol, null);
        return this;
    }

    /**
     * This method will cause the executing thread to asynchronously stop executing and exit.
     */
    public void stopExecution()
    {
        this.keepGoing=false;
    }


    /**
     * @see Runnable#run()
     */
    @Override
    public void run() {
        while (keepGoing) {
            // Generate a string with the stock symbols in them.
            String symbols = "";
            for (String s : this.recentQuotes.keySet()) {
                if (symbols.length() > 0) {
                    symbols += "," + s;
                } else {
                    symbols = s;
                }
            }

            // Obtain the stock quotes from the source.
            JSONParser parser = new JSONParser();
            String quoteURL = "https://fcsapi.com/api-v3/stock/latest?symbol=" + symbols + "&access_key=" + apiKey;

            try {
            	// Get a URL for the stock source.
                URL url = new URL(quoteURL);

                // Read from that stream.
                InputStream inputStream = url.openStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // Parse the read stream.
                Object obj = parser.parse(bufferedReader);
                JSONObject jsonObject = (JSONObject) obj;

                // Now grab what we want.
                JSONArray responses = (JSONArray) ((JSONObject) obj).get("response");

                // Iterate over the stock quotes.
                for (Object mobj : responses) {
                    // Lets get the quotes.
                    JSONObject jsonQuote = (JSONObject) mobj;

                    // We only care about US quotes.
                    if (((String) jsonQuote.get("cty")).equalsIgnoreCase("united-states")) {
                        String symbol = (String) jsonQuote.get("s");
                        double currentPrice = Double.parseDouble((String) jsonQuote.get("c"));
                        double change = Double.parseDouble((String) jsonQuote.get("ch"));
                        double prevClose = currentPrice - change;
                        String timestamp = (String) jsonQuote.get("tm");
                        recentQuotes.put(symbol, new StockQuote(symbol, prevClose, currentPrice, change, timestamp));
                    }
                }
            } catch (ParseException ex) {
                System.err.println(ex);
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
            } catch (MalformedURLException ex) {
                System.err.println(ex);
            } catch (IOException ex) {
                System.err.println(ex);
            }

            // Wait for a period of time and try it over again.
            try {
                Thread.sleep(1000 * this.refreshRate);
            } catch (InterruptedException e) {
                // Just ignore this exception.  It is likely because the program is intending on exiting.
            }
        }
    }
}
