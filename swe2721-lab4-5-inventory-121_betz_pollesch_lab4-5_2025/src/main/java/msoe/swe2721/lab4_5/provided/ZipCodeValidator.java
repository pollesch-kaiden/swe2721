package msoe.swe2721.lab4_5.provided;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class will validate whether a zip codee is valid or not.  The data inside of this class is obtained from
 * @see <a href="https://simplemaps.com/data/us-zips">...</a>
 */
public class ZipCodeValidator {

    /**
     * This is the singleton instance of this class.
     */
    private static ZipCodeValidator singleton=null;

    /**
     * This method will return a singleton representing the class.
     * @return The singleton instance to be used will be returned.
     */
    public static ZipCodeValidator getSingleton()
    {
        if (singleton==null)
        {
            singleton = new ZipCodeValidator();
        }
        return singleton;
    }

    /**
     * This is the hashMap which will map zip codes to states.
     */
    private final HashMap<Integer, String> map = new HashMap<>();

    /**
     * This method will return the state abbreviation for the given zip code.
     * @param zipcode This is the zipcode that will be checked.
     * @return The return will be the string representing the state or null if the zipcode is not valid.
     */
    public String getStateByZip(int zipcode)
    {
        return map.get(zipcode);
    }

    /**
     * This method will validate the given zip code.
     * @param zip This is the zip code that is to be returned.
     * @return The return will be true if the zip code is valid and false otherwise.
     */
    public boolean isValidZipcode(int zip)
    {
        String state = map.get(zip);
        return state != null;
    }

    /**
     * This is the constructor that will create an instance of this class.
     */
    private ZipCodeValidator()  {
        URL resource = getClass().getClassLoader().getResource("zipcodes.dat");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                File f = new File(resource.toURI());

                // Now read the file in and add all the zip codes in.
                Scanner s = new Scanner(f);
                while (s.hasNext())
                {
                    // Read the next line
                    String line = s.nextLine();
                    // Split the line
                    String[] res = line.split("[,]", 0);
                    int key = Integer.parseInt(res[0]);
                    map.put(key, res[1]);
                }
            } catch (URISyntaxException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
