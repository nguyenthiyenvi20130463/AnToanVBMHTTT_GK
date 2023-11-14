package MHDX;

import java.util.Scanner;

public class Hill {
    private static final int MATRIX_SIZE = 3; // Kích thước của ma trận khóa

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập văn bản cần mã hóa: ");
        String plaintext = scanner.nextLine();

        // Tạo ma trận khóa
        int[][] keyMatrix = generateKeyMatrix();

        // Mã hóa văn bản
        int[][] encryptedMatrix = encrypt(plaintext, keyMatrix);
        System.out.println("Văn bản đã mã hóa: " + matrixToString(encryptedMatrix));

        // Giải mã văn bản
        String decryptedText = decrypt(encryptedMatrix, keyMatrix);
        System.out.println("Văn bản đã giải mã: " + decryptedText);
    }

    // Hàm tạo ma trận khóa
    private static int[][] generateKeyMatrix() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập ma trận khóa " + MATRIX_SIZE + "x" + MATRIX_SIZE + " theo hàng, cách nhau bởi dấu cách:");
        int[][] keyMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }
        return keyMatrix;
    }

    // Hàm mã hóa văn bản
    private static int[][] encrypt(String plaintext, int[][] keyMatrix) {
        int length = plaintext.length();
        int[][] plaintextMatrix = new int[MATRIX_SIZE][length / MATRIX_SIZE];

        // Chuyển văn bản thành ma trận số
        for (int i = 0; i < length; i++) {
            plaintextMatrix[i % MATRIX_SIZE][i / MATRIX_SIZE] = plaintext.charAt(i) - 'A';
        }

        // Nhân ma trận khóa với ma trận văn bản
        int[][] encryptedMatrix = multiplyMatrices(keyMatrix, plaintextMatrix);

        return encryptedMatrix;
    }

    // Hàm giải mã văn bản
    private static String decrypt(int[][] encryptedMatrix, int[][] keyMatrix) {
        int[][] inverseKeyMatrix = getInverseMatrix(keyMatrix);

        // Nhân ma trận khóa nghịch đảo với ma trận đã mã hóa
        int[][] decryptedMatrix = multiplyMatrices(inverseKeyMatrix, encryptedMatrix);

        // Chuyển ma trận số thành văn bản
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < decryptedMatrix[0].length; i++) {
            for (int j = 0; j < decryptedMatrix.length; j++) {
                decryptedText.append((char) (decryptedMatrix[j][i] + 'A'));
            }
        }

        return decryptedText.toString();
    }

    // Hàm nhân hai ma trận
    private static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int[][] resultMatrix = new int[matrixA.length][matrixB[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
                resultMatrix[i][j] %= 26; // Modulo 26 for the English alphabet
            }
        }
        return resultMatrix;
    }

    // Hàm lấy ma trận nghịch đảo
    private static int[][] getInverseMatrix(int[][] matrix) {
        // TODO: Implement the method to calculate the inverse matrix
        return null;
    }

    // Hàm chuyển ma trận thành chuỗi
    private static String matrixToString(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(matrix[i][j]).append(" ");
            }
        }
        return result.toString();
    }
}
