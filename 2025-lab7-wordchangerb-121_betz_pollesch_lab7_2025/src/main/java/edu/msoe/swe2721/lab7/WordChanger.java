package edu.msoe.swe2721.lab7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class will map words to their given synonyms.  The words will be mapped using a hashmap.
 *
 * @author wws
 */
public class WordChanger {
    // Note: The following attribute is set to allow access from within the same
    // package. This can be used for testing.  You normally would make this be private.
    protected Map<String, String> synonymList;

    /**
     * This is the default constructor which will create a new Hasmap that can be used for testing.
     */
    public WordChanger() {
        synonymList = new HashMap<String, String>();
    }

    /**
     * This is a constructor which will instantiate a new instance and cause the given synonymn list to be used.
     * @param synonymnList This is the map of synonyms to be used.
     * @throws WCException An exception will be thrown if the parameter is null.
     */
    public WordChanger(Map<String, String> synonymnList) throws WCException
    {
        if (synonymnList==null)
        {
            throw new WCException();
        }
        this.synonymList = synonymnList;
    }

    /**
     * This method will add a new synonym pair into the listing of synonym's.
     * The pair must be unique.
     *
     * @param word    This is the word that is to map to a given synonym.
     * @param synonym This is a different word that has the same meaning as the
     *                word.
     * @return This method will return true if the word can be added as a pair
     * and false otherwise.
     * @throws WCException An exception will be thrown if the word contains any whitespace or
     *                     is not at least two characters in length. An exception also
     *                     will be thrown if the synonym length is not at least 2
     *                     characters or has whitespace.
     */
    public boolean addSynonymPair(String word, String synonym) throws WCException {
        boolean retVal = false;

        // Determine if the word has a space. If so, throw an exception.
        if (!determineIfStringisSpaceFree(word)) {
            throw new WCException("Words to be added can not have any whitespace.");
        } else if (!determineIfStringisSpaceFree(synonym)) {
            throw new WCException("Synonym to be added can not have any whitespace.");
        } else if (word.length() == 0) {
            throw new WCException("Words to be added must be two or more characters in length.");
        } else if (word.length() <= 1) {
            throw new WCException("Words to be added must be two or more characters in length.");
        } else if (synonym.length() <= 1) {
            throw new WCException("The length of the synonym must be at least 2 characters");
        } else {
            // Convert the words to lower case.
            word = toLowerCase(word);
            synonym = toLowerCase(synonym);

            // If the list is empty, simply add the item.
            if (synonymList.isEmpty()) {
                synonymList.put(word, synonym);
                retVal = true;
            } else {
                // We need to do some additional checking.
                // Check to make sure the word does not exist.
                if (!synonymList.containsKey(word)) {
                    // Add the pair.
                    synonymList.put(word, synonym);
                    retVal = true;
                }
            }
        }
        return retVal;
    }

    /**
     * This method will find a synonym for the given word.
     *
     * @param word This is the word that is to be used to locate a synonym.
     * @return A synonym for the word will be returned. If there is no synonym,
     * then null will be returned.
     * @throws WCException An exception will be thrown if the length of the word is less
     *                     than two characters, the word to be searched is not a
     *                     singular word, or a non-alphabetic character is found in the
     *                     word.
     */
    public String findSynonym(String word) throws WCException {
        String retVal = null;

        // If the word contains whitespace, throw an exception.
        if (word.matches(".*\\s.*")) {
            throw new WCException("The word can not contain whitespace.");
        }

        // Make sure the word does not have any non-alpha characters in it.
		int i = 0;
        do {    
			if (Character.isLetter(word.charAt(i)) == false) {
                throw new WCException("Non-Alpha character found in the word.");
            }
			++i;
        } while (i < word.length());

        // If the word is not a word, throw an exception.http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=1163
        if (word.length() < 2) {
            throw new WCException("There must be at least 2 characters in the word.");
        }

        // Now lets search for the word. Note that the following is not ideal by
        // any means.
        // Obtain the keys that are part of this list.
        Set<Map.Entry<String, String>> s = this.synonymList.entrySet();

        // Now determine if the key is part of the list.
        Iterator<Map.Entry<String, String>> iter = s.iterator();

        // Find the word in the map if it is there.
        while ((iter.hasNext())) {
            Map.Entry<String, String> pair = iter.next();

            if (pair.getKey().equalsIgnoreCase(word)) {
                // We have a match
                retVal = pair.getValue();
            }
        }
        return retVal;
    }

    /**
     * This method will convert a string to lower case.
     *
     * @param word This is the word that is to be converted to lower case.
     * @return A string will be returned in lower case representing the word.  If word is null, null will be returned.
     */
    protected String toLowerCase(String word) {
        String retVal = null;
        if (word != null) {
            StringBuilder newWord = new StringBuilder(word);

            int index = 0;
            for (index = 0; index < newWord.length(); index++) {
                if (Character.isUpperCase(newWord.charAt(index))) {
                    // We need to replace the character with a lower case version.
                    newWord.setCharAt(index, Character.toLowerCase(newWord.charAt(index)));
                }
            }
            retVal = newWord.toString();
        }
        return retVal;
    }

    /**
     * This method will determine if a string lacks any whitespace within it.
     *
     * @param text This is the text that is to be checked for whitespace.
     * @return If there is no whitespace in the string, the return will be true.  Otherwise, it will be false.  If the string is null it also will be false.
     */
    protected boolean determineIfStringisSpaceFree(String text) {
        boolean retVal;
        if (text == null) {
            retVal = false;
        } else {
            StringBuilder word = new StringBuilder(text);
            retVal = true;

            int index = word.length() - 1;

            while (index >= 0 && retVal) {
                if (Character.isWhitespace(word.charAt(index))) {
                    // We have found a whitespace within the string.
                    retVal = false;
                }
                index--;
            }

        }
        return retVal;
    }
}

