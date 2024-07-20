package main.java.cryptology.util;
import java.util.HashSet;
import java.util.Set;

public class CharacterUtils {
    private static Set<Character> allowedCharacters = new HashSet<>();

    // Dynamically load allowed characters from a configuration
    public static void loadAllowedCharacters(String characters) {
        allowedCharacters.clear();
        for (char ch : characters.toUpperCase().toCharArray()) {
            allowedCharacters.add(ch);
        }
    }

    public static boolean isAllowedCharacters(String text) {
        return text.toUpperCase().chars().allMatch(ch -> allowedCharacters.contains((char) ch));
    }
}
