package com.candido.server.controller.account.user;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import com.candido.server.dto.v1.request.geo.RequestAddress;
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
@RequestMapping("/api/v1/me/details/addresses")
public class ControllerUserAddress {

    private final AccountService accountService;
    private final AddressMapperService addressMapper;
    private final UserService userService;

    @Autowired
    public ControllerUserAddress(
            AccountService accountService,
            AddressMapperService addressMapper,
            UserService userService
    ) {
        this.accountService = accountService;
        this.addressMapper = addressMapper;
        this.userService = userService;
    }

    @VerifiedUser
    @GetMapping
    public ResponseEntity<ResponseUserAddress> getUserAddresses(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        ResponseUserAddress responseUserAddress = addressMapper.addressToUserAddressDto(user.getAddress());
        return responseUserAddress == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @PutMapping
    public ResponseEntity<ResponseUserAddress> putUserAddresses(
            Authentication authentication,
            @RequestBody RequestAddress requestUserAddressDto
    ) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        user = userService.updateAddress(user, requestUserAddressDto);
        ResponseUserAddress responseUserAddress = addressMapper.addressToUserAddressDto(user.getAddress());
        return responseUserAddress == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @DeleteMapping
    public ResponseEntity<Void> deleteUserAddresses(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        userService.deleteAddress(user);
        return ResponseEntity.noContent().build();
    }

}
