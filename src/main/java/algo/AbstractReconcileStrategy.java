package algo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Position;
import model.Transaction;
import model.Transaction.Side;

public abstract class AbstractReconcileStrategy implements ReconcileStrategy {

	private static final Logger logger = LoggerFactory.getLogger(AbstractReconcileStrategy.class);
	
	/**
	 * Data structure for storing the update position and the updated cash amount for each transaction and old position.
	 * @author jie
	 *
	 */
	protected class Pair {
		private final Position position;
		private final double resultCashAmount;
		
		public Pair(Position position, double amount) {
			this.position = position;
			this.resultCashAmount = amount;
		}
		
		public Position getPosition() {
			return position;
		}

		public double getResultCashAmount() {
			return resultCashAmount;
		}
	}
	
	/**
	 * Calculate new position based on the current transaction.
	 * 
	 * @param oldPosition
	 * @param transaction
	 * 
	 * @return new position based on the current transaction.
	 */
	protected Pair calculateNewPosition(final Position oldPosition, final Transaction transaction) {
		if (oldPosition == null) {
			return calculatePositionHelper(transaction);
		} else {
			Pair p = calculatePositionHelper(transaction);
			return new Pair(
					new Position(oldPosition.getSymbol(), oldPosition.getAmount() + p.getPosition().getAmount()), 
						p.getResultCashAmount());
		}
	}
	
	/**
	 * Given a transaction, return a new Position based on the transaction and the resulting amount of the transaction.
	 * @param transaction current transaction being processed
	 * @return new Position based on the transaction and the resulting amount of the transaction.
	 */
	private Pair calculatePositionHelper(final Transaction transaction) {
		if (Side.BUY == transaction.getSide()) {
			Position p = new Position(transaction.getSymbol(), transaction.getNumShares());
			return new Pair(p, -transaction.getAmount());
		} else if (Side.FEE == transaction.getSide()) {
			Position p = new Position(transaction.getSymbol(), -transaction.getAmount());
			return new Pair(p, 0); // for Cash, the transaction amount is 0 because it's already included in the position object's amount field
		} else if (Side.SELL == transaction.getSide()) {
			Position p = new Position(transaction.getSymbol(), -transaction.getNumShares());
			return new Pair(p, transaction.getAmount());
		} else if (Side.DIVIDEND == transaction.getSide()) {
			Position p = new Position(transaction.getSymbol(), 0);
			return new Pair(p, transaction.getAmount());
		} else if (Side.DEPOSIT == transaction.getSide()) {
			Position p = new Position(transaction.getSymbol(), transaction.getAmount());
			return new Pair(p, 0); // for Cash, the transaction amount is 0 because it's already included in the position object's amount field
		}
		return null;
	}
}
