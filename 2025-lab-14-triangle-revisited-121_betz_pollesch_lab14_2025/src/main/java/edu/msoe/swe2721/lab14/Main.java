package edu.msoe.swe2721.lab14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the main class.  It will allow the user to either read in files or type on the console.
 */
public class Main {
    public static void main(String args[])  {
        TriangleFactory tf = new TriangleFactory();

        if (args.length >0)
        {
            for (String s : args)
            {
                try {
                    File f = new File(s);
                    Scanner lineScanner = new Scanner(f);
                    tf.parseStream(lineScanner);
                } catch (FileNotFoundException e) {
                    System.err.println("File" + s + "not found.");
                }
            }
        }
        else {
            Scanner kbd = new Scanner(System.in);
            tf.parseStream(kbd);
        }
        System.out.println(tf.obtainTriangleText());
        System.out.printf("Total Triangles Read: %d Total area: %.3f", tf.getCount(), tf.obtainTotalArea());
    }
}
