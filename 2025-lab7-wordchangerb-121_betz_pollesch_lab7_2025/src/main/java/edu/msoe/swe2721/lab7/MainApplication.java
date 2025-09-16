package edu.msoe.swe2721.lab7;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainApplication {

	/**
	 * This method will print the given array of text words out to the console, separating each word with a single space.
	 * @param text This is the array of words that is to be printed out to the console.
	 */
	public static void printText(String[] text) {
		for (int idx = 0; idx < text.length; idx++) {
			System.out.print(text[idx] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// Instantiate a synonym list.
		WordChanger wc = new WordChanger();

		// Start by reading in the file which has the words and passing them in.
		try (Scanner scanner = new Scanner(new File("SynonymList.txt"))) {
			// Read in the next line from the synonym file.
			while (scanner.hasNext()) {
				String nextLine = scanner.nextLine();
				String[] pairs = nextLine.split("\t");
				try {
					wc.addSynonymPair(pairs[0], pairs[1]);
				} catch (Exception e) {
					// If there is an exception, just ignore that line.
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Now that the words have been added to the word changer, lets have the
		// user enter a string and do some things with it.
		Scanner kbd = new Scanner(System.in);
		System.out.println(
				"Enter the phase that you would like to have words changed in.  The phase should not contain any punctuation.");
		String textToMorph = kbd.nextLine();

		// Now that we have the next line, split it into an array.
		String[] baseText = textToMorph.split("\\s+");
		String[] morphedText = textToMorph.split("\\s+");

		printText(baseText);

		for (int idx = 0; idx < morphedText.length; idx++) {
			try {
				String synonym = wc.findSynonym(morphedText[idx]);
				if (synonym != null) {
					morphedText[idx] = synonym;
				}
			} catch (Exception e) {
				// The word can not be processed. Just move along.
			}

		}

		printText(morphedText);
		kbd.close();
	}

}
