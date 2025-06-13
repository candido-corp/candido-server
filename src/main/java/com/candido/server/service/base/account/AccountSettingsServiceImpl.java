package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.*;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountSettingsKeyNotFound;
import com.candido.server.validation.account.AccountSettingValidator;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountSettingsServiceImpl implements AccountSettingsService{

    private final AccountSettingsRepository accountSettingsRepository;
    private final XrefAccountSettingsRepository xrefAccountSettingsRepository;

    private final Map<AccountSettingsKeyEnum, AccountSettingValidator> validators;

    @Autowired
    AccountSettingsServiceImpl(
            AccountSettingsRepository accountSettingsRepository,
            XrefAccountSettingsRepository xrefAccountSettingsRepository,
            List<AccountSettingValidator> validatorList
    ) {
        this.accountSettingsRepository = accountSettingsRepository;
        this.xrefAccountSettingsRepository = xrefAccountSettingsRepository;
        this.validators = validatorList.stream()
                .collect(Collectors.toMap(AccountSettingValidator::getKey, Function.identity()));
    }

    @Override
    public Optional<AccountSettings> byKey(String key) {
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
        Optional<AccountSettings> accountSettings = byKey(key);
        if (accountSettings.isEmpty()) {
            throw new ExceptionAccountSettingsKeyNotFound(
                    EnumExceptionName.ERROR_VALIDATION_ACCOUNT_SETTINGS_KEY_NOT_FOUND.name(), key
            );
        }

        AccountSettingsKeyEnum enumKey;

        try {
            enumKey = AccountSettingsKeyEnum.valueOf(key);
        } catch (IllegalArgumentException ex) {
            throw new ExceptionAccountSettingsKeyNotFound(EnumExceptionName.ERROR_VALIDATION_INVALID_SETTINGS_KEY.name(), key);
        }

        validators.get(enumKey).validate(value.toString());

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
