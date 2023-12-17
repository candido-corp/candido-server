package com.candido.server.domain.v1.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum AuthProviderEnum {
    LOCAL(1),
    GOOGLE(2);

    private final int providerId;

    public static Optional<AuthProviderEnum> findByProviderName(String providerName) {
        return Arrays.stream(AuthProviderEnum.values())
                .filter(authProviderEnum -> authProviderEnum.name().equalsIgnoreCase(providerName))
                .findFirst();
    }

}
