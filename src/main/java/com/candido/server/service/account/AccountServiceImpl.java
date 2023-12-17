package com.candido.server.service.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.AccountRepository;
import com.candido.server.domain.v1.account.AccountStatus;
import com.candido.server.domain.v1.account.AccountStatusEnum;
import com.candido.server.exception._common.BTExceptionName;
import com.candido.server.exception.account.AccountNotFoundException;
import com.candido.server.exception.account.PasswordsDoNotMatchException;
import com.candido.server.service.auth.token.TokenService;
import com.candido.server.validation.password.PasswordConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            account.setStatus(new AccountStatus(AccountStatusEnum.Verified.getStatusId()));
            save(account);
            tokenService.revokeAllAccountTokens(account);
        });
    }

    @Override
    public void editPassword(String email, String currentPassword, String password, String confirmPassword) {
        Account account = findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(BTExceptionName.ACCOUNT_NOT_FOUND.name()));

        // Controllo che l'email corrente sia giusta
        if(!passwordEncoder.matches(currentPassword, account.getPassword()))
            throw new PasswordsDoNotMatchException(BTExceptionName.CURRENT_PASSWORD_DOES_NOT_MATCH.name());

        // Controllo che la password soddisfi i requisiti minimi
        PasswordConstraintValidator.isValid(password);
        if (!password.equals(confirmPassword))
            throw new PasswordsDoNotMatchException(BTExceptionName.AUTH_PASSWORDS_DO_NOT_MATCH.name());

        account.setPassword(passwordEncoder.encode(password));
        save(account);
    }

}
