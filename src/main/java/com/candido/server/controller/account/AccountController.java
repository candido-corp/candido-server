package com.candido.server.controller.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.request.account.RequestEditAccountName;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.exception._common.BTExceptionName;
import com.candido.server.exception.account.AccountNotFoundException;
import com.candido.server.service.account.AccountService;
import com.candido.server.service.mapstruct.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Autowired
    AccountMapper accountMapper;

    @GetMapping
    public ResponseEntity<AccountDto> getAccountInfo(Authentication authentication) {
        Account account = accountService.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccountNotFoundException(BTExceptionName.ACCOUNT_NOT_FOUND.name()));
        return ResponseEntity.ok(accountMapper.accountToAccountDto(account));
    }

}
