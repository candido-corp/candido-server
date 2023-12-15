package com.candido.server.service.auth.provider;

import com.biotekna.doctor.security.domain.provider.XrefAccountProvider;
import com.biotekna.doctor.security.domain.provider.XrefAccountProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

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
