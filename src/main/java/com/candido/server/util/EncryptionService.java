package com.candido.server.util;

public interface EncryptionService {
    String encrypt(String data);
    String decrypt(String encryptedData);
}
