package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.util.AccountUserPair;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(int accountId);
    Optional<Account> findByEmail(String email);
    Account findAccountByEmailOrThrow(String email);
    Account findAccountByIdOrThrow(int accountId);
    Account save(Account account);
    void enableAccount(int accountId);
    void editPassword(String email, String currentPassword, String password, String confirmPassword);
    void activateAccount(Account account);


    AccountUserPair createAccount(RequestRegister request);
}
