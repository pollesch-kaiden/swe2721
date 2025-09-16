package msoe.edu;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is responsible for helping to analyze whether a definition for a
 * triangle is valid or not. The user will instantiate this class with an
 * instance of a line that represents a triangle definition. The program will
 * then analyze that to determine whether a valid triangle is defined.
 *
 * @author schilling
 */

/**
 * This enumeration defines the types of triangles that may exist.
 */
enum TriangleTypeEnum
{
    INVALID,  RIGHT_SCALENE, SCALENE, RIGHT_ISOSCELES, ISOSCELES, EQUILATERAL
}

public class TriangleAnalyzer {
    /**
     * This attribute is the string that is input for the given triangle.
     * It is the value passed into the constructor.
     */
    private String textLine;

    /**
     * This boolean will be true if all read sides are integers or false if any side that is read in is a floating point number.
     */
    private boolean allIntegerSides = true;

    /**
     * This is the string representation for the triangle.  It is the portion of the textLine prior to the comment.
     */
    private String triangleDefinitionString = "";

    /**
     * These are the comments entered for the given triangle.
     */
    private String comment = "";

    /**
     * This attribute indicates if the line entered has invalid syntax based upon the specification.
     */
    private boolean invalidSyntax = false;

    /**
     * This attribute will be true if the triangle is a right triangle.
     */
    private boolean rightTriangle = false;

    /**
     * This attribute indicates that the triangle itself is valid.
     */
    //private boolean validTriangle = false;

    private TriangleTypeEnum triangleType = TriangleTypeEnum.INVALID;

    /**
     * This array is the set of side lengths for the triangle.
     */
    private double triangleSides[] = new double[3];

    /**
     * This is the constructor for the class. It takes in a single parameter,
     * namely the definition for the triangle. This definition will then be used
     * to instantiate a new TriangleAnalyzer which will analyze the given
     * entry.
     *
     * @param textLine This is the line of test representing the triangle. It
     *                 consists of 3 numbers (a, b, c) which are whitespace separated
     *                 representing the three sides of the triangle as well as an
     *                 optional comments segment that follows.
     */
    public TriangleAnalyzer(String textLine) {
        this.textLine = textLine;

        // Start by splitting based on a hash character. Anything before the
        // hash is valid. Anything after the hash is a comment.
        int hashLocation = textLine.indexOf("#");
        if (hashLocation >= 0) {
            // There is both a definition and a comment on the line.
            triangleDefinitionString = textLine.substring(0, hashLocation).trim();
            comment = textLine.substring(hashLocation, textLine.length());
        } else {
            triangleDefinitionString = textLine;
        }
    }

    public void processTextualDefinition() {
        try (Scanner sideScanner = new Scanner(triangleDefinitionString);)
        {
            // Start by reading in the 3 sides of the triangle from the line.
            for (int index = 0; index < 3; index++) {
                if (sideScanner.hasNextInt())
                {
                   triangleSides[index] = (double)sideScanner.nextInt();
                }
                else if (sideScanner.hasNextDouble()) {
                    triangleSides[index] = sideScanner.nextInt();
                    allIntegerSides = false;
                } else {
                    invalidSyntax = true;
                }
            }

            if (sideScanner.hasNextInt() || sideScanner.hasNextDouble()) {
                // There should not be another double here.
                invalidSyntax = true;
            }
        } catch (InputMismatchException ex) {
            // This will occur if the input is damaged.  When this occurs, set each side to -1 to indicate they are invalid.
            // Then continue on constructing the class.
            triangleSides[0] = -1;
            triangleSides[1] = -1;
            triangleSides[2] = -1;
            invalidSyntax = true;
        }
    }

    /**
     * This method will analyze the triangle that has been setup.  It should only be called if we know the syntax is valid.
     */
    public void analyzeTriangle() {
        if (!invalidSyntax) {
            // Let's determine the type of the triangle.
            // To do this, first make a deep copy of the array.
            double triangleSideCopy[] = new double[3];
            System.arraycopy(triangleSides, 0, triangleSideCopy, 0, 3);

            // Now that we have a copy of the three sides, sort them in ascending order.
            // This way, the Hypotenuse will be in the last location as the longest side.
            java.util.Arrays.sort(triangleSideCopy);

            // Now determine if the triangle is valid.  Check sides first.
            if (triangleSideCopy[0] < 0 || triangleSideCopy[1] <= 0 || triangleSideCopy[2] <= 0) {
                triangleType = TriangleTypeEnum.INVALID;
            } else if ((triangleSideCopy[0] + triangleSideCopy[1] >= triangleSideCopy[2])
                    && (triangleSideCopy[1] + triangleSideCopy[2] >= triangleSideCopy[0])
                    && (triangleSideCopy[2] + triangleSideCopy[0] >= triangleSideCopy[1])) {
                // Now that we know that the triangle is valid, lets analyze it.
                // Determine if all three sides are the same.
                if ((triangleSideCopy[0] == triangleSideCopy[1]) && (triangleSideCopy[1] == triangleSideCopy[2])) {
                    triangleType = TriangleTypeEnum.EQUILATERAL;
                }
                // Now let's determine if two of the sides are the same.
                if ((triangleSideCopy[0] == triangleSideCopy[1] && triangleSideCopy[1] != triangleSideCopy[2]) ||
                        (triangleSideCopy[1] == triangleSideCopy[2] && triangleSideCopy[0] != triangleSideCopy[1])) {
                    triangleType = TriangleTypeEnum.ISOSCELES;
                }

                // Now let's determine if all three sides are different.
                if ((triangleSideCopy[0] != triangleSideCopy[1]) && (triangleSideCopy[1] != triangleSideCopy[2])
                        && (triangleSideCopy[0] != triangleSideCopy[2])) {
                    triangleType = TriangleTypeEnum.SCALENE;
                }

                // Now determine if it is a right triangle and adjust accordingly if it is.  The answer should be correct to at least 2 decimal places.
                if (Math.abs(Math.sqrt(triangleSideCopy[0] *triangleSideCopy[0] + triangleSideCopy[1] * triangleSideCopy[1]) - triangleSideCopy[2] ) == 0)
                {
                    this.rightTriangle=true;
                    if (triangleType==TriangleTypeEnum.ISOSCELES) {triangleType= TriangleTypeEnum.RIGHT_ISOSCELES;}
                    if (triangleType==TriangleTypeEnum.SCALENE){triangleType=TriangleTypeEnum.RIGHT_SCALENE;}
                }
            } else {
                triangleType = TriangleTypeEnum.INVALID;
            }
        }
    }

