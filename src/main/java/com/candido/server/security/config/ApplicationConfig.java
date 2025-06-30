package com.candido.server.security.config;

import com.candido.server.domain.v1.account.AccountRepository;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.security.auth.ExceptionAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AccountRepository accountRepository;

    /**
     * Defines the UserDetailsService used to load user information by username (email).
     *
     * @return the configured UserDetailsService instance.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var account = accountRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_NOT_FOUND.name()));

            if (!account.isEnabled()) {
                throw new ExceptionAuth("Error.");
            }

            return account;
        };
    }

    /**
     * Configures the AuthenticationProvider using a DaoAuthenticationProvider implementation.
     * It sets the user details service and the password encoder used for authentication.
     *
     * @return the configured AuthenticationProvider instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides the AuthenticationManager used for processing authentication requests.
     *
     * @param config the authentication configuration containing the manager.
     * @return the configured AuthenticationManager instance.
     * @throws Exception in case of error during retrieval.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides the password encoder used to hash and verify user passwords.
     *
     * @return the configured PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
