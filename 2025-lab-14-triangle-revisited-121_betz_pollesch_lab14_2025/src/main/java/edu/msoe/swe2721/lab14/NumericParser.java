package edu.msoe.swe2721.lab14;

/**
 * This class is responsible for parsing strings to identify a number within the text.
 */
public class NumericParser {

    /**
     * This method will convert the English textual representations oif the numbers zero through ten into integer values.
     * @param s This is the parameter.  It can be in any case, but must match the English spellings for the numbers one through ten.
     *          Whitespace can be present before and after the text.
     * @return The return will be a number with an integer value of between 0 and 10.
     * @throws NumericParseException This exception will be thrown if the value to be parsed is not a valid string representation of an
     * English number between 0 and 10.  Also, will be thrown if a null parameter is passed in.
     */
    public static int parseString(String s) throws NumericParseException {
        if (s == null) {
            throw new NumericParseException("Input string cannot be null");
        }

        String trimmed = s.trim().toLowerCase();

        switch (trimmed) {
            case "zero":  return 0;
            case "one":   return 1;
            case "two":   return 2;
            case "three": return 3;
            case "four":  return 4;
            case "five":  return 5;
            case "six":   return 6;
            case "seven": return 7;
            case "eight": return 8;
            case "nine":  return 9;
            case "ten":   return 10;
            default:
                throw new NumericParseException("Invalid number string: " + s);
        }
    }
}
