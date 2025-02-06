package com.candido.server.event.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResetAccountCompletedEvent extends ApplicationEvent {

    private final Account account;
    private final transient User user;

    public OnResetAccountCompletedEvent(
            Object source,
            Account account,
            User user
    ) {
        super(source);
        this.account = account;
        this.user = user;
    }

}
