package algo;

import java.util.List;

import model.Position;
import model.ReconData;

public interface ReconcileStrategy {
	
	/**
	 * Perform reconciliation based on the D0-POS, D1-TRN and D1-POS.
	 * 
	 * @param reconData ReconData consisting of D0-POS, D1-TRN and D1-POS.
	 * @return List of final positions (i.e., recon.out)
	 */
	List<Position> reconcile(final ReconData reconData);
}
