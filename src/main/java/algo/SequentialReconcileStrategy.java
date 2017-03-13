package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algo.AbstractReconcileStrategy.Pair;
import model.Position;
import model.ReconData;
import model.Transaction;

/**
 * A reconcile strategy that processes each transactions one by one sequentially.
 * 
 * @author jie
 *
 */
public class SequentialReconcileStrategy extends AbstractReconcileStrategy {
	/**
	 * Time complexity: O(N) where N is the number of transactions.
	 * Space complexity: O(N + M) where N is the number of transactions and M is the number of initial positions.
	 */
	@Override
	public List<Position> reconcile(final ReconData reconData) {

		// D0-POS convert to map
		Map<String, Position> initialPositionsMap = new HashMap<>();
		for (Position initialPosition : reconData.getInitialPositions()) {
			initialPositionsMap.put(initialPosition.getSymbol(), initialPosition);
		}

		// Add "Cash" if initial position doesn't contain an entry to make calculations below easier.
		if (!initialPositionsMap.containsKey("Cash")) {
			initialPositionsMap.put("Cash", new Position("Cash", 0));
		}

		// for each transaction in D1-TRN, update the position in the initialPositionsMap (D0-POS)
		for (Transaction transaction : reconData.getTransactions()) {
			
			String symbol = transaction.getSymbol();
			// update position in initialPositionsMap from this transaction
			Pair pair = calculateNewPosition(initialPositionsMap.get(symbol), transaction);
			initialPositionsMap.put(pair.getPosition().getSymbol(), pair.getPosition());
			// update Cash
			double updatedCashAmount = initialPositionsMap.get("Cash").getAmount() + pair.getResultCashAmount();
			initialPositionsMap.put("Cash", new Position("Cash", updatedCashAmount));
			
		}

		// D1-POS convert to map
		Map<String, Position> finalPositionsMap = new HashMap<>();
		for (Position finalPosition : reconData.getFinalPositions()) {
			finalPositionsMap.put(finalPosition.getSymbol(), finalPosition);
		}

		// Compare calculated positions with D1-POS positions and store in output.
		Map<String, Position> output = new HashMap<>();
		for (Map.Entry<String, Position> entry : initialPositionsMap.entrySet()) {
			if (finalPositionsMap.containsKey(entry.getKey())) {
				// both calculated position and final position contain the symbol, now check if their amounts match.
				Position calculatedPosition = entry.getValue();
				Position finalPosition = finalPositionsMap.get(entry.getKey());
				double diff = finalPosition.getAmount() - calculatedPosition.getAmount();
				if (diff != 0) {
					output.put(entry.getKey(), new Position(entry.getKey(), diff));
				}
				// remove this from final positions map
				finalPositionsMap.remove(entry.getKey());
			} else {
				output.put(entry.getKey(), new Position(entry.getKey(), -entry.getValue().getAmount()));
			}
		}

		// check if there are any remaining positions in the finalPositionsMap
		output.putAll(finalPositionsMap);

		List<Position> outputPositions = new ArrayList<>();
		for (Map.Entry<String, Position> entry : output.entrySet()) {
			if (entry.getValue().getAmount() != 0) {
				outputPositions.add(entry.getValue());
			}
		}
		return outputPositions;
	}
}
