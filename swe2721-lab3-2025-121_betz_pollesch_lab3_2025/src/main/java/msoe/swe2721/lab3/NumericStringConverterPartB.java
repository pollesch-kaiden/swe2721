package msoe.swe2721.lab3;

import java.security.InvalidParameterException;

/**
 * This class is a class that will convert text of numbers into digits.
 */
public class NumericStringConverterPartB {
    private int digitCount = 0;

    // Replaces text for a number into the digit and updates digit counter
    public String convertTextToNumbers(String text) {
        if (text == null) {
            throw new InvalidParameterException();
        }
        if (text.contains("cero")) {
            text = text.replace("cero", "0");
            digitCount--;
        }
        if (text.contains("uno")) {
            text = text.replace("uno", "1");
            digitCount--;
        }
        if (text.contains("dos")) {
            text = text.replace("dos", "2");
            digitCount--;
        }
        if (text.contains("tres")) {
            text = text.replace("tres", "3");
            digitCount--;
        }
        if (text.contains("cuatro")) {
            text = text.replace("cuatro", "4");
            digitCount--;
        }
        if (text.contains("cinco")) {
            text = text.replace("cinco", "5");
            digitCount--;
        }
        if (text.contains("seis")) {
            text = text.replace("seis", "6");
            digitCount--;
        }
        if (text.contains("siete")) {
            text = text.replace("siete", "7");
            digitCount--;
        }
        if (text.contains("ocho")) {
            text = text.replace("ocho", "8");
            digitCount--;
        }
        if (text.contains("nueve")) {
            text = text.replace("nueve", "9");
            digitCount--;
        }
        if (text.contains("punto")) {
            text = text.replace("punto", ".");
        }

        return text;
    }
    // Returns the value of digit counter
    public int getDigitCount() {
        return digitCount;
    }
}
