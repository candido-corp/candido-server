package com.candido.server.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = "Candido API",
                version = "${application.api.version}",
                contact = @Contact(
                        name = "Candido",
                        email = "support@candido.com",
                        url = "https://www.candido.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "${application.tos.uri}",
                description = "${application.api.description}"
        ),
        servers = {
                @Server(url = "${application.api.server.development.url}", description = "development"),
                @Server(url = "${application.api.server.production.url}", description = "production")
        }
)
public class ConfigOpenAPI30Security {}