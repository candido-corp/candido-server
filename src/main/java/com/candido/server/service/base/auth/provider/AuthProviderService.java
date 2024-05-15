package com.candido.server.service.base.auth.provider;

public interface AuthProviderService {
    void addProviderToAccount(int providerId, int accountId);
}
