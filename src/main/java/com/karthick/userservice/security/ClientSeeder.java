package com.karthick.userservice.security;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Component
public class ClientSeeder implements ApplicationRunner {

    private final RegisteredClientRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Value("${oauth2.client.secret}")
    private String clientSecret;

    public ClientSeeder(RegisteredClientRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(repository.findByClientId("product-service-client") != null) {
            return; // already seeded
        }

        repository.save(RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("product-service-client")
                        .clientSecret(encoder.encode(clientSecret))
                        .scope("product.read")
                        .scope("product.write")
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:8080/login/oauth2/code/product-service-client-oidc")
                        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                        .build());
    }
}
