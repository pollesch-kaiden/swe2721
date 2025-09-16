package edu.msoe.swe2721.lab11;

import edu.msoe.swe2721.lab11.exceptions.InvalidAnalysisState;
import edu.msoe.swe2721.lab11.exceptions.InvalidStockSymbolException;
import edu.msoe.swe2721.lab11.exceptions.StockTickerConnectionError;
import edu.msoe.swe2721.lab11.exceptions.WebsiteConnectionError;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class MockitoTestNGTestProfessor {
	@Mock
	private StockQuoteGeneratorInterface sqg;
	@Mock
	private StockTickerAudioInterface sti;

	private StockQuoteAnalyzer classUnderTest;

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {

		sqg = Mockito.mock(StockQuoteGeneratorInterface.class);
		sti = mock(StockTickerAudioInterface.class);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		
		sqg = null;
		sti = null;

	}
	
	@Test(expectedExceptions = InvalidStockSymbolException.class, groups = {"professor"})
	public void testInvalidStockSymbol() throws NullPointerException, InvalidStockSymbolException, StockTickerConnectionError
	{
		classUnderTest = new StockQuoteAnalyzer("ZZZZZZZZZ", sqg, sti);
	}
	
	@Test(expectedExceptions = StockTickerConnectionError.class, groups = {"professor"})
	public void testNullSource() throws NullPointerException, InvalidStockSymbolException, StockTickerConnectionError
	{
		classUnderTest = new StockQuoteAnalyzer("DIS", null, sti);
	}

	@Test(expectedExceptions = StockTickerConnectionError.class, groups = {"professor"})
	public void testRefreshConnectionError() throws StockTickerConnectionError, NullPointerException, InvalidStockSymbolException, Exception
	{
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenThrow(new WebsiteConnectionError());

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.refresh();
	}
	
	@Test(expectedExceptions = InvalidAnalysisState.class, groups = {"professor"})
	public void testGetPreviousOpenInvalidAnalysisState() throws InvalidAnalysisState, NullPointerException, InvalidStockSymbolException, StockTickerConnectionError
	{
		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.obtainPreviousClose();
	}

	@Test(expectedExceptions = InvalidAnalysisState.class, groups = {"professor"})
	public void testObtainLastUpdateTimestampInvalidAnalysisState() throws InvalidAnalysisState, NullPointerException, InvalidStockSymbolException, StockTickerConnectionError
	{
		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.obtainLastUpdateTimestamp();
	}

	@Test(expectedExceptions = InvalidAnalysisState.class, groups = {"professor"})
	public void testGetCurrentPriceInvalidAnalysisState() throws InvalidAnalysisState, NullPointerException, InvalidStockSymbolException, StockTickerConnectionError
	{
		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.obtainCurrentPrice();
	}
	
	@Test(expectedExceptions = InvalidAnalysisState.class, groups = {"professor"})
	public void testGetChangeSinceOpenInvalidAnalysisState() throws InvalidAnalysisState, NullPointerException, InvalidStockSymbolException, StockTickerConnectionError
	{
		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.obtainChangeSincePreviousClose();
	}
	
	@Test(expectedExceptions = InvalidAnalysisState.class, groups = {"professor"})
	public void testGetPercentChangeSinceOpenInvalidAnalysisState() throws InvalidAnalysisState, NullPointerException, InvalidStockSymbolException, StockTickerConnectionError
	{
		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.obtainPercentChangeSincePreviousClose();
	}
	
	@Test(groups = {"professor"})
	public void testplayAppropriateAudioNoValidUpdates() throws Exception
	{
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(null);

		// Instantiate the class to test.

		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		// Check that the class is instantiated properly.
		assertEquals(classUnderTest.getSymbol(), "F");
		classUnderTest.playAppropriateAudio();


		// verify that method was never called on a mock
		verify(sti, times(1)).playErrorMusic();
		verify(sti, times(0)).playHappyMusic();
		verify(sti, times(0)).playSadMusic();
	}

	@DataProvider
	public Object[][] normalOperationDataProvider() {
		return new Object[][] {
				{ new StockQuote("F", 10.00, 10.00, 0.00, "A"), 0, 0, 0.0 ,"STABLE"}, // No change.
				{ new StockQuote("F", 10.00, 10.19, .19, "B"), 0, 0, 1.9,"STABLE" }, // 1.9% increase
				{  new StockQuote("F", 10.00, 10.20, .20, "C"), 1, 0, 2.0, "RISING" }, // 2.0% increase
				{  new StockQuote("F", 10.00, 10.21, .21, "D"), 1, 0, 2.1, "RISING" }, // 2.1% increase
				{  new StockQuote("F", 10.00, 11.00, 1.00, "E"), 1, 0, 10.0, "RISING" }, // 10.0% increase

				// Now check raw dollar changes.
				{ new StockQuote("F", 1000.00, 1000.00, 0.00, "F"), 0, 0, 0.0,"STABLE" }, // No change.
				{ new StockQuote("F", 1000.00, 1000.99,    .99, "G"), 0, 0, 0.099,"STABLE" }, // $.99 increase
				{  new StockQuote("F", 1000.00, 1001.00,   1.0, "H"), 0, 0, 0.100,"STABLE" }, // $1.00 increase
				{  new StockQuote("F", 1000.00, 1001.01,   1.01, "I"), 1, 0, 0.101, "RISING" }, // $1.01 increase

				{  new StockQuote("F", 10.00, 10.00, 0.00, "J"), 0, 0, 0.0,"STABLE" }, // No change.
				{  new StockQuote("F", 10.00, 9.81, -.19, "K"), 0, 0, -1.9,"STABLE" }, // -1.99 decrease
				{  new StockQuote("F", 10.00, 9.80, -.20, "L"), 0, 1, -2.0,"FALLING" }, // 2.0% decrease
				{  new StockQuote("F", 10.00, 9.79, -.21, "M"), 0, 1, -2.1,"FALLING"}, // 2.01% decrease
				{  new StockQuote("F", 10.00, 9.00, -1.0, "N"), 0, 1, -10.00,"FALLING" }, // 10.0% decrease

				// Now check raw dollar changes.
				{ new StockQuote("F", 1000.00, 1000.00, 0.00, "O"), 0, 0, 0.0,"STABLE" }, // No change.
				{ new StockQuote("F", 1000.00, 999.01,    -.99, "P"), 0, 0, -0.099,"STABLE" }, // $.99 increase
				{  new StockQuote("F", 1000.00, 999,   -1.0, "Q"), 0, 0, -0.100,"STABLE" }, // $1.00 increase
				{  new StockQuote("F", 1000.00, 998.99,   -1.01, "R"), 0, 1, -0.101,"FALLING" }, // $1.01 increase
		};
	}

	@Test(dataProvider = "normalOperationDataProvider", groups = {"professor"})
	public void testNoAudioPlayback(StockQuote firstReturn, int happyMusicCount, int sadMusicCount, double percentChange, String status) throws Exception {
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(firstReturn);

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, null);
		// Check that the class is instantiated properly.
		assertEquals(classUnderTest.getSymbol(), "F");
		classUnderTest.refresh();
		classUnderTest.playAppropriateAudio();

		// Now verify the methods were called or not called appropriately
		// default call count is 1
		// check if add function is called three times
		verify(sqg, times(1)).getCurrentQuote("F");

		// verify that method was never called on a mock
		verify(sti, never()).playErrorMusic();
		verify(sti, never()).playHappyMusic();
		verify(sti, never()).playSadMusic();

		// Now check that the change calculation was correct.
		assertEquals(classUnderTest.obtainPercentChangeSincePreviousClose(), percentChange, 0.01);
	}


	@Test(dataProvider = "normalOperationDataProvider", groups = {"professor"})
	public void testNormalOperation(StockQuote firstReturn, int happyMusicCount, int sadMusicCount, double percentChange, String status) throws Exception {
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(firstReturn);

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		// Check that the class is instantiated properly.
		assertEquals(classUnderTest.getSymbol(), "F");
		classUnderTest.refresh();
		classUnderTest.playAppropriateAudio();

		// Now verify the methods were called or not called appropriately
		// default call count is 1
		// check if add function is called three times
		verify(sqg, times(1)).getCurrentQuote("F");

		// verify that method was never called on a mock
		verify(sti, never()).playErrorMusic();
		verify(sti, times(happyMusicCount)).playHappyMusic();
		verify(sti, times(sadMusicCount)).playSadMusic();

		// Now check that the change calculation was correct.
		assertEquals(classUnderTest.obtainPercentChangeSincePreviousClose(), percentChange, 0.01);
	}

	@Test(dataProvider = "normalOperationDataProvider", groups = {"professor"})
	public void testobtainStatus(StockQuote firstReturn, int happyMusicCount, int sadMusicCount, double percentChange, String status) throws Exception {
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(firstReturn);

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		// Check that the class is instantiated properly.
		assertEquals(classUnderTest.getSymbol(), "F");
		classUnderTest.refresh();

		// Now verify the methods were called or not called appropriately
		// default call count is 1
		// check if add function is called three times
		verify(sqg, times(1)).getCurrentQuote("F");


		// Now check that the change calculation was correct.
		assertEquals(classUnderTest.obtainStatus(), status);
	}

	@Test(groups = {"professor"})
	public void testobtainStatusNoQuotes() throws Exception {
		// Setup the expected calls.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);

		// Now check that the change calculation was correct.
		// Act and Assert
		assertEquals(classUnderTest.obtainStatus(), "UNKNOWN");
	}


	@Test(dataProvider = "normalOperationDataProvider", groups = {"professor"})
	public void testgetChangeSinceClose(StockQuote firstReturn, int happyMusicCount, int sadMusicCount,	double percentChange, String status) throws Exception {
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(firstReturn);

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.refresh();

		// Now check that the change calculation was correct.
		assertEquals(classUnderTest.obtainChangeSincePreviousClose(), firstReturn.getChange(), 0.01);
	}
	
	@Test(dataProvider = "normalOperationDataProvider", groups = {"professor"})
	public void testgetCurrentPrice(StockQuote firstReturn, int happyMusicCount, int sadMusicCount, double percentChange, String status) throws Exception {
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(firstReturn);

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.refresh();

		// Now check that the change calculation was correct.
		assertEquals(classUnderTest.obtainCurrentPrice(), firstReturn.getLastTradingPrice(), 0.01);
	}

	@Test(dataProvider = "normalOperationDataProvider", groups = {"professor"})
	public void testgetTimestamp(StockQuote firstReturn, int happyMusicCount, int sadMusicCount, double percentChange, String status) throws Exception {
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(firstReturn);

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.refresh();

		// Now check that the change calculation was correct.
		assertEquals(classUnderTest.obtainLastUpdateTimestamp(), firstReturn.getTimestamp());
	}
	
	@Test(dataProvider = "normalOperationDataProvider", groups = {"professor"})
	public void testgetPreviousOpen(StockQuote firstReturn, int happyMusicCount, int sadMusicCount, double percentChange, String status) throws Exception {
		// Setup the expected calls.
		when(sqg.getCurrentQuote("F")).thenReturn(firstReturn);

		// Instantiate the class to test.
		classUnderTest = new StockQuoteAnalyzer("F", sqg, sti);
		classUnderTest.refresh();

		// Now check that the change calculation was correct.
		assertEquals(classUnderTest.obtainPreviousClose(), firstReturn.getPreviousClose(), 0.01);
	}
}
