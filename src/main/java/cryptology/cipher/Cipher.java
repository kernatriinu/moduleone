package main.java.cryptology.cipher;

public interface Cipher {
    String encrypt(String text);
    String decrypt(String text);
}
