package MHDX;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;


public class Twofish {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    public SecretKey key;

    public SecretKey createKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Twofish");
        keyGenerator.init(128);
        key = keyGenerator.generateKey();
        return key;
    }

    public String exportKey() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public void importKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.key = new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "Twofish");
    }

    public byte[] encrypt(String text, String type) throws Exception {
        if (key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance(type);//Twofish/ECB/NoPadding
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plaintext = text.getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    public String encryptToBase64(String text) throws Exception {
        if (key == null) return "";

        Cipher cipher = Cipher.getInstance("Twofish");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] plaintext = text.getBytes("UTF-8");

        // Add PKCS7 padding to the plaintext
        int blockSize = cipher.getBlockSize();
        int paddingLength = blockSize - (plaintext.length % blockSize);
        byte[] paddedText = new byte[plaintext.length + paddingLength];
        System.arraycopy(plaintext, 0, paddedText, 0, plaintext.length);
        Arrays.fill(paddedText, plaintext.length, paddedText.length, (byte) paddingLength);

        byte[] cipherText = cipher.doFinal(paddedText);
        return Base64.getEncoder().encodeToString(cipherText);
    }


    public String decrypt(byte[] text, String type) throws Exception {
        if (key == null) return null;
        Cipher cipher = Cipher.getInstance(type);//Twofish/ECB/NoPadding
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plaintext = cipher.doFinal(text);
        return new String(plaintext, "UTF-8");
    }

    public String decryptFromBase64(String text) throws Exception {
        if (key == null) return null;

        Cipher cipher = Cipher.getInstance("Twofish");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedData = Base64.getDecoder().decode(text);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        // Remove PKCS7 padding
        int paddingLength = decryptedData[decryptedData.length - 1];
        byte[] originalData = Arrays.copyOfRange(decryptedData, 0, decryptedData.length - paddingLength);

        return new String(originalData, "UTF-8");
    }

    public void encryptFile(String sourceFile, String destFile) throws Exception {
        if (key == null) throw new FileNotFoundException("Key not Found");
        File file = new File(sourceFile);
        if (file.isFile()) {
            Cipher cipher = Cipher.getInstance("Twofish");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] input = new byte[16]; // Twofish block size is 128 bits
            int bytesRead;

            while ((bytesRead = fis.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null)
                    fos.write(output);
            }
            byte[] output = cipher.doFinal();
            if (output != null)
                fos.write(output);

            fis.close();
            fos.flush();
            fos.close();
            System.out.println("Encrypted");

        }
    }

    public void decryptFile(String sourceFile, String destFile) throws Exception {
        if (key == null) throw new FileNotFoundException("Key not Found");
        File file = new File(sourceFile);
        if (file.isFile()) {
            Cipher cipher = Cipher.getInstance("Twofish");
            cipher.init(Cipher.DECRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] input = new byte[16]; // Twofish block size is 128 bits
            int bytesRead;

            while ((bytesRead = fis.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null)
                    fos.write(output);
            }
            byte[] output = cipher.doFinal();
            if (output != null)
                fos.write(output);

            fis.close();
            fos.flush();
            fos.close();
            System.out.println("Decrypted");
        }
    }

    public static void main(String[] args) throws Exception {
        Twofish encryptor = new Twofish();

        encryptor.createKey(); // You can choose 128 or 192 as well
        String out = encryptor.encryptToBase64("Khoa Công Nghệ Thông Tin");
        System.out.println(encryptor.exportKey());
        System.out.println(out);
        System.out.println(encryptor.decryptFromBase64(out));
    }
}