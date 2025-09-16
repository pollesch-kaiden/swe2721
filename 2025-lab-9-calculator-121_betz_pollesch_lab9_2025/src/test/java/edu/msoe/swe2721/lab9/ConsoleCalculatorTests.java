package edu.msoe.swe2721.lab9;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ConsoleCalculatorTests {
    private ConsoleCalculator calculator;
    private Class<?> clazz;

    // Reflective references for private fields/methods.
    private Field allIntegers;
    private Field currentResult;
    private Method handleAdd;
    private Method handleDivide;
    private Method handleEquals;
    private Method handleMultiply;
    private Method handleSubtract;
    private Method handleClear;
    private Method processLine;

    @BeforeMethod (groups = {"all", "student"})
    public void setUp() throws Exception {
        calculator = new ConsoleCalculator();
        clazz = calculator.getClass();

        // Get private fields
        allIntegers = clazz.getDeclaredField("allIntegers");
        allIntegers.setAccessible(true);
        currentResult = clazz.getDeclaredField("currentResult");
        currentResult.setAccessible(true);

        // Get private methods
        handleAdd = clazz.getDeclaredMethod("handleAdd", double.class);
        handleAdd.setAccessible(true);
        handleDivide = clazz.getDeclaredMethod("handleDivide", double.class);
        handleDivide.setAccessible(true);
        handleEquals = clazz.getDeclaredMethod("handleEquals");
        handleEquals.setAccessible(true);
        handleMultiply = clazz.getDeclaredMethod("handleMultiply", double.class);
        handleMultiply.setAccessible(true);
        handleSubtract = clazz.getDeclaredMethod("handleSubtract", double.class);
        handleSubtract.setAccessible(true);
        handleClear = clazz.getDeclaredMethod("handleClear");
        handleClear.setAccessible(true);

        // Public method
        processLine = clazz.getDeclaredMethod("processLine", String.class);
        processLine.setAccessible(true);
    }

    @Test (groups = {"all", "student"})
    public void testAdd() throws Exception {
        // Initial result should be 0.
        handleAdd.invoke(calculator, 5.0);
        handleEquals.invoke(calculator);
        assertEquals(currentResult.get(calculator), 5.0);
    }

    @Test (groups = {"all", "student"})
    public void testMultiply() throws Exception {
        handleAdd.invoke(calculator, 2.0);
        handleEquals.invoke(calculator);
        handleMultiply.invoke(calculator, 3.0);
        handleEquals.invoke(calculator);
        assertEquals(currentResult.get(calculator), 6.0);
    }

    @Test (groups = {"all", "student"})
    public void testClear() throws Exception {
        handleAdd.invoke(calculator, 10.0);
        handleEquals.invoke(calculator);
        handleClear.invoke(calculator);
        assertEquals(currentResult.get(calculator), 0.0);
    }

    @Test (groups = {"all", "student"})
    public void testProcessLineWithAdd() throws Exception {
        // Capture console output.
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        processLine.invoke(calculator, "add 5");
        processLine.invoke(calculator, "equals");

        // Restore console and do assertion.
        System.setOut(originalOut);
        assertTrue(baos.toString().contains("5"));

        // Also check internal state.
        assertEquals(currentResult.get(calculator), 5.0);
    }

    @AfterMethod (groups = {"all", "student"})
    public void cleanUp() {
        calculator = null;
    }
}