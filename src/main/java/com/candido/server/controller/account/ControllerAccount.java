package com.candido.server.controller.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUserDto;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.mapper.AccountMapperService;
import com.candido.server.service.base.mapper.UserMapperService;
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
    private final UserMapperService userMapper;
    private final UserService userService;

    @Autowired
    public ControllerAccount(
            AccountService accountService,
            AccountMapperService accountMapper,
            UserMapperService userMapper,
            UserService userService
    ) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<AccountDto> getAccountInfo(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        return ResponseEntity.ok(accountMapper.accountToAccountDto(account));
    }

    @GetMapping("/details")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        UserDto userDto = userMapper.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/details")
    public ResponseEntity<UserDto> postUserInfo(
            Authentication authentication,
            @RequestBody RequestUpdateUserDto requestUpdateUserDto
    ) {
        // TODO: Se c'è una candidatura aperta non può modificare il nome e cognome.

        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        UserDto userDto = userMapper.userToUserDto(userService.save(user, requestUpdateUserDto));

        return ResponseEntity.ok(userDto);
    }

}
