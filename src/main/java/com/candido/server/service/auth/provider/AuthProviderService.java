package com.candido.server.service.auth.provider;

public interface AuthProviderService {
    void addProviderToAccount(int providerId, int accountId);
}