    /**
     * This method will indicate if the triangle is valid.
     * @return The return will be true if the triangle is valid.
     */
    public boolean isTriangleValidlyDefined() {
        return triangleType!=TriangleTypeEnum.INVALID;
    }

    /**
     * This method will return whether nthe triangle is a right triangle or not.
     * @return True if the triangle is a right triangle.  False if not.
     */
    public boolean isRightTriangle()
    {
        return this.rightTriangle;
    }

    /**
     * This method will return the enumeration definition for the given triangle.
     * @return The triangle type will be returned.
     */
    public TriangleTypeEnum getTriangleType()
    {
        return triangleType;
    }

    /**
     * This method will return the 3 sides of the triangle.
     * @return The three sides will be returned as an array of doubles.  The sides should be in the entered order.
     */
    public double[] getTriangleSides() {
        return triangleSides;
    }

    /**
     * This method will return the definition of the sides of the given triangle.
     * @return The return is a copy of the string representing the triangle sides.
     */
    public String getTriangleDefinitionString() {
        return new String(triangleDefinitionString);
    }

    /**
     * This method will return the comments related to the triangle definition.
     *
     * @return The return will be the triangle comments.
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method will return the perimeter of the triangle if it is valid.  If it is not value, it will return -1.
     * @return The return will be the perimeter of the triangle.  If the triangle is not valid, the return will be -1.
     */
    public double obtainPerimeter() {
        double retVal = -1;

        if ((triangleType != TriangleTypeEnum.INVALID) && (invalidSyntax == false)) {
            retVal = getTriangleSides()[0] + getTriangleSides()[1] + getTriangleSides()[2];
        }
        return retVal;
    }

    /**
     * This method will return an analysis string.  If the syntax entered is valid, it will include the definitions of the triangle,
     * whether it is valid and , if valid, the type of triangle it is.  Additionally, if valid, it
     * will return the perimeter.  If there was a syntax error in the line, it will indicate that there was a syntax error and return the raw entered line.
     * @return The return will be a string representing the validity of the triangle.
     */

    public String obtainAnalysisString() {
        StringBuffer retString = new StringBuffer("");

        if (!invalidSyntax) {
            retString.append("The definition ");
            retString.append(this.getTriangleDefinitionString());

            if (triangleType != TriangleTypeEnum.INVALID) {
                retString.append(" is a valid");

                switch (this.triangleType)
                {
                    case EQUILATERAL:
                        retString.append(", equilateral");
                        break;
                    case ISOSCELES:
                        retString.append(", isosceles");
                    case SCALENE:
                        retString.append(", scalene");
                        break;
                }
                retString.append(" triangle with sides of length ");
                if (allIntegerSides=true) {
                    retString.append(String.format("%d", (int) this.getTriangleSides()[0]));
                    retString.append(" ");
                    retString.append(String.format("%d", (int) this.getTriangleSides()[1]));
                    retString.append(" ");
                    retString.append(String.format("%d", (int) this.getTriangleSides()[2]));
                    retString.append(". The perimeter is " + String.format("%d", (int)obtainPerimeter() )+ ".");
                }
                else {
                    retString.append(String.format("%.2f", this.getTriangleSides()[0]));
                    retString.append(" ");
                    retString.append(String.format("%.2f", this.getTriangleSides()[1]));
                    retString.append(" ");
                    retString.append(String.format("%.2f", this.getTriangleSides()[2]));
                    retString.append(". The perimeter is " + String.format("%.2f", obtainPerimeter() )+ ".");
                }

            } else {
                retString.append(" is not a valid triangle.");
            }
        } else {
            retString.append("SYNTAX ERROR IN LINE:\t");
            retString.append(textLine);
        }
        return retString.toString();
    }
}
