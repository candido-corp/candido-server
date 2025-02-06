package com.candido.server.controller.account.user;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUserDto;
import com.candido.server.dto.v1.request.geo.RequestAddressDto;
import com.candido.server.dto.v1.response.geo.ResponseUserAddress;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.application.ApplicationService;
import com.candido.server.service.base.mapper.AccountMapperService;
import com.candido.server.service.base.mapper.AddressMapperService;
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
    private final AddressMapperService addressMapper;
    private final UserService userService;
    private final ApplicationService applicationService;

    @Autowired
    public ControllerUser(
            AccountService accountService,
            AccountMapperService accountMapper,
            UserMapperService userMapper,
            AddressMapperService addressMapper,
            UserService userService,
            ApplicationService applicationService
    ) {
        this.accountService = accountService;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @VerifiedUser
    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        boolean userHasOpenApplications = applicationService.userHasOpenApplications(account.getId());
        UserDto userDto = userMapper.userToUserDto(user, userHasOpenApplications);
        return ResponseEntity.ok(userDto);
    }

    @VerifiedUser
    @GetMapping("/addresses")
    public ResponseEntity<ResponseUserAddress> getUserAddresses(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        ResponseUserAddress responseUserAddress = addressMapper.addressToUserAddressDto(user.getAddress());
        return responseUserAddress == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @PutMapping
    public ResponseEntity<UserDto> postUserInfo(
            Authentication authentication,
            @RequestBody RequestUpdateUserDto requestUpdateUserDto
    ) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        boolean userHasOpenApplications = applicationService.userHasOpenApplications(account.getId());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        UserDto userDto = userMapper.userToUserDto(
                userService.save(user, requestUpdateUserDto, userHasOpenApplications),
                userHasOpenApplications
        );

        return ResponseEntity.ok(userDto);
    }

    @VerifiedUser
    @PutMapping("/addresses")
    public ResponseEntity<ResponseUserAddress> putUserAddresses(
            Authentication authentication,
            @RequestBody RequestAddressDto requestUserAddressDto
    ) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        user = userService.updateAddress(user, requestUserAddressDto);
        ResponseUserAddress responseUserAddress = addressMapper.addressToUserAddressDto(user.getAddress());
        return responseUserAddress == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @DeleteMapping("/addresses")
    public ResponseEntity<Void> deleteUserAddresses(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        userService.deleteAddress(user);
        return ResponseEntity.noContent().build();
    }

}
