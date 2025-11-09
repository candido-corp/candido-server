package com.candido.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;

@Data
@EnableAsync
@Configuration
@ConfigurationProperties(prefix = "application")
public class ConfigAppProperties {

    private final List<String> authorizedRedirectUris = new ArrayList<>();

    private final Security security = new Security();

    @Data
    public static class Security {
        private final Jwt jwt = new Jwt();

        @Data
        public static class Jwt {
            private String issuer;
            private String secretKey;
            private int expiration;
            private final RefreshToken refreshToken = new RefreshToken();
            private final RegistrationToken registrationToken = new RegistrationToken();
            private final ResetToken resetToken = new ResetToken();

            @Data
            public static class RefreshToken {
                private int expiration;
            }

            @Data
            public static class RegistrationToken {
                private int expiration;
            }

            @Data
            public static class ResetToken {
                private int expiration;
            }

        }

    }
}
