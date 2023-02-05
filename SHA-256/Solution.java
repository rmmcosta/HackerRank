import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.math.BigInteger;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.nio.charset.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        try {
            SHA256 sha256 = new SHA256();
            Scanner sc = new Scanner(System.in);  
            System.out.println(sha256.hash(sc.nextLine()));
            sc.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class SHA256 {
    public String hash(String text) throws RuntimeException {
        try   
        {  
        //static getInstance() method is called with hashing MD5  
            MessageDigest md = MessageDigest.getInstance("SHA-256");  
            //calculating message digest of an input that return array of byte
            byte[] encodedHash = md.digest(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);  
        }  
        //for specifying wrong message digest algorithms  
        catch (NoSuchAlgorithmException e)   
        {  
            throw new RuntimeException(e);  
        }  
    }
    
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}