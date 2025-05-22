package com.candido.server.controller.account.user;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.application.ApplicationService;
import com.candido.server.service.base.mapper.UserMapperService;
import com.candido.server.service.base.user.UserService;
import com.candido.server.validation.annotations.VerifiedUser;
import jakarta.validation.Valid;
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
    private final ApplicationService applicationService;

    @Autowired
    public ControllerUser(
            AccountService accountService,
            UserMapperService userMapper,
            UserService userService,
            ApplicationService applicationService
    ) {
        this.accountService = accountService;
        this.userMapper = userMapper;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        boolean userHasOpenApplications = applicationService.userHasOpenApplications(account.getId());
        UserDto userDto = userMapper.userToUserDto(user, account, userHasOpenApplications);
        return ResponseEntity.ok(userDto);
    }

    @VerifiedUser
    @PutMapping
    public ResponseEntity<UserDto> editUserInfo(
            Authentication authentication,
            @Valid @RequestBody RequestUpdateUser requestUpdateUser
    ) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        boolean userHasOpenApplications = applicationService.userHasOpenApplications(account.getId());
        boolean canChangeName = !userHasOpenApplications;
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        UserDto userDto = userMapper.userToUserDto(
                userService.save(user, requestUpdateUser, canChangeName),
                account,
                userHasOpenApplications
        );

        return ResponseEntity.ok(userDto);
    }

}
