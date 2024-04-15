package com.candido.server.service.account;

import com.candido.server.domain.v1.account.*;
import com.candido.server.domain.v1.provider.AuthProviderEnum;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.exception._common.ExceptionNameEnum;
import com.candido.server.exception.account.AccountNotFoundException;
import com.candido.server.exception.account.DuplicateAccountException;
import com.candido.server.exception.account.InvalidEmailAccountException;
import com.candido.server.exception.account.PasswordsDoNotMatchException;
import com.candido.server.service.auth.provider.AuthProviderService;
import com.candido.server.service.auth.token.TokenService;
import com.candido.server.service.user.UserService;
import com.candido.server.validation.email.EmailConstraintValidator;
import com.candido.server.validation.password.PasswordConstraintValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthProviderService authProviderService;

    @Autowired
    private UserService userService;

    @Override
    public Optional<Account> findById(int accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
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
                .orElseThrow(() -> new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name()));

        // Controllo che l'email corrente sia giusta
        if(!passwordEncoder.matches(currentPassword, account.getPassword()))
            throw new PasswordsDoNotMatchException(ExceptionNameEnum.CURRENT_PASSWORD_DOES_NOT_MATCH.name());

        // Controllo che la password soddisfi i requisiti minimi
        PasswordConstraintValidator.isValid(password);
        if (!password.equals(confirmPassword))
            throw new PasswordsDoNotMatchException(ExceptionNameEnum.AUTH_PASSWORDS_DO_NOT_MATCH.name());

        account.setPassword(passwordEncoder.encode(password));
        save(account);
    }

    @Override
    public void activateAccount(Account account) {
        // Abilito l'account
        account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        save(account);
    }

    @Transactional
    @Override
    public Account createAccount(RequestRegister request) {
        // Verifica l'esistenza di un account duplicato
        var duplicateAccount = findByEmail(request.email());
        if (duplicateAccount.isPresent())
            throw new DuplicateAccountException(ExceptionNameEnum.AUTH_REGISTRATION_DUPLICATE_USERNAME_ACCOUNT.name());

        // Verifica la validità dell'email
        if (!EmailConstraintValidator.isValid(request.email()))
            throw new InvalidEmailAccountException(ExceptionNameEnum.INVALID_EMAIL.name());

        // Verifica che la password soddisfi i requisiti minimi e corrisponda alla conferma
        PasswordConstraintValidator.isValid(request.password());
        if (!request.password().equals(request.confirmPassword()))
            throw new PasswordsDoNotMatchException(ExceptionNameEnum.AUTH_PASSWORDS_DO_NOT_MATCH.name());

        // Creazione dell'account
        var account = Account.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountRole(new AccountRole(AccountRoleEnum.USER.getRoleId()))
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

        return savedAccount;
    }


}
