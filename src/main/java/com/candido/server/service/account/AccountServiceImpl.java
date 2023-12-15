package com.candido.server.service.account;

import com.biotekna.doctor.exception._common.BTExceptionName;
import com.biotekna.doctor.exception.account.AccountNotFoundException;
import com.biotekna.doctor.exception.account.FirstNameEmptyException;
import com.biotekna.doctor.exception.account.LastNameEmptyException;
import com.biotekna.doctor.exception.account.PasswordsDoNotMatchException;
import com.biotekna.doctor.security.domain.account.Account;
import com.biotekna.doctor.security.domain.account.AccountRepository;
import com.biotekna.doctor.security.domain.account.AccountStatus;
import com.biotekna.doctor.security.domain.account.AccountStatusEnum;
import com.biotekna.doctor.service.auth.token.TokenService;
import com.biotekna.doctor.validation.password.PasswordConstraintValidator;
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
            account.setStatus(new AccountStatus(AccountStatusEnum.Enabled.getStatusId()));
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

    @Override
    public void editAccountName(String email, String firstName, String lastName) {
        // Controllo che il nome non sia vuoto
        if(firstName == null || firstName.isEmpty())
            throw new FirstNameEmptyException(BTExceptionName.FIRST_NAME_CAN_NOT_BE_EMPTY.name());

        // Controllo che il cognome non sia vuoto
        if(lastName == null || lastName.isEmpty())
            throw new LastNameEmptyException(BTExceptionName.LAST_NAME_CAN_NOT_BE_EMPTY.name());

        findByEmail(email).ifPresent(account -> {
            account.setFirstname(firstName);
            account.setLastname(lastName);
            save(account);
        });
    }
}
