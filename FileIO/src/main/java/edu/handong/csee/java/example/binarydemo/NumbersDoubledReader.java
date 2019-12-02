package edu.handong.csee.java.example.binarydemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NumbersDoubledReader {
	private ObjectInputStream inputStream = null;
	public static void main(String[] args) {
		
		NumbersDoubledReader reader = new NumbersDoubledReader();
		reader.run();

	}

	private void run() {
		String inputFileName = ("numbersDoubled.dat");
		try {
			inputStream = new ObjectInputStream(
					new FileInputStream(inputFileName));
		} catch(FileNotFoundException e) {
			System.out.println("File " + inputFileName + " not found.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Error opening input file: " + inputFileName);
		}
	}
}