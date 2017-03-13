package algo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import algo.ReconcileStrategy;
import algo.SequentialReconcileStrategy;
import model.Position;
import model.ReconData;
import parser.ReconFileReader;

public class ReconciliationStrategyTest {

	@Test
	public void testReconcile() throws IOException {
		ReconcileStrategy strategy = new SequentialReconcileStrategy();
		ReconFileReader reconFileReader = new ReconFileReader();
		ReconData reconData = reconFileReader.readReconFromFileLocation("src/test/resources/recon.in");
		List<Position> positions = strategy.reconcile(reconData);
		
		assertEquals(4, positions.size());
		
		Map<String, Position> map = new HashMap<>();
		for (Position p : positions) {
			map.put(p.getSymbol(), p);
		}
		
		Position p1 = map.get("Cash");
		assertEquals(8000, p1.getAmount(), Double.MIN_VALUE);
		
		Position p2 = map.get("GOOG");
		assertEquals(10, p2.getAmount(), Double.MIN_VALUE);
		
		Position p3 = map.get("TD");
		assertEquals(-100, p3.getAmount(), Double.MIN_VALUE);
		
		Position p4 = map.get("MSFT");
		assertEquals(10, p4.getAmount(), Double.MIN_VALUE);
	}
}
