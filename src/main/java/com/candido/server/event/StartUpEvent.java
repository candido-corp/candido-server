package com.candido.server.event;

import com.candido.server.domain.v1.candido.Address;
import com.candido.server.domain.v1.candido.User;
import com.candido.server.repository.v1.candido.AccountRepository;
import com.candido.server.repository.v1.candido.AddressRepository;
import com.candido.server.repository.v1.candido.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartUpEvent {

    @Autowired
    UserRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartUpRun() {
        List<User> list = repository.findAll();
        System.out.println(list);
    }

}
