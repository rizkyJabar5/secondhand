//package com.secondhand.ecommerce.security.config;
//
//import com.secondhand.ecommerce.utils.HasLogger;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.util.UriUtils;
//
//import javax.crypto.*;
//import javax.crypto.spec.GCMParameterSpec;
//import javax.crypto.spec.PBEKeySpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.KeySpec;
//import java.util.Arrays;
//import java.util.Base64;
//
//@Service
//public class EncryptionConfig implements HasLogger {
//
//    private static final String ERROR_ENCRYPTING_DATA = "Error encrypting data";
//    private static final String ERROR_DECRYPTING_DATA = "Error decrypting data";
//    private static final String DERIVATION_FUNCTION = "PBKDF2WithHmacSHA256";
//    private static final String ENCRYPT_ALGORITHM = "AES/GCM/NoPadding";
//    private static final String AES_ALGORITHM = "AES";
//
//    private static final int GCM_TAG_LENGTH = 12; // bits
//    private static final int GCM_IV_LENGTH = 12;
//    // The password-based key derivation function
//    private static final int ITERATION_COUNT = 65536;
//    private static final int KEY_LENGTH = 256;
//
//    private final transient SecureRandom RANDOM = new SecureRandom();
//
//    private final transient String password;
//    private final transient String salt;
//
//    public EncryptionConfig(
//            @Value("${encryption.secret.password}") String password,
//            @Value("${encryption.secret.salt}") String salt) {
//
//        this.password = password;
//        this.salt = salt;
//    }
//
//    /**
//     * Encrypts the text to be sent out.
//     *
//     * @param text the text
//     * @return the encrypted text
//     */
//    public String encrypt(String text) {
//        try {
//            if (StringUtils.isBlank(text)) {
//                return null;
//            }
//            byte[] iv = new byte[GCM_IV_LENGTH];
//            RANDOM.nextBytes(iv);
//
//            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
//            GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
//            cipher.init(Cipher.ENCRYPT_MODE, getKeyFromPassword(), ivSpec);
//
//            byte[] ciphertext = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
//            byte[] encrypted = new byte[iv.length + ciphertext.length];
//            System.arraycopy(iv, 0, encrypted, 0, iv.length);
//            System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);
//
//            return Base64.getEncoder().encodeToString(encrypted);
//        } catch (NoSuchAlgorithmException
//                 | IllegalArgumentException
//                 | InvalidKeyException
//                 | InvalidAlgorithmParameterException
//                 | IllegalBlockSizeException
//                 | BadPaddingException
//                 | NoSuchPaddingException e) {
//            getLogger().debug(ERROR_ENCRYPTING_DATA, e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Decrypts the encryptedText to be sent out.
//     *
//     * @param encryptedText the encryptedText
//     * @return the decrypted uri
//     */
//    public String decrypt(String encryptedText) {
//        try {
//            if (StringUtils.isBlank(encryptedText)) {
//                return null;
//            }
//            byte[] decoded = Base64.getDecoder().decode(encryptedText);
//            byte[] iv = Arrays.copyOfRange(decoded, 0, GCM_IV_LENGTH);
//
//            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
//            GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
//            cipher.init(Cipher.DECRYPT_MODE, getKeyFromPassword(), ivSpec);
//
//            byte[] ciphertext = cipher.doFinal(decoded, GCM_IV_LENGTH, decoded.length - GCM_IV_LENGTH);
//
//            return new String(ciphertext, StandardCharsets.UTF_8);
//        } catch (NoSuchAlgorithmException
//                 | IllegalArgumentException
//                 | InvalidKeyException
//                 | InvalidAlgorithmParameterException
//                 | IllegalBlockSizeException
//                 | BadPaddingException
//                 | NoSuchPaddingException e) {
//            getLogger().debug(ERROR_DECRYPTING_DATA, e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    public String encode(String text) {
//        if (StringUtils.isBlank(text)) {
//            return null;
//        }
//        return UriUtils.encode(text, StandardCharsets.UTF_8.name());
//    }
//
//    public String decode(String encodedTest) {
//        if (StringUtils.isBlank(encodedTest)) {
//            return null;
//        }
//        return UriUtils.decode(encodedTest, StandardCharsets.UTF_8.name());
//    }
//
//    private SecretKey getKeyFromPassword() {
//        try {
//            SecretKeyFactory factory = SecretKeyFactory.getInstance(DERIVATION_FUNCTION);
//            byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
//            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATION_COUNT, KEY_LENGTH);
//
//            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), AES_ALGORITHM);
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
