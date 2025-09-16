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
 * This class implements a full market analyzer.  Multiple symbols can be watched, and the output is printed to the screen as necessary.
 * 
 */

package edu.msoe.swe2721.lab11;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import edu.msoe.swe2721.lab11.exceptions.InvalidAnalysisState;
import edu.msoe.swe2721.lab11.exceptions.StockTickerConnectionError;

/**
 * @author schilling
 * 
 */
public class MarketAnalyzer implements Runnable {

	/**
	 * This parameter sets how often the stock data is refreshed by accessing
	 * it.
	 */
	private int refreshRate = 60;

	/**
	 * This is the number of times that the loop is to execute, querying for
	 * data. It allows the user to set the number of thread executions to a
	 * given count. -1 indicates it should run until the thread is killed.
	 */
	private int runCount = -1;

	/**
	 * This is a listing of the symbols which are to be tracked.
	 */
	private ArrayList<StockQuoteAnalyzer> symbolsToTrack = new ArrayList<StockQuoteAnalyzer>();

	/**
	 * This class represents the audioPlayer which is to be used to play error
	 * messages, happy messages, or other sounds based on the behavior of the
	 * market and the system.
	 */
	private StockTickerAudioInterface audioPlayer;

	/**
	 * This is the output stream to which the data is to be written.
	 */
	private PrintStream out = System.out;

	/**
	 * Display an error message and play the appropriate error sound(s).
	 * 
	 * @param text
	 *            This is the text of the error message that is to be printed.
	 */
	public void showErrorMessage(String text) {
		audioPlayer.playErrorMusic();
		out.println("Error: " + text);
	}

	/**
	 * @param symbols
	 *            This is an array of symbols that are to be watched by the
	 *            stock ticker.
	 * @param wi
	 *            This is the stock quote generator that is to be used.
	 * @param audioPlayer
	 *            This is the audio player that is to be used to generate sounds
	 *            when things happen.
	 */
	public MarketAnalyzer(String[] symbols, StockQuoteGeneratorInterface wi, StockTickerAudioInterface audioPlayer) {
		this.audioPlayer = audioPlayer;
		for (int index = 0; index < symbols.length; index++) {
			try {
				StockQuoteAnalyzer analyzer = new StockQuoteAnalyzer(symbols[index], wi.createNewInstance(symbols[index]), audioPlayer);
				this.symbolsToTrack.add(analyzer);
			} catch (Exception e) {
				this.showErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 * Update the data based on the current market conditions. This will obtain
	 * the most current data and print it out to the screen / console /
	 * printstream.
	 */
	public void updateData() {
		Iterator<StockQuoteAnalyzer> iter = this.symbolsToTrack.iterator();

		out.println(new Date().toString());
		out.println(
				"##############################################################################################################################################################");
		out.printf("%48s", "Stock Name and Symbol ");
		out.printf("\t%10s", "Last Close ");
		out.printf("\t%10s", "Cur. Price ");
		out.printf("\t%10s", "Change ");
		out.printf("\t%10s", "% Change ");
		out.printf("\t%10s", "Status ");
		out.printf("\t%s", "Timestamp ");
		out.println();

		while (iter.hasNext() == true) {
			StockQuoteAnalyzer instance = iter.next();
			try {
				instance.refresh();
				String symbol = instance.getSymbol();
				String stockName = StockTickerListing.getSingleton().getCompanyName(symbol);

				out.printf("%40s", stockName);
				out.printf(" (%5s) ", symbol);
				out.printf("\t$%7.2f", instance.obtainPreviousClose());
				out.printf("\t$%7.2f", instance.obtainCurrentPrice());
				out.printf("\t$%7.2f", instance.obtainChangeSincePreviousClose());
				out.printf("\t%7.2f%%", instance.obtainPercentChangeSincePreviousClose());
				out.printf("\t%10s", instance.obtainStatus());
				out.printf("\t%s", instance.obtainLastUpdateTimestamp());
				instance.playAppropriateAudio();
			} catch (StockTickerConnectionError e) {
				this.showErrorMessage(e.getMessage());
			} catch (InvalidAnalysisState e) {
				this.showErrorMessage(e.getMessage());
			} finally {
				out.println();
			}
		}
		out.println(
				"##############################################################################################################################################################");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		synchronized(this) {
			while ((runCount == -1) || (runCount > 0)) {
				try {
					Thread.sleep(1000 * this.refreshRate);
				} catch (InterruptedException e) {
					System.out.println("Interrupted Exception caught");
				}
				this.updateData();
				if (runCount != -1) {
					runCount--;
				}
			}
			this.notifyAll();
		}
	}

	/**
	 * Return the rate of refreshing of data (in seconds)
	 * 
	 * @return the refreshRate
	 */
	public int getRefreshRate() {
		return refreshRate;
	}

	/**
	 * Set the refresh rate to occur every x seconds.
	 * 
	 * @param refreshRate
	 *            the refreshRate to set
	 */
	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}

	/**
	 * @return the audioPlayer
	 */
	public StockTickerAudioInterface getAudioPlayer() {
		return audioPlayer;
	}

	/**
	 * @param audioPlayer
	 *            the audioPlayer to set
	 */
	public void setAudioPlayer(StockTickerAudioInterface audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	/**
	 * Return the output stream into which text is written.
	 * 
	 * @return the out
	 */
	public PrintStream getOut() {
		return out;
	}

	/**
	 * Set the output stream to the appropriate value.
	 * 
	 * @param out
	 *            the out to set
	 */
	public void setOut(PrintStream out) {
		this.out = out;
	}

	/**
	 * @return the runCount
	 */
	public int getRunCount() {
		return runCount;
	}

	/**
	 * @param runCount
	 *            the runCount to set
	 */
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
}
