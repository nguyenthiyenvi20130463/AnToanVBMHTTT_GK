package MHDX;

import java.io.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class Vigenere {
    public String key;
    public void generateKey(){
        Random random = new Random();
        int n = random.nextInt(26);
        char[] alphaBet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        ArrayList<Character> characterList = new ArrayList<>();
        for (char c : alphaBet) {
            characterList.add(c);
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(characterList.size());
            char randomChar = characterList.get(randomIndex);
            result.append(randomChar);
            characterList.remove(randomIndex);
        }

        key = result.toString();
    }

    public void importKey(String key) {
        this.key = key;
    }

public String decrypt(String plaintext) {
    StringBuilder ciphertext = new StringBuilder();
    int keyLength = key.length();

    for (int i = 0; i < plaintext.length(); i++) {
        char plainChar = plaintext.charAt(i);
        char keyChar = key.charAt(i % keyLength);
        ciphertext.append(encryptChar(plainChar, keyChar));
    }

    return ciphertext.toString();
}



        public String encrypt(String ciphertext) {
            StringBuilder plaintext = new StringBuilder();
            int keyLength = key.length();

            for (int i = 0; i < ciphertext.length(); i++) {
                char cipherChar = ciphertext.charAt(i);
                char keyChar = key.charAt(i % keyLength);
                plaintext.append(decryptChar(cipherChar, keyChar));
            }

            return plaintext.toString();
        }

//        private char encryptChar(char plainChar, char keyChar) {
//            // Implement your encryption logic here
//            // For example, a simple XOR operation
//            return (char) (plainChar ^ keyChar);
//        }
//
//        private char decryptChar(char cipherChar, char keyChar) {
//            // Implement your decryption logic here
//            // For example, the same XOR operation as in encryption
//            return (char) (cipherChar ^ keyChar);
//        }
    private char encryptChar(char plaintextChar, char keyChar) {
        if (!Character.isLetter(plaintextChar)) {
            return plaintextChar; // Keep non-alphabetic characters unchanged
        }

        int shift = keyChar - 'A';
        char base = Character.isUpperCase(plaintextChar) ? 'A' : 'a';

        return (char) (((plaintextChar - base + shift) % 26 + 26) % 26 + base);
    }

    private char decryptChar(char ciphertextChar, char keyChar) {
        if (!Character.isLetter(ciphertextChar)) {
            return ciphertextChar; // Keep non-alphabetic characters unchanged
        }

        int shift = keyChar - 'A';
        char base = Character.isUpperCase(ciphertextChar) ? 'A' : 'a';

        return (char) (((ciphertextChar - base - shift) % 26 + 26) % 26 + base);
    }
}

