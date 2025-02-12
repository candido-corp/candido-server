package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.*;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountSettingsKeyNotFound;
import com.candido.server.validation.account.AccountSettingValidator;
import com.candido.server.validation.account.LanguageSettingValidator;
import com.candido.server.validation.account.ThemeSettingValidator;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountSettingsServiceImpl implements AccountSettingsService{

    private final AccountSettingsRepository accountSettingsRepository;
    private final XrefAccountSettingsRepository xrefAccountSettingsRepository;

    private static final Map<String, AccountSettingValidator> VALIDATORS = Map.of(
            "S_THEME", new ThemeSettingValidator(),
            "S_LANG", new LanguageSettingValidator()
    );

    @Autowired
    AccountSettingsServiceImpl(
            AccountSettingsRepository accountSettingsRepository,
            XrefAccountSettingsRepository xrefAccountSettingsRepository
    ) {
        this.accountSettingsRepository = accountSettingsRepository;
        this.xrefAccountSettingsRepository = xrefAccountSettingsRepository;
    }

    @Override
    public Optional<AccountSettings> fromKey(String key) {
        Specification<AccountSettings> byKey = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(AccountSettings_.ACCOUNT_SETTINGS_KEY), key);

        return accountSettingsRepository.findOne(byKey);
    }

    @Override
    public List<XrefAccountSettings> getAllAccountSettings(int accountId) {
        Specification<XrefAccountSettings> byAccountId = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(XrefAccountSettings_.ACCOUNT_ID), accountId);

        return xrefAccountSettingsRepository.findAll(byAccountId);
    }

    @Override
    public <T> void saveAccountSetting(int accountId, String key, T value) {
        Optional<AccountSettings> accountSettings = fromKey(key);
        if (accountSettings.isEmpty()) {
            throw new ExceptionAccountSettingsKeyNotFound(
                    EnumExceptionName.ERROR_BUSINESS_ACCOUNT_SETTINGS_KEY_NOT_FOUND.name(), key
            );
        }

        if (VALIDATORS.containsKey(key)) {
            VALIDATORS.get(key).validate(value.toString());
        }

        XrefAccountSettings xrefAccountSettings = XrefAccountSettings.builder()
                .accountId(accountId)
                .accountSettingsId(accountSettings.get().getAccountSettingsId())
                .value(value.toString())
                .build();

        xrefAccountSettingsRepository.save(xrefAccountSettings);
    }

    @Override
    public Optional<XrefAccountSettings> getAccountSetting(int accountId, String key) {
        Specification<XrefAccountSettings> byAccountIdAndKey = (root, query, criteriaBuilder) -> {
            Join<XrefAccountSettings, AccountSettings> accountSettingsJoin = root.join(XrefAccountSettings_.ACCOUNT_SETTINGS);

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get(XrefAccountSettings_.ACCOUNT_ID), accountId),
                    criteriaBuilder.equal(accountSettingsJoin.get(AccountSettings_.ACCOUNT_SETTINGS_KEY), key)
            );
        };

        return xrefAccountSettingsRepository.findOne(byAccountIdAndKey);
    }

    @Override
    public void deleteAccountSetting(int accountId, String key) {
        getAccountSetting(accountId, key).ifPresent(xrefAccountSettingsRepository::delete);
    }

    @Override
    public void deleteAllAccountSettings(int accountId) {
        xrefAccountSettingsRepository.deleteAllByAccountId(accountId);
    }
}
