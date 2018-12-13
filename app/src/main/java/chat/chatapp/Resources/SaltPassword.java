package chat.chatapp.Resources;

import android.os.Debug;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;



public class SaltPassword {

    public static String[] GenerateSaltedHash(String password){
        //byte[] bytesOfMessage = yourString.getBytes("UTF-8");
        StringBuffer saltString = new StringBuffer();
        StringBuffer mergedString = new StringBuffer();
        String[] result = new String[3];

        try {
            //generate random salt password and get the Bytes of it
            /*byte[] saltBytes = RngBytes(20);
            String randomString = RngString(10);
            byte[] passwordBytes = password.getBytes("UTF-8");

        //compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.8'
            byte[] mergedBytes = MergeBytes(saltBytes, passwordBytes);

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] StringEncoded = md.digest(randomString.getBytes());
            for (int i = 0; i < StringEncoded.length; ++i) {
                saltString.append(Integer.toHexString((StringEncoded[i] & 0xFF) | 0x100).substring(1, 3));
            }

            byte[] encodedMD5 = md.digest(mergedBytes);
            for (int i = 0; i < encodedMD5.length; ++i) {
                mergedString.append(Integer.toHexString((encodedMD5[i] & 0xFF) | 0x100).substring(1, 3));
            }

            result[0] = randomString;
            result[1] = mergedString.toString();
            result[2] = password + saltString.toString();*/
            //result[1] = new String(saltBytes);
            //result[2] = new String(passwordBytes);

            /**
             * Convert the password argument into HASH, then hexadecimal
             */
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashOfPassword = digest.digest(password.getBytes("UTF-8"));

            StringBuffer hexPassword = new StringBuffer();
            for (int i = 0; i < hashOfPassword.length; i++) {
                String hex = Integer.toHexString(0xff & hashOfPassword[i]);
                if (hex.length() == 1) hexPassword.append('0');
                hexPassword.append(hex);
            }

            String generatedString = RandomStringUtils.randomAlphabetic(10);
            byte[] hashOfRandomString = digest.digest(generatedString.getBytes("UTF-8"));

            StringBuffer hexRandomString = new StringBuffer();
            for (int i = 0; i < hashOfRandomString.length; i++) {
                String hex = Integer.toHexString(0xff & hashOfRandomString[i]);
                if (hex.length() == 1) hexRandomString.append('0');
                hexRandomString.append(hex);
            }

            result[0] = hexPassword.toString();
            result[1] = hexRandomString.toString();
            result[2] = hexPassword.toString() + hexRandomString.toString();

        } catch (NoSuchAlgorithmException ex) { Log.d("MD5",ex.toString()); }
        catch (UnsupportedEncodingException ex) { Log.d("UTF-8",ex.toString()); }
        return result;
    }

    private static byte[] RngBytes(int length){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private static String RngString(){
        byte[] array = new byte[10]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    private static byte[] MergeBytes(byte[] SaltBytes, byte[] PasswordBytes){
        byte[] combinated = new byte[SaltBytes.length + PasswordBytes.length];
        for (int i = 0; i < combinated.length; ++i){
            combinated[i] = i < SaltBytes.length ? SaltBytes[i] : PasswordBytes[i-SaltBytes.length];
        }
        return combinated;
    }

}
