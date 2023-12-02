package com.candido.server.event;

import com.candido.server.repository.v1.candido.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartUpEvent {

    @Autowired
    AccountRepository accountRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartUpRun() {
    }

}
