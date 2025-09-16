/*
 * Course: SWE2721-121
 * Spring 2025
 * Stock Market Ticker
 * Name: Kaiden Pollesch
 * Created 4/21/2025
 */
package edu.msoe.swe2721.lab11;

import edu.msoe.swe2721.lab11.exceptions.InvalidStockSymbolException;
import edu.msoe.swe2721.lab11.exceptions.StockTickerConnectionError;
import edu.msoe.swe2721.lab11.exceptions.WebsiteConnectionError;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
/**
 * Course: SWE2721-121
 * Spring 2025
 * Class StockQuoteAnalyzerTest Purpose: Test StockQuoteAnalyzer
 *
 * @author polleschk
 * SWE2721-1package edu.msoe.swe2721.lab11;
 * @version created on 4/21/2025 4:26 PM
 */

 public class StockQuoteAnalyzerTest {

     @Mock
     private StockQuoteGeneratorInterface sqg;
     @Mock
     private StockTickerAudioInterface stAudio;

     private StockQuoteAnalyzer stockQuoteAnalyzer;

     @BeforeMethod(alwaysRun = true)
     public void setUp() {
         sqg = Mockito.mock(StockQuoteGeneratorInterface.class);
         stAudio = Mockito.mock(StockTickerAudioInterface.class);
     }

     @Test(expectedExceptions = InvalidStockSymbolException.class,groups = {"all, student"})
     public void testValidConstructor() throws InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
         // Act
         stockQuoteAnalyzer = new StockQuoteAnalyzer("F", sqg, stAudio);

         // Assert
         assertNotNull(stockQuoteAnalyzer, "The StockQuoteAnalyzer instance should not be null.");
         verify(sqg, never()).getCurrentQuote("F");
         verify(sqg, never()).createNewInstance("F");
     }

     @Test(expectedExceptions = InvalidStockSymbolException.class,groups = {"all, student"})
     public void testConstructorWithNullAudio() throws InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
         // Act
         stockQuoteAnalyzer = new StockQuoteAnalyzer("F", sqg, null);

         // Assert
         assertNotNull(stockQuoteAnalyzer, "The StockQuoteAnalyzer instance should not be null.");
         verify(sqg, never()).getCurrentQuote("F");
         verify(sqg, never()).createNewInstance("F");
     }

     @Test(expectedExceptions = InvalidStockSymbolException.class,groups = {"all, student"})
     public void testInvalidSymbolConstructor() {
         // Act and Assert
         assertThrows(InvalidStockSymbolException.class, () -> new StockQuoteAnalyzer("INVALID", sqg, stAudio));
     }

     @Test(expectedExceptions = InvalidStockSymbolException.class,groups = {"all, student"})
     public void testNullStockQuoteGenerator() {
         // Act and Assert
         assertThrows(StockTickerConnectionError.class, () -> new StockQuoteAnalyzer("F", null, stAudio));
     }

    @Test(groups = {"student", "all"})
    public void testobtainStatusNoQuotes() throws Exception {
        // Setup the expected calls.
        stockQuoteAnalyzer = new StockQuoteAnalyzer("F", sqg, stAudio);

        // Now check that the change calculation was correct.
        // Act and Assert
        assertEquals(stockQuoteAnalyzer.obtainStatus(), "UNKNOWN");
    }
 }