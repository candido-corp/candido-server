package com.candido.server.controller.account.user;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.dto.v1.response.geo.ResponseUserAddress;
import com.candido.server.service.base.geo.AddressService;
import com.candido.server.service.base.mapper.AddressMapperService;
import com.candido.server.service.base.user.UserService;
import com.candido.server.validation.annotations.VerifiedUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/details/addresses")
public class ControllerUserAddress {

    private final AddressMapperService addressMapper;
    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public ControllerUserAddress(
            AddressMapperService addressMapper,
            UserService userService,
            AddressService addressService) {
        this.addressMapper = addressMapper;
        this.userService = userService;
        this.addressService = addressService;
    }

    @VerifiedUser
    @GetMapping
    public ResponseEntity<List<ResponseUserAddress>> getUserAddresses(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        List<ResponseUserAddress> responseUserAddressList = addressService.getAllActiveAddressesByUserId(user.getId())
                .stream()
                .map(addressMapper::addressToUserAddressDto)
                .toList();

        return ResponseEntity.ok(responseUserAddressList);
    }

    @VerifiedUser
    @PostMapping
    public ResponseEntity<ResponseUserAddress> createUserAddresses(
            Authentication authentication,
            @Valid @RequestBody RequestAddress requestUserAddressDto
    ) {
        Account account = (Account) authentication.getPrincipal();
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        Address address = addressService.saveAddress(user.getId(), null, requestUserAddressDto);

        if (address == null) return ResponseEntity.noContent().build();

        ResponseUserAddress responseUserAddress = addressMapper.addressToUserAddressDto(address);
        return ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @PutMapping("/{addressId}")
    public ResponseEntity<ResponseUserAddress> putUserAddresses(
            Authentication authentication,
            @Valid @RequestBody RequestAddress requestUserAddressDto,
            @PathVariable("addressId") int addressId
    ) {
        Account account = (Account) authentication.getPrincipal();
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        Address address = addressService.saveAddress(user.getId(), addressId, requestUserAddressDto);

        if (address == null) return ResponseEntity.noContent().build();

        ResponseUserAddress responseUserAddress = addressMapper.addressToUserAddressDto(address);
        return ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteUserAddresses(
            Authentication authentication,
            @PathVariable("addressId") Integer addressId
    ) {
        Account account = (Account) authentication.getPrincipal();
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        Address address = addressService.getAddressByIdAndUserIdOrThrow(addressId, user.getId());
        if (address == null) return ResponseEntity.notFound().build();
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

}
