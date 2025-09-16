/*
 * Course: SWE2721-121
 * Spring 2025
 * Lab 3 - Testing NumericStringConverter
 * Name: Kaiden Pollesch
 * Created: 2/11/2025
 */
package msoe.swe2721.lab3;

import java.security.InvalidParameterException;

/**
 * This class is a class that will convert numbers representations using digits into textual strings.  It will also allow the reverse traslation of words into digits.
 */
public class NumericStringConverterPartA {
    private int digitCount = 0;
    public String convertNumbersToText(String text){
        if (text == null) {
            throw new InvalidParameterException();
        }
        int length = text.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = text.charAt(i);
            if (Character.isDigit(ch)) {
                digitCount++;
                result.append(translator(ch));
//            This is the code to fix the problem with the decimal point
//            } else if (ch == '.' && i != length - 1 && !(i >= 2 && text.substring(i - 2, i).equals("Dr")) && (i == length - 1 || text.charAt(i + 1) != ' ')) {
//                result.append(translator(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    /**
     * This method will translate a single character into its string representation.
     * @param c The character to be translated.
     * @return The string representation of the character.
     */
    private String translator(char c) {
        return switch (c) {
            case '0' -> "cero";
            case '1' -> "uno";
            case '2' -> "dos";
            case '3' -> "tres";
            case '4' -> "cuatro";
            case '5' -> "cinco";
            case '6' -> "seis";
            case '7' -> "siete";
            case '8' -> "ocho";
            case '9' -> "nueve";
            case '.' -> "punto";
            default -> "";
        };
    }

    /**
     * This method will return the number of digits that were converted.
     * @return The number of digits that were converted.
     */
    public int getDigitCount() {
        return digitCount;
    }
}
