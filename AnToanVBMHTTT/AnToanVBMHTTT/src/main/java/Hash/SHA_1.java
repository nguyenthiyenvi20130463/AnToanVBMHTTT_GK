package Hash;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA_1 {
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
                MessageDigest md = MessageDigest.getInstance("SHA-1");
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
            System.out.println(SHA_1.check("Khoa Công Nghệ Thông Tin", "SHA-1"));
        }


}
