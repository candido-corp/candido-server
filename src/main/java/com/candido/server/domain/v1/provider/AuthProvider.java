package com.candido.server.domain.v1.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum AuthProvider {
    LOCAL(1),
    GOOGLE(2);

    private final int providerId;

    public static Optional<AuthProvider> findByProviderName(String providerName) {
        return Arrays.stream(AuthProvider.values())
                .filter(authProvider -> authProvider.name().equalsIgnoreCase(providerName))
                .findFirst();
    }

}
