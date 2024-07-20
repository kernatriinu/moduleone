package main.java.cryptology.cipher;

public class CaesarCipher {
    private int shift;
    private String alphabet;

    public CaesarCipher(int shift, String alphabet) {
        this.shift = shift;
        this.alphabet = alphabet;
    }

    public String encrypt(String text) {
        return shiftText(text, shift);
    }

    public String decrypt(String text) {
        return shiftText(text, -shift);
    }

    private String shiftText(String text, int shift) {
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
