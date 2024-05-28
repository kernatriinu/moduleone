package main.java.cryptology.gui;

import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.cryptanalysis.BruteForceAnalyzer;
import main.java.cryptology.util.CharacterUtils;
import main.java.cryptology.util.ConfigLoader;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static main.java.cryptology.cryptanalysis.BruteForceAnalyzer.createValidTextPredicate;

public class CryptoGUI extends JFrame {
    private final JTextField txtInput;
    private final JTextField txtOutput;
    private final JTextField txtShift;
    private final JComboBox<String> languageSelector;
    private final static String[] LANGUAGES = {"English", "Spanish"};

    public CryptoGUI() {
        setTitle("Caesar Cipher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        txtInput = new JTextField();
        txtOutput = new JTextField();
        txtShift = new JTextField();
        languageSelector = new JComboBox<>(LANGUAGES);
        JButton btnEncrypt = new JButton("Encrypt");
        JButton btnDecrypt = new JButton("Decrypt");
        JButton btnBruteForce = new JButton("Brute force decrypt");

        add(new JLabel("Input text:"));
        add(txtInput);
        add(new JLabel("Shift amount:"));
        add(txtShift);
        add(new JLabel("Select language:"));
        add(languageSelector);
        add(new JLabel("Output text:"));
        add(txtOutput);
        add(btnEncrypt);
        add(btnDecrypt);
        add(btnBruteForce);

        btnEncrypt.addActionListener(e -> {
            var input = txtInput.getText();
            if (CharacterUtils.isLettersAndSpaces(input)) {
                int shift = Integer.parseInt(txtShift.getText());
                var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
                txtOutput.setText(CaesarCipher.encrypt(input, shift, alphabet));
            } else {
                JOptionPane.showMessageDialog(this, "Input contains invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDecrypt.addActionListener(e -> {
            var input = txtInput.getText();
            int shift = Integer.parseInt(txtShift.getText());
            var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
            txtOutput.setText(CaesarCipher.decrypt(input, shift, alphabet));
        });

        btnBruteForce.addActionListener(e -> {
            var input = txtInput.getText();
            var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
            var commonWords = ConfigLoader.loadCommonWords(languageSelector.getSelectedItem().toString().toLowerCase());

            var isValidText = createValidTextPredicate(commonWords);

            var result = BruteForceAnalyzer.decrypt(input, isValidText, alphabet);
            txtOutput.setText(result);
        });

        setVisible(true);
    }
}
