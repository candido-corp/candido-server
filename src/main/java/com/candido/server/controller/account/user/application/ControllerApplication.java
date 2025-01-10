package com.candido.server.controller.account.user.application;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.response.application.ResponseUserApplication;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.application.ApplicationService;
import com.candido.server.validation.annotations.VerifiedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/applications")
public class ControllerApplication {

    private final ApplicationService applicationService;
    private final AccountService accountService;

    @Autowired
    public ControllerApplication(
            ApplicationService applicationService,
            AccountService accountService
    ) {
        this.applicationService = applicationService;
        this.accountService = accountService;
    }

    @VerifiedUser
    @GetMapping
    public ResponseEntity<List<ResponseUserApplication>> getOpenApplications(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        return ResponseEntity.ok(applicationService.findAllByAccountId(account.getId()));
    }

}
