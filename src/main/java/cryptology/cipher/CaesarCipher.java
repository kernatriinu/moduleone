package main.java.cryptology.cipher;

public class CaesarCipher {
    public static String encrypt(String text, int shift, String alphabet) {
        return shiftText(text, shift, alphabet);
    }

    public static String decrypt(String text, int shift, String alphabet) {
        return shiftText(text, -shift, alphabet);
    }

    private static String shiftText(String text, int shift, String alphabet) {
        StringBuilder result = new StringBuilder();
        int alphabetSize = alphabet.length();
        text = text.toUpperCase();

        for (char character : text.toCharArray()) {
            int index = alphabet.indexOf(character);
            if (index != -1) {
                int shiftedIndex = (index + shift) % alphabetSize;
                if (shiftedIndex < 0) {
                    shiftedIndex += alphabetSize;
                }
                result.append(alphabet.charAt(shiftedIndex));
            } else {
                // Append character unchanged if it's not found in the alphabet
                result.append(character);
            }
        }
        return result.toString();
    }
}
