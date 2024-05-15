package com.candido.server.controller.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUserDto;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountNotFound;
import com.candido.server.exception.user.ExceptionUserNotFound;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.mapper.UserMapperService;
import com.candido.server.service.base.mapstruct.AccountMapper;
import com.candido.server.service.base.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class ControllerAccount {

    private final AccountService accountService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UserMapperService userMapper;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<AccountDto> getAccountInfo(Authentication authentication) {
        Account account = accountService.findByEmail(authentication.getName())
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));
        return ResponseEntity.ok(accountMapper.accountToAccountDto(account));
    }

    @GetMapping("/details")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        Account account = accountService.findByEmail(authentication.getName())
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        User user = userService.findUserByAccountId(account.getId())
                .orElseThrow(() -> new ExceptionUserNotFound(EnumExceptionName.USER_NOT_FOUND.name()));

        UserDto userDto = userMapper.userToUserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/details")
    public ResponseEntity<UserDto> postUserInfo(
            Authentication authentication,
            @RequestBody RequestUpdateUserDto requestUpdateUserDto
    ) {
        // TODO: Modifica nome e cognome ogni X giorni. La prima volta è possibile cambiarlo subito.
        // TODO: Se c'è una candidatura aperta non può modificare il nome e cognome.

        Account account = accountService.findByEmail(authentication.getName())
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        User user = userService.findUserByAccountId(account.getId())
                .orElseThrow(() -> new ExceptionUserNotFound(EnumExceptionName.USER_NOT_FOUND.name()));

        UserDto userDto = userMapper.userToUserDto(userService.save(user, requestUpdateUserDto));

        return ResponseEntity.ok(userDto);
    }

}
