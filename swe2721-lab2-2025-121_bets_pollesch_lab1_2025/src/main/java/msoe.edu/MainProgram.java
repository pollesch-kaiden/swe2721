package msoe.edu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This class holds the main program for the triangle analyzer.  It serves to setup the file input 
 * and read the file, passing lines into the triangle analyzer as is appropriate.
 * @author schilling
 */
public class MainProgram {
	public static void main(String[] args) {
		/**
		 * Make sure that the user has properly specified the input parameters.
		 */
		if (args.length != 2)
		{
			System.err.println("Usage: Program <Source Definitions File> <Output File>");
			System.exit(0);
		}

		/**
		 * Based upon the arguments, set up the two filenames.
		 */
		String filename = args[0];
		String outputFile = args[1];

		/**
		 * Open up the input file and create the output file as well.
		 */
		File inputFile = new File(filename);
		try (Scanner theFile = new Scanner(inputFile);PrintStream outputFileStream = new PrintStream(new File(outputFile)); ) {
			/**
			 * Iterate over all of the lines in the program.
			 */
			while (theFile.hasNextLine()) {
				String textLine = theFile.nextLine();
				/**
				 * If the line is non-blank, process it.  Otherwise ignore it, placing a blank line in the output file.
				 */
				if (textLine.length() > 0) {
					/**
					 * Instantiate a new object and print out the analysis of the triangle.
					 */
					msoe.edu.TriangleAnalyzer ta = new msoe.edu.TriangleAnalyzer(textLine);
					ta.processTextualDefinition();
					ta.analyzeTriangle();
					outputFileStream.println(ta.obtainAnalysisString());
				}
				else {
					outputFileStream.println("");
				}
			}
			/**
			 * If the source file can not be found, print out an error to the console and exit.
			 */
		} catch (FileNotFoundException e) {
			System.err.println("The file " + filename + " could not be found.");
		}
	}
}
