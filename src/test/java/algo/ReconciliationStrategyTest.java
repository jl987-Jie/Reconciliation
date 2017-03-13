package algo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import model.Position;
import model.ReconData;
import parser.ReconFileReader;

public class ReconciliationStrategyTest {

	//@Test
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
	
	//@Test
	public void testReconcileCash() throws IOException {
		ReconcileStrategy strategy = new SequentialReconcileStrategy();
		ReconFileReader reconFileReader = new ReconFileReader();
		ReconData reconData = reconFileReader.readReconFromFileLocation("src/test/resources/recon_cash.in");
		List<Position> positions = strategy.reconcile(reconData);
		
		assertEquals(1, positions.size());
		assertEquals("Cash", positions.get(0).getSymbol());
		assertEquals(-950, positions.get(0).getAmount(), Double.MIN_VALUE);
	}
	
	@Test
	public void testReconcileCorrect() throws IOException {
		ReconcileStrategy strategy = new SequentialReconcileStrategy();
		ReconFileReader reconFileReader = new ReconFileReader();
		ReconData reconData = reconFileReader.readReconFromFileLocation("src/test/resources/recon_short_sell.in");
		List<Position> positions = strategy.reconcile(reconData);
		
		assertEquals(3, positions.size());
		
		Map<String, Position> map = new HashMap<>();
		for (Position p : positions) {
			map.put(p.getSymbol(), p);
		}
		
		Position p1 = map.get("GOOG");
		assertEquals(-10, p1.getAmount(), Double.MIN_VALUE);
		
		Position p2 = map.get("AAPL");
		assertEquals(-5, p2.getAmount(), Double.MIN_VALUE); // 5 lower than expected. I expect -10, bank says -15.
		
		Position p3 = map.get("Cash");
		assertEquals(100, p3.getAmount(), Double.MIN_VALUE);
	}
}
