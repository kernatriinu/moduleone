package main.java.cryptology.cryptanalysis;

import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.util.ConfigLoader;
import main.java.cryptology.util.SingletonNotifier;

import java.util.Set;
import java.util.function.Predicate;

public class StatisticalAnalyzer {
    private Set<String> commonWords;

    public StatisticalAnalyzer(String language) {
        this.commonWords = ConfigLoader.loadCommonWords(language);
    }

    public String analyze(String cipherText, String alphabet) {
        var notifier = SingletonNotifier.getNotifier();
        int alphabetLength = alphabet.length();
        notifier.notifyInfo("Starting statistical analysis...");

        Predicate<String> isValidText = createValidTextPredicate(this.commonWords);

        for (int shift = 0; shift < alphabetLength; shift++) {
            var cipher = new CaesarCipher(shift, alphabet);
            var decryptedText = cipher.decrypt(cipherText);
            notifier.notifyInfo("Shift " + shift + ": " + decryptedText);

            if (isValidText.test(decryptedText)) {
                notifier.notifyInfo("Potential decryption found at shift " + shift + ": " + decryptedText);
                return decryptedText;
            }
        }

        notifier.notifyInfo("No valid decryption found.");
        return "No valid decryption found.";
    }

    private Predicate<String> createValidTextPredicate(Set<String> commonWords) {
        return text -> {
            String[] words = text.toLowerCase().split("\\s+");
            for (String word : words) {
                if (commonWords.contains(word)) {
                    return true;
                }
            }
            return false;
        };
    }
}
