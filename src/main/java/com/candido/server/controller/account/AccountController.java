package com.candido.server.controller.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.exception._common.BTExceptionName;
import com.candido.server.exception.account.AccountNotFoundException;
import com.candido.server.exception.user.UserNotFoundException;
import com.candido.server.service.account.AccountService;
import com.candido.server.service.mapstruct.AccountMapper;
import com.candido.server.service.mapstruct.UserMapper;
import com.candido.server.service.user.UserService;
import lombok.RequiredArgsConstructor;
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

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<AccountDto> getAccountInfo(Authentication authentication) {
        Account account = accountService.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccountNotFoundException(BTExceptionName.ACCOUNT_NOT_FOUND.name()));
        return ResponseEntity.ok(accountMapper.accountToAccountDto(account));
    }

    @GetMapping("/details")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        Account account = accountService.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccountNotFoundException(BTExceptionName.ACCOUNT_NOT_FOUND.name()));

        User user = userService.findByAccountId(account.getId())
                .orElseThrow(() -> new UserNotFoundException(BTExceptionName.USER_NOT_FOUND.name()));

        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @PostMapping("/details")
    public ResponseEntity<UserDto> postUserInfo(Authentication authentication) {
        return null;
    }

}
