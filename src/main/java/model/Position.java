package model;

public class Position {
	private final String symbol;
	private final double amount;
	
	public Position(String symbol, double amount) {
		this.symbol = symbol;
		this.amount = amount;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public double getAmount() {
		return amount;
	}
}
