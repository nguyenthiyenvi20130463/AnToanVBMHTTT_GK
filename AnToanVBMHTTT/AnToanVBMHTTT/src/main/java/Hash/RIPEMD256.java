package Hash;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class RIPEMD256 {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String check(String data, String type) {
        try {
            MessageDigest md = MessageDigest.getInstance(type, "BC");
            byte[] output = md.digest(data.getBytes());
            return new BigInteger(1, output).toString(16);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String checkFile(String path) {
        try {
            MessageDigest md = MessageDigest.getInstance("RIPEMD256", "BC");
            DigestInputStream dis = new DigestInputStream(new BufferedInputStream(new FileInputStream(new File(path))), md);
            byte[] read = new byte[1024];
            int i;
            do {
                i = dis.read(read);
            } while (i != -1);

            return new BigInteger(1, dis.getMessageDigest().digest()).toString(16);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
       // System.out.println(RIPEMD256.check("KHoa Công Nghệ THông Tin"));

    }
}

