package com.candido.server.controller.account.user;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUserDto;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.mapper.AccountMapperService;
import com.candido.server.service.base.mapper.UserMapperService;
import com.candido.server.service.base.user.UserService;
import com.candido.server.validation.annotations.VerifiedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me/details")
public class ControllerUser {

    private final AccountService accountService;
    private final UserMapperService userMapper;
    private final UserService userService;

    @Autowired
    public ControllerUser(
            AccountService accountService,
            AccountMapperService accountMapper,
            UserMapperService userMapper,
            UserService userService
    ) {
        this.accountService = accountService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @VerifiedUser
    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        UserDto userDto = userMapper.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> postUserInfo(
            Authentication authentication,
            @RequestBody RequestUpdateUserDto requestUpdateUserDto
    ) {
        // TODO: If an application is open the user can't change the name.

        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        UserDto userDto = userMapper.userToUserDto(userService.save(user, requestUpdateUserDto));

        return ResponseEntity.ok(userDto);
    }

}
