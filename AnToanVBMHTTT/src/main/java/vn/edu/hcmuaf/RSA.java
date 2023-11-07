package vn.edu.hcmuaf;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class RSA {
    KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;

    public void genKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGnerator = KeyPairGenerator.getInstance("RSA");
        keyGnerator.initialize(2048);
        keyPair = keyGnerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

    }
    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(output);

    }
    public String decrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] output = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(output, StandardCharsets.UTF_8);
    }
    public void fileEncrypt(String inpputPath, String ouputPath) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        byte[] iv = new byte[16];
        IvParameterSpec spec = new IvParameterSpec(iv);
        SecretKey secretKey = keyGen.generateKey();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        CipherInputStream inputStream = new CipherInputStream(new BufferedInputStream(new FileInputStream(inpputPath)), cipher);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(ouputPath)));

        String keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        dataOutputStream.writeUTF(keyString);
        dataOutputStream.writeLong(new File(inpputPath).length());
        dataOutputStream.writeUTF(Base64.getEncoder().encodeToString(iv));

        byte[] buff = new byte[1024];
        int i;
        while((i=inputStream.read(buff))!=-1){
            dataOutputStream.write(buff,0,i);
        }
        inputStream.close();
        dataOutputStream.flush();
        dataOutputStream.close();

    }
    public void fileDecrypt(String inputPath, String outputPath) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(inputPath)));
        String keyString = dis.readUTF();
        long size = dis.readLong();
        byte[] iv = Base64.getDecoder().decode(dis.readUTF());
//        System.out.println(keyString);
//        System.out.println(size);
//        System.out.println(iv);

        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(decrypt(keyString)),"AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        CipherInputStream cis = new CipherInputStream(dis,cipher);
        BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(outputPath));

        byte[] buff = new byte[1024];
        int i;
        while((i =cis.read(buff))!=-1){
            bof.write(buff, 0,i);
        }
        cis.close();
        bof.close();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        RSA rsa = new RSA();
        rsa.genKey();
//        String encrypt = rsa.encrypt("CNTT");
//        System.out.println(encrypt);
//        System.out.println(rsa.decrypt(encrypt));
        rsa.fileDecrypt("src1.rar","src2.rar");
    }

    /**
     * - create public key and private key
     * - create key giải thuật MH ĐX
     * - quá trình mã hóa
     * - Mã key giải thuật -> lưu vào file
     * - Lưu độ dài file
     * - Mã hóa file bằng key giải thuật MH ĐX và lưu vào file
     */
}
