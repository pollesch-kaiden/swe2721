package edu.msoe.swe2721.lab9;

import java.util.Scanner;

/**
 * This is the main driver for the calculator.
 */
public class Main {

    /**
     * This is the main method.
     *
     * @param args Ignored.
     */
    public static void main(String args[]) {
        /**
         * Instantiate a scanner.
         */
        Scanner kbd = new Scanner(System.in);
        /**
         * Instantiate a console calculator.
         */
        ConsoleCalculator cc = new ConsoleCalculator();

        String lineRead;
        do {
            /**
             * Read in the next line from the keyboard.
             */
            lineRead = kbd.nextLine();
            /**
             * So long as the text is not quit in any form, process the line with the calculator.
             */
            if (!lineRead.equalsIgnoreCase("QUIT")) {
                cc.processLine(lineRead);
            }
            /**
             * Continue looping.
             */
        } while (!lineRead.equalsIgnoreCase("QUIT"));
    }
}
