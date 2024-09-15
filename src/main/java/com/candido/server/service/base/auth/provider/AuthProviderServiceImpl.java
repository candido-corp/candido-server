package com.candido.server.service.base.auth.provider;

import com.candido.server.domain.v1.provider.XrefAccountProvider;
import com.candido.server.domain.v1.provider.XrefAccountProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthProviderServiceImpl implements AuthProviderService {

    private final XrefAccountProviderRepository xrefAccountProviderRepository;

    public AuthProviderServiceImpl(XrefAccountProviderRepository xrefAccountProviderRepository) {
        this.xrefAccountProviderRepository = xrefAccountProviderRepository;
    }

    @Override
    public void addProviderToAccount(int providerId, int accountId) {
        var providerRelation = XrefAccountProvider
                .builder()
                .providerId(providerId)
                .accountId(accountId)
                .build();

        xrefAccountProviderRepository.save(providerRelation);
    }

}
