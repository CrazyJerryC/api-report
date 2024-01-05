package com.zjy.apireport.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESGCMEncryption {

    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;
    private static final int ITERATION_COUNT = 10000;

    public static String encrypt(String plaintext, String password) throws Exception {
        byte[] salt = generateSalt();

        SecretKey secretKey = deriveKeyFromPassword(password, salt);

        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] cipherText = cipher.doFinal(plaintextBytes);

        byte[] encrypted = new byte[salt.length + iv.length + cipherText.length];
        System.arraycopy(salt, 0, encrypted, 0, salt.length);
        System.arraycopy(iv, 0, encrypted, salt.length, iv.length);
        System.arraycopy(cipherText, 0, encrypted, salt.length + iv.length, cipherText.length);

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String ciphertext, String password) throws Exception {
        byte[] encrypted = Base64.getDecoder().decode(ciphertext);

        byte[] salt = new byte[16];
        System.arraycopy(encrypted, 0, salt, 0, salt.length);

        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(encrypted, salt.length, iv, 0, iv.length);

        byte[] cipherText = new byte[encrypted.length - salt.length - iv.length];
        System.arraycopy(encrypted, salt.length + iv.length, cipherText, 0, cipherText.length);

        SecretKey secretKey = deriveKeyFromPassword(password, salt);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] decrypted = cipher.doFinal(cipherText);

        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private static SecretKey deriveKeyFromPassword(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static void main(String[] args) throws Exception {
        String password = "YourPassword"; // 用于生成密钥的密码
        String plaintext = "Hello, AES/GCM encryption with password!";

        String encryptedText = encrypt(plaintext, password);
//        String encryptedText = "+Won5CjxhLm/VfhagI0HmGm5eiWAR6DFUSYGlUdwiy1ywhdKT7SJ/llZ/GX+a0VYtJj0z3GDMtdGUTG/nkun+flUwYEvtEnIFblS2CE5ziCgmGCv";
        System.out.println("Encrypted: " + encryptedText);

        String decryptedText = decrypt(encryptedText, password);
        System.out.println("Decrypted: " + decryptedText);
    }
}


