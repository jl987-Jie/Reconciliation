package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.Position;
import model.Transaction;
import model.Transaction.Side;
import parser.exception.ReconParserException;

public class ReconParserTest {

	@Test
	public void testParsePosition() throws ReconParserException {
		String input = "AAPL 100";
		ReconParser parser = new ReconParser();
		Position position = parser.parsePosition(input);
		assertEquals("AAPL", position.getSymbol());
		assertEquals(100, position.getAmount(), Double.MIN_VALUE);
	}

	@Test
	public void testParseTransaction() throws ReconParserException {
		String input = "GOOG BUY 10 10000";
		ReconParser parser = new ReconParser();
		Transaction transaction = parser.parseTransaction(input);
		assertEquals("GOOG", transaction.getSymbol());
		assertEquals(Side.BUY, transaction.getSide());
		assertEquals(10, transaction.getNumShares());
		assertEquals(10000, transaction.getAmount(), Double.MIN_VALUE);
	}

	@Test(expected=ReconParserException.class)
	public void testParsePositionException() throws ReconParserException {
		String input = "AAPL 100Nonsense";
		ReconParser parser = new ReconParser();
		parser.parsePosition(input);
	}
	
	@Test(expected=ReconParserException.class)
	public void testParseTransactionInvalidSideException() throws ReconParserException {
		String input = "GOOG BUYS 10 10000";
		ReconParser parser = new ReconParser();
		parser.parseTransaction(input);
	}
	
	@Test(expected=ReconParserException.class)
	public void testParseTransactionInvalidNumSharesException() throws ReconParserException {
		String input = "GOOG BUY 10nonsense 10000";
		ReconParser parser = new ReconParser();
		parser.parseTransaction(input);
	}
	
	@Test(expected=ReconParserException.class)
	public void testParseTransactionInvalidAmountException() throws ReconParserException {
		String input = "GOOG BUYS 10 10000nonsense";
		ReconParser parser = new ReconParser();
		parser.parseTransaction(input);
	}
}
