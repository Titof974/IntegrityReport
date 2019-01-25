package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String generateSha256(Path path) throws NoSuchAlgorithmException, IOException {
        DigestInputStream digestInputStream = new DigestInputStream(Files.newInputStream(path), MessageDigest.getInstance("SHA-256"));
        byte[] inputStreamBuffer = new byte[8192];
        while(digestInputStream.read(inputStreamBuffer) > -1);
        byte[] hash = digestInputStream.getMessageDigest().digest();
        StringBuilder stringBuilder = new StringBuilder();
        for(byte i : hash){
            stringBuilder.append(Integer.toHexString(i & 0xff));
        }
        return stringBuilder.toString();
    }
}
