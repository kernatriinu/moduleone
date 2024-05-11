package main.java.cryptology.util;

public class CharacterUtils {
    /**
     * Checks if a string contains only letters and spaces.
     * @param text The text to check.
     * @return true if the text contains only letters and spaces, false otherwise.
     */
    public static boolean isLettersAndSpaces(String text) {
        return text.chars().allMatch(ch -> Character.isLetter(ch) || Character.isSpaceChar(ch));
    }
}
