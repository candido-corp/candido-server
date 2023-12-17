package com.candido.server.service.account;

import com.candido.server.domain.v1.account.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(int accountId);
    Optional<Account> findByEmail(String email);
    Account save(Account account);
    void enableAccount(int accountId);
    void editPassword(String email, String currentPassword, String password, String confirmPassword);
}
