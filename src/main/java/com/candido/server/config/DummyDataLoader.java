package com.candido.server.config;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DummyDataLoader {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    DummyDataLoader(
            PasswordEncoder passwordEncoder
    ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadData(AccountRepository repository) {
        return args -> {
            Optional<Account> optionalAccount = repository.findByEmail("admin@candidocorp.com");
            optionalAccount.ifPresent(account -> {
                account.setPassword(passwordEncoder.encode("password"));
                repository.save(account);
            });
        };
    }
}