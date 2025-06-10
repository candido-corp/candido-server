package com.candido.server.controller.account.user;

import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.dto.v1.response.geo.ResponseUserAddress;
import com.candido.server.service.business.address.BusinessAddressService;
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

    private final BusinessAddressService businessAddressService;

    @Autowired
    public ControllerUserAddress(
            BusinessAddressService businessAddressService
    ) {
        this.businessAddressService = businessAddressService;
    }

    @VerifiedUser
    @GetMapping
    public ResponseEntity<List<ResponseUserAddress>> getUserAddresses(Authentication authentication) {
        List<ResponseUserAddress> responseUserAddressList = businessAddressService.getUserAddresses(authentication);
        return ResponseEntity.ok(responseUserAddressList);
    }

    @VerifiedUser
    @PostMapping
    public ResponseEntity<ResponseUserAddress> createUserAddress(
            Authentication authentication,
            @Valid @RequestBody RequestAddress requestUserAddressDto
    ) {
        ResponseUserAddress responseUserAddress = businessAddressService.createUserAddress(
                authentication, requestUserAddressDto
        );
        return ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @PutMapping("/{addressId}")
    public ResponseEntity<ResponseUserAddress> putUserAddress(
            Authentication authentication,
            @Valid @RequestBody RequestAddress requestUserAddressDto,
            @PathVariable("addressId") int addressId
    ) {
        ResponseUserAddress responseUserAddress = businessAddressService.updateUserAddress(
                authentication, requestUserAddressDto, addressId
        );
        return ResponseEntity.ok(responseUserAddress);
    }

    @VerifiedUser
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteUserAddresses(
            Authentication authentication,
            @PathVariable("addressId") Integer addressId
    ) {
        businessAddressService.deleteAddress(authentication, addressId);
        return ResponseEntity.noContent().build();
    }

}
