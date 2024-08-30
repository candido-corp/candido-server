package com.candido.server.event.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnEmailRegistrationEvent extends ApplicationEvent {

    private final Account account;
    private final transient User user;
    private final String registrationToken;
    private final String appUrl;

    public OnEmailRegistrationEvent(
            Object source,
            final Account account,
            final User user,
            final String registrationToken,
            final String appUrl
    ) {
        super(source);
        this.account = account;
        this.user = user;
        this.registrationToken = registrationToken;
        this.appUrl = appUrl;
    }

}