package com.candido.server.controller.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.mapper.AccountMapperService;
import com.candido.server.service.base.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me")
public class ControllerAccount {

    private final AccountService accountService;
    private final AccountMapperService accountMapper;
    private final UserService userService;

    @Autowired
    public ControllerAccount(
            AccountService accountService,
            AccountMapperService accountMapper,
            UserService userService
    ) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<AccountDto> getAccountInfo(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        return ResponseEntity.ok(accountMapper.accountToAccountDto(account, user));
    }

}
