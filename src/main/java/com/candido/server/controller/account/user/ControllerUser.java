package com.candido.server.controller.account.user;

import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.service.business.user.BusinessUserService;
import com.candido.server.validation.annotations.VerifiedUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me/details")
public class ControllerUser {

    private final BusinessUserService businessUserService;

    @Autowired
    public ControllerUser(
            BusinessUserService businessUserService
    ) {
        this.businessUserService = businessUserService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        UserDto userDto = businessUserService.getUserDtoWithPrimaryAddress(authentication);
        return ResponseEntity.ok(userDto);
    }

    @VerifiedUser
    @PutMapping
    public ResponseEntity<UserDto> editUserInfo(
            Authentication authentication,
            @Valid @RequestBody RequestUpdateUser requestUpdateUser
    ) {
        UserDto userDto = businessUserService.editUserInfo(authentication, requestUpdateUser);
        return ResponseEntity.ok(userDto);
    }

}
