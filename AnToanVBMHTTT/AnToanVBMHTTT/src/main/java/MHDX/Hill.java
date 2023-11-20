package MHDX;

import java.security.SecureRandom;

public class Hill {
    private int[][] keyMatrix;

    // Generate a random 3x3 key matrix for Hill Cipher
    public String createKey() {
        keyMatrix = new int[2][2];
        SecureRandom random = new SecureRandom();
        do {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    keyMatrix[i][j] = random.nextInt(26);
                }
            }
        }while (!isMatrixInvertible(keyMatrix));
        return formatKey();
    }
    private boolean isMatrixInvertible(int[][] matrix) {
        int det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        if(det == 0) return false;
        if(det<=0){
            det+=26;
        }
        if(det % 2 == 0) return false;
        return true;
    }
    public String formatKey(){
        String keyString = "";
        for(int i=0; i< keyMatrix.length; i++){
            for(int j=0; j<keyMatrix[i].length; j++){
                keyString += keyMatrix[i][j] + " ";
            }
        }
        return keyString;
    }

    // Import key from a string
    public void importKey(String key) {
        String[] keyRows = key.split(" ");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                keyMatrix[i][j] = Integer.parseInt(keyRows[i*2+j]);
                System.out.println(keyMatrix[i][j]);
            }
        }
    }

    // Encrypt plaintext
    public String encrypt(String message) {
        message = addPadding(message);
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i += 2) {
            int[] pair = {message.charAt(i) - 'a', message.charAt(i + 1) - 'a'};
            int[] result = multiplyMatrix(keyMatrix, pair);

            encryptedMessage.append((char) (result[0] + 'a'));
            encryptedMessage.append((char) (result[1] + 'a'));
        }

        return encryptedMessage.toString();
    }
    private String addPadding(String message) {
        while (message.length() % 2 != 0) {
            message += 'x'; // Có thể thay đổi ký tự padding tùy ý
        }
        return message;
    }
    private static int[] multiplyMatrix(int[][] matrix, int[] vector) {
        int[] result = new int[2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] = (result[i] + 26) % 26; // Sửa lỗi, cần thêm ALPHABET_SIZE trước khi lấy phần dư
        }
        return result;
    }

    // Decrypt ciphertext
    public String decrypt(String encryptedMessage) {
        encryptedMessage = addPadding(encryptedMessage);
        StringBuilder decryptedMessage = new StringBuilder();

        int determinant = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0] + 26);
        while(determinant<=0){
            determinant+=26;
        }
        determinant = determinant % 26;
        int modularInverse = findModularInverse(determinant, 26);
        int[][] inverseMatrix = {{keyMatrix[1][1], -keyMatrix[0][1] + 26},
                {-keyMatrix[1][0] + 26, keyMatrix[0][0]}};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverseMatrix[i][j] = (inverseMatrix[i][j] * modularInverse) % 26;
            }
        }

        for (int i = 0; i < encryptedMessage.length(); i += 2) {
            int[] pair = {encryptedMessage.charAt(i) - 'a', encryptedMessage.charAt(i + 1) - 'a'};
            int[] result = multiplyMatrix(inverseMatrix, pair);

            decryptedMessage.append((char) (result[0] + 'a'));
            decryptedMessage.append((char) (result[1] + 'a'));
        }


        return decryptedMessage.toString();
    }

    private int findModularInverse(int a, int m) {
        for (int i = 1; i < m; i++) {
            if ((a * i) % m == 1) {
                return i;
            }
        }
        return -1;
    }

}
