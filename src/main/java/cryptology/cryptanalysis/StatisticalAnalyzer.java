package main.java.cryptology.cryptanalysis;

import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.cipher.Cipher;

import java.util.HashMap;
import java.util.Map;

public class StatisticalAnalyzer {

    public static Map<Character, Double> calculateFrequency(String text, String alphabet) {
        Map<Character, Integer> counts = new HashMap<>();
        text = text.toUpperCase();
        int totalChars = 0;

        for (char c : text.toCharArray()) {
            if (alphabet.indexOf(c) >= 0) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
                totalChars++;
            }
        }

        Map<Character, Double> frequencies = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            frequencies.put(entry.getKey(), entry.getValue() / (double) totalChars);
        }

        return frequencies;
    }

    public static String analyzeByFrequency(String cipherText, String referenceText, String alphabet) {
        Map<Character, Double> cipherFrequencies = calculateFrequency(cipherText, alphabet);
        Map<Character, Double> referenceFrequencies = calculateFrequency(referenceText, alphabet);

        int bestShift = findBestShift(cipherFrequencies, referenceFrequencies, alphabet);
        Cipher cipher = new CaesarCipher(bestShift, alphabet);

        return cipher.decrypt(cipherText);
    }

    private static int findBestShift(Map<Character, Double> cipherFreq, Map<Character, Double> refFreq, String alphabet) {
        double minDifference = Double.MAX_VALUE;
        int bestShift = 0;

        for (int shift = 0; shift < alphabet.length(); shift++) {
            double difference = 0;
            for (char ch : refFreq.keySet()) {
                char shiftedChar = alphabet.charAt((alphabet.indexOf(ch) + shift) % alphabet.length());
                double freqDifference = refFreq.get(ch) - cipherFreq.getOrDefault(shiftedChar, 0.0);
                difference += Math.pow(freqDifference, 2);
            }

            if (difference < minDifference) {
                minDifference = difference;
                bestShift = shift;
            }
        }

        return bestShift;
    }
}
