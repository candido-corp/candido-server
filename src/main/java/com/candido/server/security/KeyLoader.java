package com.candido.server.security;

import com.candido.server.exception.security.auth.ExceptionAuth;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyLoader {

    private String normalizeKey(String key, boolean isPrivate) {
        String placeholder = isPrivate ? "PRIVATE" : "PUBLIC";
        return key.replace("-----BEGIN " + placeholder + " KEY-----", "")
                .replace("-----END " + placeholder + " KEY-----", "")
                .replaceAll("\\s", "");
    }

    private KeyFactory getKeyFactoryInstance(String algorithm) throws NoSuchAlgorithmException {
        try {
            return KeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
    }

    private PrivateKey generatePrivateKey(KeyFactory keyFactory, PKCS8EncodedKeySpec keySpec) throws InvalidKeySpecException {
        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException(e);
        }
    }

    private PublicKey generatePublicKey(KeyFactory keyFactory, X509EncodedKeySpec keySpec) throws InvalidKeySpecException {
        try {
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException(e);
        }
    }

    public PrivateKey loadPrivateKey() throws ExceptionAuth {
        ClassPathResource resource = new ClassPathResource("security/keys/private_key_pkcs8.pem");
        try (InputStream is = resource.getInputStream()) {
            String privateKeyPEM = normalizeKey(new String(is.readAllBytes()), true);
            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            KeyFactory keyFactory = getKeyFactoryInstance("EC");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return generatePrivateKey(keyFactory, keySpec);
        } catch (Exception e) {
            throw new ExceptionAuth(e.getMessage());
        }
    }


    public PublicKey loadPublicKey() throws ExceptionAuth {
        ClassPathResource resource = new ClassPathResource("security/keys/public_key.pem");
        try (InputStream is = resource.getInputStream()) {
            String privateKeyPEM = normalizeKey(new String(is.readAllBytes()), false);
            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            KeyFactory keyFactory = getKeyFactoryInstance("EC");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return generatePublicKey(keyFactory, keySpec);
        } catch (Exception e) {
            throw new ExceptionAuth(e.getMessage());
        }
    }

}
