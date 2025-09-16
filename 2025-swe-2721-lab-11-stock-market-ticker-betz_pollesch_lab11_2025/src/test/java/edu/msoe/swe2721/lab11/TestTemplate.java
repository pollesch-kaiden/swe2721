package edu.msoe.swe2721.lab11;

import edu.msoe.swe2721.lab11.exceptions.WebsiteConnectionError;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import edu.msoe.swe2721.lab11.exceptions.InvalidStockSymbolException;
import edu.msoe.swe2721.lab11.exceptions.StockTickerConnectionError;

import org.testng.annotations.BeforeMethod;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.testng.Assert.*;

import org.mockito.Mock;
import org.testng.annotations.AfterMethod;

public class TestTemplate {
	@Mock
	private StockQuoteGeneratorInterface sqg;
	@Mock
	private StockTickerAudioInterface stAudio;

	private StockQuoteAnalyzer classUnderTest;

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		sqg = Mockito.mock(StockQuoteGeneratorInterface.class);
		stAudio = Mockito.mock(StockTickerAudioInterface.class);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		sqg = null;
		stAudio = null;
	}

	@DataProvider(name = "ConstructorDataProvider")
	public Object[][] obtainConstructorDateValues()
	{
		// These two lines are a bit wierd.  By default, the return array is created before the before method above executes.  So, if in a mock
		// you are using an instance of a variable, you need to initialize it upfront here in the dataprovider before the array is returned.
		//  However, you need to be careful then, because the tests will be linked, which is not good.
		// So, it likely is better to not pass a mock in via a data provider as we are doing here, but
		// rather to write the separate tests shown further below.
		sqg = Mockito.mock(StockQuoteGeneratorInterface.class);
		stAudio = Mockito.mock(StockTickerAudioInterface.class);
		return new Object[][]{
				{"F", sqg, stAudio, false, false}, // A normal case where no exception should be generated.
				{"ZZZZZZZZZZZ", sqg, stAudio, true, false}, // A case where the symbol is invalid.
				{"F", null, stAudio, false, true}, // A case where the source for stock quotes is invalid.
				{"F", sqg, null, false, false}, // A case where the audio is null.  Still should generate an instance.
		};
	}

	@Test(groups = {"all"}, dataProvider ="ConstructorDataProvider" )
	public void testConsutructor(String symbol, StockQuoteGeneratorInterface p_sqg, StockTickerAudioInterface p_stAudio, boolean invalidSymbol, boolean invalidStockSrc)
			throws NullPointerException, InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
		if (!invalidStockSrc && !invalidSymbol) {
			// In this case, both the stock source and the symbol are valid.  Therefore, we should simply instantiate an instance of the class.
			// Arrange - None Here
			// This is where we would normally setup the expected calls for sti and sqg.
			// In this case, there are none.  For this particular test, we are really just checking that values of null throw exceptions and that nothing else happens.
			// In other tests, you will need to program expected calls.
			// A second example test shows you a bit more how that might appear.

			// Act
			classUnderTest = new StockQuoteAnalyzer(symbol, p_sqg, p_stAudio);

			// Assert
			assertNotNull(classUnderTest, "The class should not be null at this point.");
			// The following are two different ways to verify that the constructor does not make any calls to the stock quote generator,
			// which it should not do per the sequence diagram.  It is not always necessary to verify that a method is not called in every case,
			// but there are times when it is usedful and should be done.  This is provided more as an example.
			verify(p_sqg, times(0)).getCurrentQuote(symbol);
			verify(p_sqg, never()).createNewInstance(symbol);
		} else {
			// This indicates the constructor should be throwing an exception.  Lets test that.
			if (invalidSymbol) {
				// The symbol is invalid.
				assertThrows(InvalidStockSymbolException.class, () -> new StockQuoteAnalyzer(symbol, p_sqg, p_stAudio));
			} else {
				// The source is invalid for stocks.
				assertThrows(StockTickerConnectionError.class, () -> new StockQuoteAnalyzer(symbol, p_sqg, p_stAudio));
			}
		}
	}
		@Test(groups={"all"})
		public void testNormalConstructor() throws InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
		// Arrange
		// There are no when statements here to be programmed.  The default mocks are fine.

		// Act
			classUnderTest = new StockQuoteAnalyzer("F", sqg, stAudio);

			// Assert
			assertNotNull(classUnderTest, "The class should not be null at this point.");
			// The following are two different ways to verify that the constructor does not make any calls to the stock quote generator,
			// which it should not do per the sequence diagram.  It is not always necessary to verify that a method is not called in every case,
			// but there are times when it is usedful and should be done.  This is provided more as an example.
			verify(sqg, times(0)).getCurrentQuote("F");
			verify(sqg, never()).createNewInstance("F");
		}

	@Test(groups={"all"})
	public void testNormalConstructorNoSOund() throws InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
		// Arrange
		// There are no when statements here to be programmed.  The default mocks are fine.

		// Act
		classUnderTest = new StockQuoteAnalyzer("F", sqg, null);

		// Assert
		assertNotNull(classUnderTest, "The class should not be null at this point.");
		// The following are two different ways to verify that the constructor does not make any calls to the stock quote generator,
		// which it should not do per the sequence diagram.  It is not always necessary to verify that a method is not called in every case,
		// but there are times when it is usedful and should be done.  This is provided more as an example.
		verify(sqg, times(0)).getCurrentQuote("F");
		verify(sqg, never()).createNewInstance("F");
	}

	@Test(groups={"all"})
	public void testInvalidSymbolConstructor() throws InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
		// Arrange
		// There are no when statements here to be programmed.  The default mocks are fine.

		// Act and Assert
		assertThrows(InvalidStockSymbolException.class, () -> new StockQuoteAnalyzer("ZZZZZZZZZZZZ", sqg, stAudio));
	}

	@Test(groups={"all"})
	public void testInvalidSourceConstructor() throws InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
		// Arrange
		// There are no when statements here to be programmed.  The default mocks are fine.

		// Act and Assert
		assertThrows(StockTickerConnectionError.class, () -> new StockQuoteAnalyzer("F", null, stAudio));
	}



}

