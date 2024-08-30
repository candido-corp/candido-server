package com.candido.server.event.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResetAccountEvent extends ApplicationEvent {

    private final Account account;
    private final transient User user;
    private final String resetToken;
    private final String appUrl;

    public OnResetAccountEvent(
            Object source,
            final Account account,
            User user,
            String resetToken,
            String appUrl
    ) {
        super(source);
        this.account = account;
        this.user = user;
        this.resetToken = resetToken;
        this.appUrl = appUrl;
    }

}
