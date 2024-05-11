package main.java.cryptology;

import javax.swing.*;
import main.java.cryptology.gui.CryptoGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CryptoGUI::new);
    }
}
