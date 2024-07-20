package main.java.cryptology.gui;

import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.cryptanalysis.BruteForceAnalyzer;
import main.java.cryptology.util.CharacterUtils;
import main.java.cryptology.util.ConfigLoader;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CryptoGUI extends JFrame {
    private JTextField txtInput;
    private JTextField txtOutput;
    private JTextField txtShift;
    private JComboBox<String> languageSelector;
    private JButton btnEncrypt;
    private JButton btnDecrypt;
    private JButton btnBruteForce;
    private final static String[] LANGUAGES = {"English", "Spanish"};

    public CryptoGUI() {
        setTitle("Caesar Cipher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        initializeComponents();
        setupListeners();
        loadInitialSettings();

        setVisible(true);
    }

    private void initializeComponents() {
        txtInput = new JTextField();
        txtOutput = new JTextField();
        txtShift = new JTextField();
        languageSelector = new JComboBox<>(LANGUAGES);
        btnEncrypt = new JButton("Encrypt");
        btnDecrypt = new JButton("Decrypt");
        btnBruteForce = new JButton("Brute force decrypt");

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
    }

    private void setupListeners() {
        languageSelector.addActionListener(e -> {
            var selectedLanguage = Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase();
            loadCharacterSet(selectedLanguage);
        });

        btnEncrypt.addActionListener(e -> {
            var input = txtInput.getText();
            var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
            int shift = Integer.parseInt(txtShift.getText());
            if (CharacterUtils.isAllowedCharacters(input)) {
                CaesarCipher cipher = new CaesarCipher(shift, alphabet);
                txtOutput.setText(cipher.encrypt(input));
            } else {
                JOptionPane.showMessageDialog(this, "Input contains invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDecrypt.addActionListener(e -> {
            var input = txtInput.getText();
            var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
            int shift = Integer.parseInt(txtShift.getText());
            CaesarCipher cipher = new CaesarCipher(shift, alphabet);
            txtOutput.setText(cipher.decrypt(input));
        });

        btnBruteForce.addActionListener(e -> {
            var input = txtInput.getText();
            var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
            var commonWords = ConfigLoader.loadCommonWords(languageSelector.getSelectedItem().toString().toLowerCase());

            var isValidText = BruteForceAnalyzer.createValidTextPredicate(commonWords);
            assert alphabet != null;
            var result = BruteForceAnalyzer.decrypt(input, isValidText, alphabet);
            txtOutput.setText(result);
        });
    }

    private void loadInitialSettings() {
        // Load the default alphabet and allowed characters
        var defaultLanguage = "english";
        loadCharacterSet(defaultLanguage);
    }

    private void loadCharacterSet(String language) {
        var characters = ConfigLoader.loadAlphabet(language);
        if (characters != null) {
            CharacterUtils.loadAllowedCharacters(characters);
            System.out.println("CharacterSet loaded for " + language);  // Debug statement
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load character set for " + language, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
