package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.*;
import com.candido.server.domain.v1.provider.AuthProviderEnum;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestAccountSettings;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.util.AccountUserPairDto;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.*;
import com.candido.server.service.base.auth.provider.AuthProviderService;
import com.candido.server.service.base.auth.token.TokenService;
import com.candido.server.service.base.user.UserService;
import com.candido.server.validation.email.EmailConstraintValidator;
import com.candido.server.validation.password.PasswordConstraintValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthProviderService authProviderService;

    private final UserService userService;

    private final AccountSettingsService accountSettingsService;

    @Autowired
    public AccountServiceImpl(
            AccountRepository accountRepository,
            AccountRoleRepository accountRoleRepository,
            TokenService tokenService,
            PasswordEncoder passwordEncoder,
            AuthProviderService authProviderService,
            UserService userService,
            AccountSettingsService accountSettingsService
    ) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authProviderService = authProviderService;
        this.userService = userService;
        this.accountSettingsService = accountSettingsService;
    }

    @Override
    public Optional<Account> findById(int accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account findAccountByEmailOrThrow(String email) {
        var account = findByEmail(email).orElseThrow(() ->
                new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_NOT_FOUND.name(), "Email: " + email)
        );

        if (!account.isEnabled())
            throw new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_DISABLED.name(), "Email: " + email);

        return account;
    }

    @Override
    public Account findAccountByIdOrThrow(int accountId) {
        var account = findById(accountId).orElseThrow(() ->
                new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_NOT_FOUND.name(), "ID: " + accountId)
        );

        if (!account.isEnabled())
            throw new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_DISABLED.name(), "ID: " + accountId);

        return account;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void enableAccount(int accountId) {
        findById(accountId).ifPresent(account -> {
            account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
            save(account);
            tokenService.revokeAllAccountTokens(account);
        });
    }

    @Override
    public void editPassword(String email, String currentPassword, String password, String confirmPassword) {
        Account account = findByEmail(email)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_NOT_FOUND.name()));

        // Controllo che l'email corrente sia giusta
        if(!passwordEncoder.matches(currentPassword, account.getPassword()))
            throw new ExceptionPasswordsDoNotMatch(EnumExceptionName.ERROR_VALIDATION_CURRENT_PASSWORD_DOES_NOT_MATCH.name());

        // Controllo che la password soddisfi i requisiti minimi
        PasswordConstraintValidator.isValid(password);
        if (!password.equals(confirmPassword))
            throw new ExceptionPasswordsDoNotMatch(EnumExceptionName.ERROR_VALIDATION_PASSWORDS_DO_NOT_MATCH.name());

        account.setPassword(passwordEncoder.encode(password));
        save(account);
    }

    @Override
    public void activateAccount(Account account) {
        // Abilito l'account
        account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        save(account);
    }

    @Override
    public void saveAccountSettings(int accountId, List<RequestAccountSettings<?>> settings) {
        settings.forEach(setting -> accountSettingsService.saveAccountSetting(accountId, setting.key(), setting.value()));
    }

    @Transactional
    @Override
    public AccountUserPairDto createAccount(RequestRegister request) {
        // Verifica l'esistenza di un account duplicato
        var duplicateAccount = findByEmail(request.email());
        if (duplicateAccount.isPresent())
            throw new ExceptionDuplicateAccount(EnumExceptionName.ERROR_BUSINESS_DUPLICATE_USERNAME_ACCOUNT.name());

        // Verifica la validità dell'email
        if (!EmailConstraintValidator.isValid(request.email()))
            throw new ExceptionInvalidEmailAccount(EnumExceptionName.ERROR_VALIDATION_INVALID_EMAIL.name());

        // Verifica che la password soddisfi i requisiti minimi e corrisponda alla conferma
        PasswordConstraintValidator.isValid(request.password());
        if (!request.password().equals(request.confirmPassword()))
            throw new ExceptionPasswordsDoNotMatch(EnumExceptionName.ERROR_VALIDATION_PASSWORDS_DO_NOT_MATCH.name());

        AccountRole role = accountRoleRepository.findById(AccountRoleEnum.USER_NOT_VERIFIED.getRoleId())
                .orElseThrow(() -> new ExceptionAccountRoleNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_ROLE_NOT_FOUND.name()));

        // Creazione dell'account
        var account = Account.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .createdAt(LocalDateTime.now())
                .status(new AccountStatus(AccountStatusEnum.PENDING.getStatusId()))
                .build();

        // Salvataggio dell'account
        var savedAccount = save(account);

        // Creazione e salvataggio dell'utente associato all'account
        var user = User.builder()
                .accountId(savedAccount.getId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .createdAt(LocalDateTime.now())
                .build();

        userService.save(user);

        // Aggiunta del provider di autenticazione
        authProviderService.addProviderToAccount(AuthProviderEnum.LOCAL.getProviderId(), savedAccount.getId());

        return new AccountUserPairDto(savedAccount, user);
    }


}
