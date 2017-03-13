package model;

import java.util.List;

public class ReconData {

	private List<Transaction> transactions;
	private List<Position> initialPositions;
	private List<Position> finalPositions;
	
	public ReconData(List<Transaction> transactions, List<Position> initialPositions, List<Position> finalPositions) {
		this.transactions = transactions;
		this.initialPositions = initialPositions;
		this.finalPositions = finalPositions;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public List<Position> getInitialPositions() {
		return initialPositions;
	}
	public void setInitialPositions(List<Position> initialPositions) {
		this.initialPositions = initialPositions;
	}
	public List<Position> getFinalPositions() {
		return finalPositions;
	}
	public void setFinalPositions(List<Position> finalPositions) {
		this.finalPositions = finalPositions;
	}
}
