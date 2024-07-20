package main.java.cryptology.cryptanalysis;


import main.java.cryptology.cipher.CaesarCipher;

public class BruteForce {

    public static void bruteForceDecrypt(String cipherText, String alphabet) {
        int alphabetLength = alphabet.length();
        System.out.println("Starting brute force decryption...");

        for (int shift = 0; shift < alphabetLength; shift++) {
            var cipher = new CaesarCipher(shift, alphabet);
            var decryptedText = cipher.decrypt(cipherText);
            System.out.println("Shift " + shift + ": " + decryptedText);
        }
    }
}
