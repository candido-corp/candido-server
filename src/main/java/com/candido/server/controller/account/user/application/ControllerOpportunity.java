package com.candido.server.controller.account.user.application;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.response.application.ResponseApplicationForm;
import com.candido.server.service.base.application.ApplicationService;
import com.candido.server.validation.annotations.VerifiedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/opportunities")
public class ControllerOpportunity {

    private final ApplicationService applicationService;

    @Autowired
    public ControllerOpportunity(
            ApplicationService applicationService
    ) {
        this.applicationService = applicationService;
    }

    @VerifiedUser
    @GetMapping("/saved")
    public ResponseEntity<List<ResponseApplicationForm>> getSavedApplications(
            Authentication authentication
    ) {
        Account account = (Account) authentication.getPrincipal();
        return ResponseEntity.ok(applicationService.findAllApplicationsSavedByAccountId(account.getId()));
    }

}
