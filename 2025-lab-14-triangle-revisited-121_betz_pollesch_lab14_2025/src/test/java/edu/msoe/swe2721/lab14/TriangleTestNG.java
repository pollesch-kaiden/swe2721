/*
 * Course: SWE2721 121
 * Spring 2025
 * Triangle Revisited
 * Name: Kaiden Pollesch
 * Created 5/5/2025
 */
package edu.msoe.swe2721.lab14;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Scanner;

/**
 * Course: SWE2721 121
 * Spring 2025
 * Class TriangleTestNG Purpose: Test the revisited triangle classes
 *
 * @author polleschk
 * SWE2721 121 Laboratory Assignment
 * @version created on 5/5/2025 4:14 PM
 */

public class TriangleTestNG {

    private Triangle triangle;

    @BeforeMethod
    public void setUp() {
        triangle = null; // Initialize or reset the triangle before each test
        System.out.println("Setting up test environment...");
    }

    @AfterMethod
    public void tearDown() {
        triangle = null; // Clean up the triangle after each test
        System.out.println("Cleaning up test environment...");
    }

    @Test(groups = {"all", "student"})
    public void testTriangleConstructionValid() throws TriangleConstructionException {
        triangle = new Triangle(3, 4, 5);
        Assert.assertTrue(triangle.determineIfValid());
        Assert.assertEquals(triangle.determineTriangleType(), TriangleType.SCALENE);
        Assert.assertEquals(triangle.calculatePerimeter(), 12.0);
        Assert.assertEquals(triangle.calculateArea(), 6.0, 0.0001);
    }

    @Test(groups = {"all", "student"}, expectedExceptions = TriangleConstructionException.class)
    public void testTriangleConstructionInvalid() throws TriangleConstructionException {
        triangle = new Triangle(-1, 4, 5);
    }

    @Test(groups = {"all", "student"})
    public void testTriangleTypeEquilateral() throws TriangleConstructionException {
        triangle = new Triangle(3, 3, 3);
        Assert.assertEquals(triangle.determineTriangleType(), TriangleType.EQUILATERAL);
    }

    @Test(groups = {"all", "student"})
    public void testTriangleTypeIsosceles() throws TriangleConstructionException {
        triangle = new Triangle(3, 3, 5);
        Assert.assertEquals(triangle.determineTriangleType(), TriangleType.ISOSCELES);
    }

    @Test(groups = {"all", "student"})
    public void testTriangleTypeInvalid() throws TriangleConstructionException {
        triangle = new Triangle(1, 2, 10);
        Assert.assertEquals(triangle.determineTriangleType(), TriangleType.INVALID);
    }

    @Test(groups = {"all", "student"})
    public void testTriangleTextualRepresentation() throws TriangleConstructionException {
        triangle = new Triangle(3, 4, 5);
        Assert.assertEquals(triangle.obtainTextualRepresentation(), "a=3.000 b=4.000 c=5.000 area=6.000 SCALENE");
    }

    @Test(groups = {"all", "student"})
    public void testNumericParserValid() throws NumericParseException {
        Assert.assertEquals(NumericParser.parseString("one"), 1);
        Assert.assertEquals(NumericParser.parseString("  TWO  "), 2);
        Assert.assertEquals(NumericParser.parseString("Ten"), 10);
    }

    @Test(groups = {"all", "student"}, expectedExceptions = NumericParseException.class)
    public void testNumericParserInvalid() throws NumericParseException {
        NumericParser.parseString("eleven");
    }

    @Test(groups = {"all", "student"}, expectedExceptions = NumericParseException.class)
    public void testNumericParserNull() throws NumericParseException {
        NumericParser.parseString(null);
    }

    @Test(groups = {"all", "student"})
    public void testTriangleFactoryValidInput() {
        TriangleFactory factory = new TriangleFactory();
        String input = "3 4 5\n6 8 10\nquit";
        Scanner scanner = new Scanner(input);
        factory.parseStream(scanner);

        Assert.assertEquals(factory.getCount(), 2);
        Assert.assertTrue(factory.obtainTriangleText().contains("a=3.000 b=4.000 c=5.000 area=6.000 SCALENE"));
        Assert.assertTrue(factory.obtainTriangleText().contains("a=6.000 b=8.000 c=10.000 area=24.000 SCALENE"));
        Assert.assertEquals(factory.obtainTotalArea(), 30.0, 0.001);
    }

    @Test(groups = {"all", "student"})
    public void testTriangleFactoryInvalidInput() {
        TriangleFactory factory = new TriangleFactory();
        String input = "1 2 10\n-1 4 5\nquit";
        Scanner scanner = new Scanner(input);
        factory.parseStream(scanner);

        Assert.assertEquals(factory.getCount(), 0);
        Assert.assertEquals(factory.obtainTotalArea(), 0.0, 0.001);
    }

    @Test(groups = {"all", "student"})
    public void testMainWithFileNotFound() {
        String[] args = {"nonexistentfile.txt"};
        Main.main(args);
        // Verify that the error message is printed (manual verification or use a logging framework for assertions)
    }

    @Test(groups = {"all", "student"})
    public void testMainWithValidInput() {
        String input = "3 4 5\nquit";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        // Verify the output (manual verification or use a logging framework for assertions)
    }
}