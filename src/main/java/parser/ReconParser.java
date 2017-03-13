package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Position;
import model.Transaction;
import model.Transaction.Side;
import parser.exception.ReconParserException;

public class ReconParser {

	private static final Logger logger = LoggerFactory.getLogger(ReconParser.class);
	
	/**
	 * Parse the string position into a Position object.
	 * 
	 * @param position position encoded as a string
	 * @return a Position object
	 * 
	 * @throws ReconParserException - if position string is invalid (i.e., split by whitespace and the resulting
	 * array length is not equal to two) or the amount field cannot be parsed.
	 */
	public Position parsePosition(String position) throws ReconParserException {
		if (position == null || position.isEmpty()) {
			return null;
		}
		String[] splits = position.split("\\s+");
		
		// position is invalid if its length != 2
		if (splits.length != 2) {
			logger.error("Invalid position: " + position);
			throw new ReconParserException("Invalid position: " + position);
		}
		
		String symbol = splits[0];
		double amount = parseAmount(splits[1]);
		
		return new Position(symbol, amount);
	}
	
	/**
	 * Parse the string transaction into a Transaction object.
	 * 
	 * @param transaction transaction encoded as a string
	 * @return a Transaction object
	 * 
	 * @throws ReconParserException - if input string is invalid (i.e., split by whitespace and the resulting
	 * array length is not equal to four) or if side string is not in the list of Side enums or the number of shares
	 * is not a long or the amount is not a double.
	 */
	public Transaction parseTransaction(String transaction) throws ReconParserException {
		if (transaction == null || transaction.isEmpty()) {
			return null;
		}
		String[] splits = transaction.split("\\s+");
		
		// transaction is invalid if its length != 4
		if (splits.length != 4) {
			logger.error("Invalid transaction: " + transaction);
			throw new ReconParserException("Invalid transaction: " + transaction);
		}
		String symbol = splits[0];
		Side side = parseSide(splits[1]);
		long numShares = parseNumShares(splits[2]);
		double amount = parseAmount(splits[3]);
		
		return new Transaction(symbol, side, numShares, amount);
	}
	
	private double parseAmount(String amountString) throws ReconParserException {
		double amount;
		try {
			amount = Double.parseDouble(amountString);
		} catch (Exception e) {
			logger.error("Invalid position amount: " + amountString);
			throw new ReconParserException("Invalid position amount: " + amountString);
		}
		return amount;
	}
	
	private Side parseSide(String sideString) throws ReconParserException {
		Side side;
		
		if ("SELL".equals(sideString)) {
			side = Side.SELL;
		} else if ("BUY".equals(sideString)) {
			side = Side.BUY;
		} else if ("DEPOSIT".equals(sideString)) {
			side = Side.DEPOSIT;
		} else if ("FEE".equals(sideString)) {
			side = Side.FEE;
		} else if ("DIVIDEND".equals(sideString)) {
			side = Side.DIVIDEND;
		} else {
			logger.error("Invalid side: " + sideString);
			throw new ReconParserException("Invalid side: " + sideString);
		}
		return side;
	}
	
	private long parseNumShares(String numSharesString) throws ReconParserException {
		long numShares;
		try {
			numShares = Long.parseLong(numSharesString);
		} catch (Exception e) {
			logger.error("Invalid number of shares: " + numSharesString);
			throw new ReconParserException("Invalid number of shares: " + numSharesString);
		}
		return numShares;
	}
}
