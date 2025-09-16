package edu.msoe.swe2721.lab14;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This class will serve as a factory, creatiung triangles as is applicable.
 */
public class TriangleFactory {
    private List<Triangle> triangles = new LinkedList<>();

    /**
     * This method will parse the input stream until the text quit is received.
     * @param lineReader This is a scanner that will read in one line of text at a time.
     *                   If three sides are read in, a triangle will be instantiated and added to the list.
     */
    public void parseStream(Scanner lineReader) {
        while (lineReader.hasNextLine()) {
            String line = lineReader.nextLine().trim().toLowerCase();
            if (line.equals("quit")) {
                break;
            }

            try {
                String[] numbers = line.split("\\s+");
                if (numbers.length == 3) {
                    double a = Double.parseDouble(numbers[0]);
                    double b = Double.parseDouble(numbers[1]);
                    double c = Double.parseDouble(numbers[2]);

                    Triangle triangle = new Triangle(a, b, c);
                    if (triangle.determineIfValid()) {
                        triangles.add(triangle);
                    }
                }
            } catch (NumberFormatException | TriangleConstructionException e) {
                // Skip invalid input
                continue;
            }
        }
    }

    /**
     * This method will return the number of triangles that have been added into the array.
     * @return The number of valid read triangles will be returned.
     */
    public int getCount()
    {
        return triangles.size();
    }

    /**
     * This method will iterate over the valid triangles and obtain their representations
     * @return The string representing the concatenation of all valid triangles will be returned.
     */
    public String obtainTriangleText()
    {
        StringBuilder sb = new StringBuilder();
        for (Triangle triangle : triangles) {
            sb.append(triangle.obtainTextualRepresentation()).append("\n");
        }
        return sb.toString();
    }

    /**
     * This method will obtain the total area for all triangles in the system.
     * @return The total area will be returned.  It must be accurate to at least the nearest 0.001.
     */
    public double obtainTotalArea()
    {
        double totalArea = 0.0;
        for (Triangle triangle : triangles) {
            totalArea += triangle.calculateArea();
        }
        return totalArea;
    }
}
