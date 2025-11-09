package com.candido.server.util;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.ExceptionResolver;
import com.candido.server.exception.security.auth.ExceptionAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final SecretKey secretKey;
    private final ExceptionResolver exceptionResolver;

    @Autowired
    public EncryptionServiceImpl(ExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
        // Decodifica la chiave
        byte[] keyBytes = Base64.getDecoder().decode("7G12bJ3gF6H8K9L0P2Q5R8S1U4V7W0Z3");
        // Verifica la lunghezza della chiave
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException("Invalid key length: " + keyBytes.length);
        }
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    @Override
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            byte[] iv = new byte[12];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            byte[] ivAndEncryptedData = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, ivAndEncryptedData, 0, iv.length);
            System.arraycopy(encryptedData, 0, ivAndEncryptedData, iv.length, encryptedData.length);

            return Base64.getUrlEncoder().withoutPadding().encodeToString(ivAndEncryptedData);
        } catch (Exception e) {
            exceptionResolver.printException(e, "[DATA_TO_ENCRYPT]: " + data);
            throw new ExceptionAuth(EnumExceptionName.ERROR_AUTH_VERIFICATION.name());
        }
    }

    @Override
    public String decrypt(String encryptedData) {
        try {
            byte[] ivAndEncryptedData = Base64.getUrlDecoder().decode(encryptedData);

            byte[] iv = Arrays.copyOfRange(ivAndEncryptedData, 0, 12);
            byte[] encryptedBytes = Arrays.copyOfRange(ivAndEncryptedData, 12, ivAndEncryptedData.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            byte[] decryptedData = cipher.doFinal(encryptedBytes);

            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            exceptionResolver.printException(e, "[DATA_TO_DECRYPT]: " + encryptedData);
            throw new ExceptionAuth(EnumExceptionName.ERROR_AUTH_VERIFICATION.name());
        }
    }
}
