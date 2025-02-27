package com.candido.server.config;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@Profile("dev")
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
            Optional<Account> optionalAdminAccount = repository.findByEmail("admin@candidocorp.com");
            optionalAdminAccount.ifPresent(account -> {
                account.setPassword(passwordEncoder.encode("Password123@@"));
                repository.save(account);
            });

            Optional<Account> optionalClientAccount = repository.findByEmail("client@candidocorp.com");
            optionalClientAccount.ifPresent(account -> {
                account.setPassword(passwordEncoder.encode("Password123@@"));
                repository.save(account);
            });
        };
    }
}