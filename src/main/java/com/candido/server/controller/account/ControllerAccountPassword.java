package com.candido.server.controller.account;

import com.candido.server.dto.v1.request.account.RequestEditAccountPassword;
import com.candido.server.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me/password")
@RequiredArgsConstructor
public class ControllerAccountPassword {

    private final AccountService accountService;

    @PutMapping
    public ResponseEntity<Void> editAccountPassword(@RequestBody RequestEditAccountPassword request, Authentication authentication) {
        accountService.editPassword(authentication.getName(), request.currentPassword(), request.password(), request.confirmPassword());
        return ResponseEntity.noContent().build();
    }

}
