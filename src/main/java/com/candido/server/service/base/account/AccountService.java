package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.request.auth.RequestRegister;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(int accountId);
    Optional<Account> findByEmail(String email);
    Account findAccountByEmailOrThrow(String email);
    Account save(Account account);
    void enableAccount(int accountId);
    void editPassword(String email, String currentPassword, String password, String confirmPassword);
    void activateAccount(Account account);


    Account createAccount(RequestRegister request);
}
