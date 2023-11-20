package CKDT;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static String check(String data, String type) {
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            byte[] output = md.digest(data.getBytes());
            BigInteger num = new BigInteger(1, output);
            return num.toString(16);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String checkFile(String path) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            DigestInputStream dis = new DigestInputStream(new BufferedInputStream(new FileInputStream(new File(path))), md);
            byte[] read = new byte[1024];
            int i;
            do {
                i = dis.read(read);
            } while (i != -1);

            BigInteger num = new BigInteger(1, dis.getMessageDigest().digest());
            return num.toString(16);

        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String dataToHash = "Khoa công nghệ thông tin";
       // String fileToHash = "";
        String sha256Result = check(dataToHash,"SHA-256");
        System.out.println(sha256Result);
       // String sha256FileResult = hashFile(fileToHash);
       // System.out.println("SHA-256 hash of file: " + sha256FileResult);
    }
}

