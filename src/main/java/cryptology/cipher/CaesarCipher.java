package main.java.cryptology.cipher;

public class CaesarCipher {
    public static String encrypt(String text, int shift) {
        return shiftText(text, shift);
    }

    public static String decrypt(String text, int shift) {
        return shiftText(text, -shift);
    }

    private static String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            char shiftedChar = (char) (character + shift);
            result.append(shiftedChar);
        }

        return result.toString();
    }
}
