package com.artist.accounts;

import com.artist.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@EnableFeignClients
@OpenAPIDefinition(
        info = @Info(
                title = "MicroSevices Rest Api Documentaion",
                version = "v1",
                description = "Microservices course Documentation",
                contact = @Contact(
                        name = "Rakesh Rane",
                        email = "rakesh@1992",
                        url = "localhost://www."
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://"
                )

        ),
        externalDocs = @ExternalDocumentation(
                description = "search for more related to artist",
                url = "https://ggguysdgviu"
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
