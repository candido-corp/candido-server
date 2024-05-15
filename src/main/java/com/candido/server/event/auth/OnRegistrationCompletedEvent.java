package com.candido.server.event.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * This class represents the event that is published when a registration is completed.
 * It extends the ApplicationEvent class provided by Spring Framework.
 * It contains the account and user details associated with the registration.
 */
@Getter
public class OnRegistrationCompletedEvent extends ApplicationEvent {

    // The account associated with the registration
    private final Account account;

    // The user associated with the registration
    private final User user;

    /**
     * Constructor for the OnRegistrationCompletedEvent.
     *
     * @param source  the object on which the event initially occurred
     * @param account the account associated with the registration
     * @param user    the user associated with the registration
     */
    public OnRegistrationCompletedEvent(Object source, final Account account, final User user) {
        super(source);
        this.account = account;
        this.user = user;
    }

}