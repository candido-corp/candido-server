package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.request.account.RequestAccountSettings;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.util.AccountUserPairDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(int accountId);
    Optional<Account> findByEmail(String email);
    Account findAccountByEmailOrThrow(String email);
    Account findAccountByIdOrThrow(int accountId);
    Account save(Account account);
    void activateAccount(Account account);

    AccountUserPairDto createAccount(RequestRegister request);
}
