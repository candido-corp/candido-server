package com.candido.server.service.base.auth.provider;

import com.candido.server.domain.v1.provider.XrefAccountProvider;
import com.candido.server.domain.v1.provider.XrefAccountProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthProviderServiceImpl implements AuthProviderService {

    @Autowired
    private XrefAccountProviderRepository xrefAccountProviderRepository;

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
