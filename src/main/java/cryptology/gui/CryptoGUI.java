package main.java.cryptology.gui;

import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.cipher.Cipher;
import main.java.cryptology.cryptanalysis.BruteForce;
import main.java.cryptology.cryptanalysis.StatisticalAnalyzer;
import main.java.cryptology.util.CharacterUtils;
import main.java.cryptology.util.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CryptoGUI extends JFrame {
    private JTextField txtInput, txtOutput, txtShift;
    private JComboBox<String> languageSelector;
    private JButton btnEncrypt, btnDecrypt, btnBruteForce, btnStatisticalAnalysis;
    private static final String[] LANGUAGES = {"English", "Spanish"};

    public CryptoGUI() {
        setTitle("Caesar Cipher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        initializeComponents();
        setupListeners();
        loadInitialSettings();

        setVisible(true);
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        txtInput = new JTextField("Enter text here...");
        txtOutput = new JTextField("Output will appear here...");
        txtOutput.setEditable(false);
        txtShift = new JTextField("0");
        languageSelector = new JComboBox<>(LANGUAGES);

        btnEncrypt = new JButton("Encrypt");
        btnDecrypt = new JButton("Decrypt");
        btnBruteForce = new JButton("Brute force decrypt");
        btnStatisticalAnalysis = new JButton("Statistical analysis");

        addComponent(gbc, "Input text:", txtInput);
        addComponent(gbc, "Shift amount:", txtShift);
        addComponent(gbc, "Select language:", languageSelector);
        addComponent(gbc, "Output text:", txtOutput);
        add(btnEncrypt, gbc);
        add(btnDecrypt, gbc);
        add(btnBruteForce, gbc);
        add(btnStatisticalAnalysis, gbc);
    }

    private void addComponent(GridBagConstraints gbc, String label, Component comp) {
        add(new JLabel(label), gbc);
        add(comp, gbc);
    }

    private void setupListeners() {
        languageSelector.addActionListener(e -> loadCharacterSet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase()));
        btnEncrypt.addActionListener(e -> performEncryption());
        btnDecrypt.addActionListener(e -> performDecryption());
        btnBruteForce.addActionListener(e -> performBruteForce());
        btnStatisticalAnalysis.addActionListener(e -> performStatisticalAnalysis());
    }

    private void performEncryption() {
        String input = txtInput.getText();
        String alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
        int shift = Integer.parseInt(txtShift.getText());
        if (CharacterUtils.isAllowedCharacters(input)) {
            Cipher cipher = new CaesarCipher(shift, alphabet);
            txtOutput.setText(cipher.encrypt(input));
        } else {
            JOptionPane.showMessageDialog(this, "Input contains invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performDecryption() {
        var input = txtInput.getText();
        var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
        int shift = Integer.parseInt(txtShift.getText());
        var cipher = new CaesarCipher(shift, alphabet);
        txtOutput.setText(cipher.decrypt(input));
    }

    private void performBruteForce() {
        String input = txtInput.getText();
        String alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
        txtOutput.setText("Brute force decryption started, check console for results.");
        assert alphabet != null;
        BruteForce.bruteForceDecrypt(input, alphabet);
    }

    private void performStatisticalAnalysis() {
        String input = txtInput.getText();
        String alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());
        String language = (String) languageSelector.getSelectedItem();

        if (alphabet != null && language != null) {
            StatisticalAnalyzer analyzer = new StatisticalAnalyzer(language.toLowerCase());
            String analysisResult = analyzer.analyze(input, alphabet);
            txtOutput.setText(analysisResult);
            System.out.println("Statistical Analysis Complete: " + analysisResult);
        } else {
            txtOutput.setText("Error: Could not load resources for analysis.");
        }
    }

    private void loadInitialSettings() {
        loadCharacterSet("english");
    }

    private void loadCharacterSet(String language) {
        var characters = ConfigLoader.loadAlphabet(language);
        if (characters != null) {
            CharacterUtils.loadAllowedCharacters(characters);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load character set for " + language, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
