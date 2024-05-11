package main.java.cryptology.cryptanalysis;



import main.java.cryptology.cipher.CaesarCipher;

import java.util.function.Predicate;

public class BruteForceAnalyzer {
    public static String decrypt(String cipherText, Predicate<String> isValidText) {
        for (int shift = 1; shift < 65536; shift++) {
            String decryptedText = CaesarCipher.decrypt(cipherText, shift);
            if (isValidText.test(decryptedText)) {
                return decryptedText;
            }
        }

        return "No valid decryption found.";
    }
}
