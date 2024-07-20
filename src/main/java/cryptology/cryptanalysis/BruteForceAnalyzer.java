package main.java.cryptology.cryptanalysis;


import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.util.ConfigLoader;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

public class BruteForceAnalyzer {
    private static final Set<String> commonWords = ConfigLoader.loadCommonWords("english");

    public static String decrypt(String cipherText, Predicate<String> isValidText, String alphabet) {
        int alphabetLength = alphabet.length();
        System.out.println("Starting brute force decryption...");
        for (int shift = 0; shift < alphabetLength; shift++) {
            CaesarCipher cipher = new CaesarCipher(shift, alphabet);
            var decryptedText = cipher.decrypt(cipherText);
            System.out.println("Shift " + shift + ": " + decryptedText + " | Testing validity...");
            String cleanedText = decryptedText.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();

            if (isValidText.test(cleanedText)) {
                System.out.println("Valid decryption found at shift " + shift + ": " + decryptedText);

                return decryptedText;
            }
        }
        System.out.println("No valid decryption found.");

        return "No valid decryption found.";
    }

    public static Predicate<String> createValidTextPredicate(Set<String> commonWords) {
        return text -> {
            String cleanedText = text.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
            String[] words = cleanedText.split("\\s+");

            return Arrays.stream(words).anyMatch(commonWords::contains);
        };
    }
}
