package model;

public class Transaction {
	private final String symbol;
	private final Side side;
	private final long numShares;
	private final double amount;
	
	public Transaction(String symbol, Side side, long numShares, double amount) {
		this.symbol = symbol;
		this.side = side;
		this.numShares = numShares;
		this.amount = amount;
	}
	
	public static enum Side {
		BUY,
		SELL,
		DEPOSIT,
		FEE,
		DIVIDEND
	}

	public String getSymbol() {
		return symbol;
	}

	public Side getSide() {
		return side;
	}
	
	public long getNumShares() {
		return numShares;
	}
	
	public double getAmount() {
		return amount;
	}
}
