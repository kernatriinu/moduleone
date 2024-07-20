package main.java.cryptology.gui;

import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.cipher.Cipher;
import main.java.cryptology.cryptanalysis.BruteForce;
import main.java.cryptology.util.CharacterUtils;
import main.java.cryptology.util.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CryptoGUI extends JFrame {
    private JTextField txtInput, txtOutput, txtShift;
    private JComboBox<String> languageSelector, methodSelector;
    private JButton btnEncrypt, btnDecrypt, btnAnalyze;
    private static final String[] LANGUAGES = {"English", "Spanish"};
    private static final String[] METHODS = {"Brute Force", "Statistical Analysis"};

    public CryptoGUI() {
        setTitle("Caesar Cipher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 450);
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
        methodSelector = new JComboBox<>(METHODS);

        btnEncrypt = new JButton("Encrypt");
        btnDecrypt = new JButton("Decrypt");
        btnAnalyze = new JButton("Perform Analysis");

        addComponent(gbc, "Input text:", txtInput);
        addComponent(gbc, "Shift amount:", txtShift);
        addComponent(gbc, "Select language:", languageSelector);
        addComponent(gbc, "Analysis Method:", methodSelector);
        addComponent(gbc, "Output text:", txtOutput);
        add(btnEncrypt, gbc);
        add(btnDecrypt, gbc);
        add(btnAnalyze, gbc);
    }

    private void addComponent(GridBagConstraints gbc, String label, Component comp) {
        add(new JLabel(label), gbc);
        add(comp, gbc);
    }

    private void setupListeners() {
        languageSelector.addActionListener(e -> loadCharacterSet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase()));
        btnEncrypt.addActionListener(e -> performEncryption());
        btnDecrypt.addActionListener(e -> performDecryption());
        btnAnalyze.addActionListener(e -> performAnalysis());
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

    private void performAnalysis() {
        var input = txtInput.getText();
        var method = (String) methodSelector.getSelectedItem();
        var alphabet = ConfigLoader.loadAlphabet(Objects.requireNonNull(languageSelector.getSelectedItem()).toString().toLowerCase());

        if ("Brute Force".equals(method)) {
            assert alphabet != null;
            BruteForce.bruteForceDecrypt(input, alphabet);
        } else if ("Statistical analysis".equals(method)) {
            txtOutput.setText("Statistical analysis feature is currently under development.");
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
