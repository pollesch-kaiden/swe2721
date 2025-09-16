package edu.msoe.swe2721.lab9;

public class ConsoleCalculator {
    private boolean allIntegers;
    private double currentResult;

    public ConsoleCalculator() {
        // Constructor
        allIntegers = true;
        currentResult = 0.0;
    }

    private void handleAdd(double addend) {
        currentResult += addend;
    }

    private void handleClear() {
        currentResult = 0.0;
    }

    private void handleDivide(double dividend) {
        if (dividend == 0.0) {
            System.out.println("Error: Division by zero");
        } else {
            currentResult /= dividend;
        }
    }

    private void handleEquals() {
        printNumber(currentResult);
        printNewline();
    }

    private void handleMultiply(double multiplier) {
        currentResult *= multiplier;
    }

    private void handleSubtract(double subtracted) {
        currentResult -= subtracted;
    }

    private void printNewline() {
        System.out.println();
    }

    private void printNumber(double value) {
        if (allIntegers) {
            System.out.print((int) value);
        } else {
            System.out.print(value);
        }

    }

    private void printEquation(String equation, double result) {
        System.out.print(equation + " = ");
        printNumber(result);
        printNewline();
    }

    public void processLine(String lineOfText) {
        if (lineOfText == null || lineOfText.trim().isEmpty()) {
            return;
        }

        String[] tokens = lineOfText.trim().split("\\s+");
        if (tokens.length == 0) {
            return;
        }

        String firstToken = tokens[0].toUpperCase();
        if (firstToken.equals("EQUALS")) {
            handleEquals();
            return;
        }

        if (firstToken.equals("CLEAR")) {
            handleClear();
            return;
        }

        if (tokens.length == 3) {
            try {
                double firstNumber;
                if (tokens[0].equalsIgnoreCase("ans")) {
                    firstNumber = currentResult;
                } else {
                    firstNumber = Double.parseDouble(tokens[0]);
                }

                String operator = tokens[1];
                double secondNumber = Double.parseDouble(tokens[2]);

                String equation = (tokens[0].equalsIgnoreCase("ans") ?
                        String.valueOf(currentResult) : tokens[0]) +
                        " " + operator + " " + tokens[2];

                currentResult = firstNumber;

                switch (operator) {
                    case "+":
                        handleAdd(secondNumber);
                        break;
                    case "-":
                        handleSubtract(secondNumber);
                        break;
                    case "*":
                        handleMultiply(secondNumber);
                        break;
                    case "/":
                        handleDivide(secondNumber);
                        break;
                    default:
                        System.out.println("Error: Invalid operator");
                        return;
                }

                printEquation(equation, currentResult);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format");
                return;
            }
        }

        if (tokens.length >= 2) {
            try {
                double number = Double.parseDouble(tokens[1]);
                String equation = currentResult + " " +
                        (firstToken.equals("ADD") ? "+" :
                                firstToken.equals("SUBTRACT") ? "-" :
                                        firstToken.equals("MULTIPLY") ? "*" : "/") +
                        " " + tokens[1];

                switch (firstToken) {
                    case "ADD":
                        handleAdd(number);
                        printEquation(equation, currentResult);
                        break;
                    case "SUBTRACT":
                        handleSubtract(number);
                        printEquation(equation, currentResult);
                        break;
                    case "MULTIPLY":
                        handleMultiply(number);
                        printEquation(equation, currentResult);
                        break;
                    case "DIVIDE":
                        handleDivide(number);
                        printEquation(equation, currentResult);
                        break;
                    default:
                        System.out.println("Error: Invalid command");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format");
            }
        }
    }

    private void setAndPrintCurrentResult(double newResult) {
        currentResult = newResult;
        allIntegers = allIntegers && (newResult == (int)newResult);
        printNumber(currentResult);

    }
}