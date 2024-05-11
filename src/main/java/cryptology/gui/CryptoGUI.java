package main.java.cryptology.gui;

import main.java.cryptology.cipher.CaesarCipher;
import main.java.cryptology.cryptanalysis.BruteForceAnalyzer;
import main.java.cryptology.util.CharacterUtils;

import javax.swing.*;
import java.awt.*;

public class CryptoGUI extends JFrame {
    private final JTextField txtInput;
    private final JTextField txtOutput;
    private final JTextField txtShift;

    public CryptoGUI() {
        setTitle("Caesar Cipher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        txtInput = new JTextField();
        txtOutput = new JTextField();
        txtShift = new JTextField();
        JButton btnEncrypt = new JButton("Encrypt");
        JButton btnDecrypt = new JButton("Decrypt");
        JButton btnBruteForce = new JButton("Brute Force Decrypt");

        add(new JLabel("Input text:"));
        add(txtInput);
        add(new JLabel("Shift amount:"));
        add(txtShift);
        add(new JLabel("Output text:"));
        add(txtOutput);
        add(btnEncrypt);
        add(btnDecrypt);
        add(btnBruteForce);

        btnEncrypt.addActionListener(e -> {
            String input = txtInput.getText();
            if (CharacterUtils.isLettersAndSpaces(input)) {
                int shift = Integer.parseInt(txtShift.getText());
                txtOutput.setText(CaesarCipher.encrypt(input, shift));
            } else {
                JOptionPane.showMessageDialog(this, "Input contains invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDecrypt.addActionListener(e -> {
            String input = txtInput.getText();
            int shift = Integer.parseInt(txtShift.getText());
            txtOutput.setText(CaesarCipher.decrypt(input, shift));
        });

        btnBruteForce.addActionListener(e -> {
            String input = txtInput.getText();
            String result = BruteForceAnalyzer.decrypt(input, text -> text.contains("example"));  // Example predicate
            txtOutput.setText(result);
        });

        setVisible(true);
    }
}
