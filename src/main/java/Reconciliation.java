

import java.io.IOException;
import java.util.List;

import algo.ReconcileStrategy;
import algo.SequentialReconcileStrategy;
import model.Position;
import model.ReconData;
import parser.ReconFileReader;
import parser.ReconFileWriter;

public class Reconciliation {
	public static void main(String[] args) throws IOException {
		ReconcileStrategy strategy = new SequentialReconcileStrategy();
		ReconFileReader reconFileReader = new ReconFileReader();
		ReconData reconData = reconFileReader.readReconFromFileLocation("src/main/resources/recon.in");
		List<Position> positions = strategy.reconcile(reconData);
		ReconFileWriter reconFileWriter = new ReconFileWriter();
		reconFileWriter.write(positions, "src/main/resources/recon.out");
	}
}
