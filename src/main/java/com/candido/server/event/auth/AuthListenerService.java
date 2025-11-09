package com.candido.server.event.auth;

public interface AuthListenerService {
    void handleOnEmailRegistrationEvent(OnEmailRegistrationEvent event);
    void handleOnCodeRegistrationEvent(OnCodeRegistrationEvent event);
    void handleOnRegistrationCompletedEvent(OnRegistrationCompletedEvent event);
    void handleOnResetPasswordEvent(OnResetAccountEvent event);
    void handleOnResetPasswordCompletedEvent(OnResetAccountCompletedEvent event);
}
