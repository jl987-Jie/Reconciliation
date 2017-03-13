package parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import model.ReconData;
import model.Transaction.Side;

public class ReconFileReaderTest {

	@Test
	public void testReadReconFromFileLocation() throws IOException {
		ReconFileReader reconFileReader = new ReconFileReader();
		ReconData reconData = reconFileReader.readReconFromFileLocation("src/test/resources/recon.in");
		
		assertEquals(4, reconData.getInitialPositions().size());
		
		assertEquals("AAPL", reconData.getInitialPositions().get(0).getSymbol());
		assertEquals(100, reconData.getInitialPositions().get(0).getAmount(), Double.MIN_VALUE);
		
		assertEquals("GOOG", reconData.getInitialPositions().get(1).getSymbol());
		assertEquals(200, reconData.getInitialPositions().get(1).getAmount(), Double.MIN_VALUE);
		
		assertEquals("SP500", reconData.getInitialPositions().get(2).getSymbol());
		assertEquals(175.75, reconData.getInitialPositions().get(2).getAmount(), Double.MIN_VALUE);
		
		assertEquals("Cash", reconData.getInitialPositions().get(3).getSymbol());
		assertEquals(1000, reconData.getInitialPositions().get(3).getAmount(), Double.MIN_VALUE);
		
		assertEquals(6, reconData.getTransactions().size());
		assertEquals("AAPL", reconData.getTransactions().get(0).getSymbol());
		assertEquals(Side.SELL, reconData.getTransactions().get(0).getSide());
		assertEquals(100, reconData.getTransactions().get(0).getNumShares());
		assertEquals(30000, reconData.getTransactions().get(0).getAmount(), Double.MIN_VALUE);
		
		assertEquals("GOOG", reconData.getTransactions().get(1).getSymbol());
		assertEquals(Side.BUY, reconData.getTransactions().get(1).getSide());
		assertEquals(10, reconData.getTransactions().get(1).getNumShares());
		assertEquals(10000, reconData.getTransactions().get(1).getAmount(), Double.MIN_VALUE);
		
		assertEquals("Cash", reconData.getTransactions().get(2).getSymbol());
		assertEquals(Side.DEPOSIT, reconData.getTransactions().get(2).getSide());
		assertEquals(0, reconData.getTransactions().get(2).getNumShares());
		assertEquals(1000, reconData.getTransactions().get(2).getAmount(), Double.MIN_VALUE);
		
		assertEquals("Cash", reconData.getTransactions().get(3).getSymbol());
		assertEquals(Side.FEE, reconData.getTransactions().get(3).getSide());
		assertEquals(0, reconData.getTransactions().get(3).getNumShares());
		assertEquals(50, reconData.getTransactions().get(3).getAmount(), Double.MIN_VALUE);
		
		assertEquals("GOOG", reconData.getTransactions().get(4).getSymbol());
		assertEquals(Side.DIVIDEND, reconData.getTransactions().get(4).getSide());
		assertEquals(0, reconData.getTransactions().get(4).getNumShares());
		assertEquals(50, reconData.getTransactions().get(4).getAmount(), Double.MIN_VALUE);
		
		assertEquals("TD", reconData.getTransactions().get(5).getSymbol());
		assertEquals(Side.BUY, reconData.getTransactions().get(5).getSide());
		assertEquals(100, reconData.getTransactions().get(5).getNumShares());
		assertEquals(10000, reconData.getTransactions().get(5).getAmount(), Double.MIN_VALUE);
		
		assertEquals(4, reconData.getFinalPositions().size());
		
		assertEquals("GOOG", reconData.getFinalPositions().get(0).getSymbol());
		assertEquals(220, reconData.getFinalPositions().get(0).getAmount(), Double.MIN_VALUE);
		
		assertEquals("SP500", reconData.getFinalPositions().get(1).getSymbol());
		assertEquals(175.75, reconData.getFinalPositions().get(1).getAmount(), Double.MIN_VALUE);
		
		assertEquals("Cash", reconData.getFinalPositions().get(2).getSymbol());
		assertEquals(20000, reconData.getFinalPositions().get(2).getAmount(), Double.MIN_VALUE);
		
		assertEquals("MSFT", reconData.getFinalPositions().get(3).getSymbol());
		assertEquals(10, reconData.getFinalPositions().get(3).getAmount(), Double.MIN_VALUE);
	}
}
