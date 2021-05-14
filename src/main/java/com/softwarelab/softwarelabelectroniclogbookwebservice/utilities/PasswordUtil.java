package com.softwarelab.softwarelabelectroniclogbookwebservice.utilities;


import com.fasterxml.uuid.Generators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
@Service
public class PasswordUtil {
@Autowired
PasswordEncoder passwordEncoder;

    public  String getHash(String password) {
        return passwordEncoder.encode(password);
    }
    public static String getHash1(String password) {
        String hash = "";
        try {
            hash = PasswordUtil.generateStrongPassword(password);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            hash = null;
        }
        return hash;
    }


    private static String generateStrongPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 65536;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }


    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Check whether a password matches a hash
     *
     * @param password
     * @param hash
     * @return
     */
    public boolean isPasswordValid(String password, String hash) {
        return passwordEncoder.matches(password,hash);
    }
    public static boolean isPasswordValid1(String password, String hash) {
        boolean matched = false;
        try {
            matched = PasswordUtil.validatePassword(password, hash);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            matched = false;
        }
        return matched;
    }


    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = 65536;
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static String generateToken(String email){
        //using current time and user email and random string
        String random = Generators.randomBasedGenerator().generate().toString();
        UUID uuid = Generators.nameBasedGenerator().generate(email+random+System.currentTimeMillis());
        return uuid.toString();
    }

}
