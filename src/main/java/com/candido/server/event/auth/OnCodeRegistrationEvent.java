package com.candido.server.event.auth;

import com.candido.server.domain.v1.account.Account;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnCodeRegistrationEvent extends ApplicationEvent {

    private final Account account;
    private final String registrationToken;
    private final String temporaryCode;
    private final String appUrl;

    public OnCodeRegistrationEvent(
            Object source,
            final Account account,
            final String registrationToken,
            final String temporaryCode,
            final String appUrl
    ) {
        super(source);
        this.account = account;
        this.registrationToken = registrationToken;
        this.temporaryCode = temporaryCode;
        this.appUrl = appUrl;
    }

}