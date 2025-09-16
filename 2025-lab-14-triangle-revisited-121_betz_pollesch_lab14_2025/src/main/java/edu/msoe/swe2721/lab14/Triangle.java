package edu.msoe.swe2721.lab14;

/**
 * This class will hold information about a triangle, or at least a 3 sided object that could be a triangle.
 */
public class Triangle {
    /**
     * These represent the three sides of the triangle.
     */
    private double a, b, c;

    /**
     * This method will construct a new instance of a Triangle based upon the sides.
     * @param a This is side a length.  Must be greater than 0.
     * @param b This is side b length.  Must be greater than 0.
     * @param c This is side c length.  Must be greater than 0.
     * @throws TriangleConstructionException This exception will be thrown if any side is invalid.
     */
    public Triangle(double a, double b, double c) throws TriangleConstructionException {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new TriangleConstructionException("All sides must be greater than zero.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * This method will determine, based upon the sides, whether the triangle is valid.
     * @return true if the triangle is valid from a definition of sides standpoint.  False if it is not valid.
     */
    public boolean determineIfValid() {
        return (a + b > c) && (b + c > a) && (a + c > b);
    }

    /**
     * This method will determine the triangle type based on sides a, b, and c.
     * @return TriangleType.INVALID if invalid or TriangleType.EQUILATERAL, TriangleType.ISOSCELES, or TriangleType.SCALENE;
     */
    public TriangleType determineTriangleType() {
        if (!determineIfValid()) {
            return TriangleType.INVALID;
        }

        if (a == b && b == c) {
            return TriangleType.EQUILATERAL;
        } else if (a == b || b == c || a == c) {
            return TriangleType.ISOSCELES;
        } else {
            return TriangleType.SCALENE;
        }
    }

    /**
     * This method will calculate the perimeter of the triangle.
     * @return The return will be 0 if the triangle is not valid or the sum of the three sides if it is valid.
     */
    public double calculatePerimeter() {
        if (!determineIfValid()) {
            return 0.0;
        }
        return a + b + c;
    }

    /**
     * This method will calculate the area of the triangle using Heron's formula, assuming the triangle is valid.
     * @return The return value will be the area of the triangle, if valid, or 0 if the triangle is not valid.
     * The result should be accurate to at least the nearest 0.0001.
     */
    public double calculateArea() {
        if (!determineIfValid()) {
            return 0.0;
        }

        double s = (a + b + c) / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    /**
     * This method will return a textual representation for the triangle.
     * @return A string representing the triangle.  For example a=4.000 b=5.000 c=6.000 area=9.922 SCALENE
     */
    public String obtainTextualRepresentation() {
        String format = "a=%.3f b=%.3f c=%.3f area=%.3f %s";
        return String.format(format,
                a, b, c,
                calculateArea(),
                determineTriangleType().toString());
    }
}
