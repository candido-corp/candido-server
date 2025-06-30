package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.util.AccountUserPairDto;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(Long accountId);
    Optional<Account> findByEmail(String email);
    Account findAccountByEmailOrThrow(String email);
    Account findAccountByIdOrThrow(Long accountId);
    Account save(Account account);
    Account activateAccount(Account account);

    AccountUserPairDto createAccount(RequestRegister request);
}
