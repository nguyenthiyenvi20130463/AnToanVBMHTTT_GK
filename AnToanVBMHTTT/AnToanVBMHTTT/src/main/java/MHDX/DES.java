package MHDX;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DES {
    public SecretKey key;
    public SecretKey createKey() throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        key = keyGenerator.generateKey();
        return key;
    }
    public String exportKey() {

        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public Key importKey(String key){
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.key = new javax.crypto.spec.SecretKeySpec(decodedKey,0, decodedKey.length,"DES");
        return null;
    }

    public byte[] encrypt(String text, String type) throws Exception{
        if(key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plaintext = text.getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }
    // encrypt string and return base64 string
    public String encryptToBase64(String text) throws Exception{
        if(key == null) return "";
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plaintext = text.getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(plaintext);
        return Base64.getEncoder().encodeToString(cipherText);
    }
    // decrypt must using byte[]
    public String decrypt(byte[] text, String type )throws Exception{
        if(key == null) return null;
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] plaintext = cipher.doFinal(text);
        String output = new String(plaintext, "UTF-8");
        return output;
    }
    public String decryptFromBase64(String text) throws Exception{
        if(key == null) return null;
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(text));
        String ouput = new String(plaintext, "UTF-8");
        return ouput;
    }
    // encryptFile
    public void encryptFile(String sourceFile, String destFile) throws Exception {
        if(key == null) throw new FileNotFoundException("Key not Found");
        File file = new File(sourceFile);
        if(file.isFile()){
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] input = new byte[128];
            int bytesRed;

            while((bytesRed = fis.read(input)) !=-1){
                byte[] output = cipher.update(input,0, bytesRed);
                if(output != null)
                    fos.write(output);
            }
            byte[] output = cipher.doFinal();
            if(output != null)
                fos.write(output);

            fis.close();
            fos.flush();
            fos.close();
            System.out.println("Encrypted");

        }else{
            System.out.println("This is not file!!");
        }
    }
    // decryptFile
    public void decryptFile(String sourceFile, String destFile) throws Exception {
        if (key == null) throw new FileNotFoundException("Key not Found");
        File file = new File(sourceFile);
        if (file.isFile()) {

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(new File(destFile));

            byte[] input = new byte[128];
            int redByte = 0;
            while ((redByte = fis.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, redByte);
                if (output != null)
                    fos.write(output);
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                fos.write(output);
            }
            fis.close();
            fos.flush();
            fos.close();
            System.out.println("Encrypted");
        }
    }

    public static void main(String[] args) throws Exception {
        DES des = new DES();

        des.createKey();
        String out = des.encryptToBase64("Khoa Công Nghệ Thông Tin");
        System.out.println(des.exportKey());
        System.out.println(out);
        System.out.println(des.decryptFromBase64(out));

    }



}