package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Position;
import model.ReconData;
import model.Transaction;
import parser.exception.ReconParserException;

public class ReconFileReader {

	private static final Logger logger = LoggerFactory.getLogger(ReconFileReader.class);

	public ReconData readReconFromFileLocation(String path) throws IOException {

		String input = null;
		BufferedReader br = null;
		List<Position> initialPositions = new ArrayList<>();
		List<Position> finalPositions = new ArrayList<>();
		List<Transaction> transactions = new ArrayList<>();

		boolean d0Pos = false;
		boolean d1Trn = false;
		boolean d1Pos = false;
		try {
			br = new BufferedReader(new FileReader(path));
			ReconParser reconParser = new ReconParser();
			while ((input = br.readLine()) != null) {
				if (!input.isEmpty()) {
					if ("D0-POS".equals(input)) {
						d0Pos = true;
						continue;
					} else if ("D1-TRN".equals(input)) {
						d0Pos = false;
						d1Trn = true;
						continue;
					} else if ("D1-POS".equals(input)) {
						d1Trn = false;
						d1Pos = true;
						continue;
					}
					if (d0Pos || d1Pos) {
						Position position = null;
						try {
							position = reconParser.parsePosition(input);
						} catch (ReconParserException e) {
							logger.error("Unable to parse position: " + input);
						}
						if (position != null) {
							if (d0Pos) {
								initialPositions.add(position);
							} else {
								finalPositions.add(position);
							}
						}
					} else if (d1Trn) {
						Transaction transaction = null;
						try {
							transaction = reconParser.parseTransaction(input);
						} catch (ReconParserException e) {
							logger.error("Unable to parse transaction: " + input);
						}
						if (transaction != null) {
							transactions.add(transaction);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error reading from file: " + path);
		} finally {
			br.close();
		}
		return new ReconData(transactions, initialPositions, finalPositions);
	}
}
