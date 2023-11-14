package MHDX;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {
    public SecretKey key;

    public SecretKey createKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        key = keyGenerator.generateKey();
        return key;
    }

    public String exportKey() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public void importKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.key = new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public byte[] encrypt(String text, String type) throws Exception {
        if (key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance(type); //"AES/ECB/PKCS5Padding"
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plaintext = text.getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    public String encryptToBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plaintext = text.getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(plaintext);
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(byte[] text, String type) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(type);//"AES/ECB/PKCS5Padding"
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plaintext = cipher.doFinal(text);
        return new String(plaintext, "UTF-8");

    }

    public String decryptFromBase64(String text) throws Exception {
        if (key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(plaintext, "UTF-8");
    }

    public void encryptFile(String sourceFile, String destFile) throws Exception {
        if (key == null) throw new FileNotFoundException("Key not Found");
        File file = new File(sourceFile);
        if (file.isFile()) {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] input = new byte[64];
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
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] input = new byte[64];
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
        AES encryptor = new AES();

        encryptor.createKey();
        String out = encryptor.encryptToBase64("Khoa Công Nghệ Thông Tin");
        System.out.println(encryptor.exportKey());
        System.out.println(out);
        System.out.println(encryptor.decryptFromBase64(out));
    }
}

