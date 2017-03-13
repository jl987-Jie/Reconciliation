package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Position;

public class ReconFileWriter {

	public void write(List<Position> positions, String file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
		for (Position p : positions) {
			writer.write(p.getSymbol() + " " + p.getAmount());
			writer.newLine();
		}
		writer.close();
	}
}
