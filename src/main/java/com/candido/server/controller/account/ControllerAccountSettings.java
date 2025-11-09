package com.candido.server.controller.account;

import com.candido.server.dto.v1.util.AccountSettingsDto;
import com.candido.server.service.business.account.settings.BusinessAccountSettingsService;
import com.candido.server.validation.annotations.VerifiedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/settings")
public class ControllerAccountSettings {

    private final BusinessAccountSettingsService businessAccountSettingsService;

    @Autowired
    public ControllerAccountSettings(
            BusinessAccountSettingsService businessAccountSettingsService
    ) {
        this.businessAccountSettingsService = businessAccountSettingsService;
    }

    @VerifiedUser
    @GetMapping
    public ResponseEntity<List<AccountSettingsDto>> getAccountSettings(
            Authentication authentication
    ) {
        List<AccountSettingsDto> accountSettingsDtos = businessAccountSettingsService.getAccountSettings(authentication);
        return ResponseEntity.ok(accountSettingsDtos);
    }

    @VerifiedUser
    @PutMapping
    public ResponseEntity<Void> updateAccountSettings(
            Authentication authentication,
            @RequestBody AccountSettingsDto accountSettingsDto
    ) {
        businessAccountSettingsService.updateAccountSettings(authentication, accountSettingsDto);
        return ResponseEntity.noContent().build();
    }

}
