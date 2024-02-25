package com.candido.server.event.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompletedEvent extends ApplicationEvent {

    private final Account account;

    private final User user;

    public OnRegistrationCompletedEvent(Object source, final Account account, final User user) {
        super(source);
        this.account = account;
        this.user = user;
    }

}
