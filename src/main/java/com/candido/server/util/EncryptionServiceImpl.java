package com.candido.server.util;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.ExceptionResolver;
import com.candido.server.exception.security.auth.ExceptionAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class EncryptionServiceImpl implements EncryptionService {

    private static final String SECRET = "7G12bJ3gF6H8K9L0P2Q5R8S1U4V7W0Z3";
    private final SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET), "AES");
    private final ExceptionResolver exceptionResolver;

    @Override
    public String encrypt(String data) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedData);
        } catch (Exception e) {
            exceptionResolver.printException(e, "[DATA_TO_ENCRYPT]: " + data);
            throw new ExceptionAuth(EnumExceptionName.ERROR_AUTH_VERIFICATION.name());
        }
    }

    @Override
    public String decrypt(String encryptedData) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = Base64.getUrlDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            exceptionResolver.printException(e, "[DATA_TO_DECRYPT]: " + encryptedData);
            throw new ExceptionAuth(EnumExceptionName.ERROR_AUTH_VERIFICATION.name());
        }
    }
}
