package com.dybek.timeme.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class KeycloakCustomBuilder {
    private final Keycloak keycloakInstance;
    private final String realm;

    @Autowired
    public KeycloakCustomBuilder(
            @Value("${keycloak.auth-server-url}") String keycloakServerUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.resource}") String resource,
            @Value("${keycloak.credentials.secret}") String secret
    ) {
        this.realm = realm;
        keycloakInstance = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .username("spring-admin")
                .password("passwd")
                .realm(realm)
                .clientId(resource)
                .clientSecret(secret)
                .build();
    }

    public RealmResource realm() {
        return keycloakInstance.realm(realm);
    }
}
