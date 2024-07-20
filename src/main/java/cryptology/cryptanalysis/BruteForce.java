package main.java.cryptology.cryptanalysis;


import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.util.Notifier;
import main.java.cryptology.util.SingletonNotifier;

public class BruteForce {

    public static void bruteForceDecrypt(String cipherText, String alphabet) {
        Notifier notifier = SingletonNotifier.getNotifier();
        int alphabetLength = alphabet.length();
        notifier.notifyInfo("Starting brute force decryption...");

        for (int shift = 0; shift < alphabetLength; shift++) {
            var cipher = new CaesarCipher(shift, alphabet);
            var decryptedText = cipher.decrypt(cipherText);
            notifier.notifyInfo("Shift " + shift + ": " + decryptedText);
        }
    }
}
