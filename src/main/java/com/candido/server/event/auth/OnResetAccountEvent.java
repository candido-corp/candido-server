package com.candido.server.event.auth;

import com.candido.server.domain.v1.account.Account;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResetAccountEvent extends ApplicationEvent {

    private final Account account;

    private final String resetToken;

    private final String appUrl;

    public OnResetAccountEvent(Object source, final Account account, String resetToken, String appUrl) {
        super(source);
        this.account = account;
        this.resetToken = resetToken;
        this.appUrl = appUrl;
    }

}
