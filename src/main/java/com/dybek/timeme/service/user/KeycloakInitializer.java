package com.dybek.timeme.service.user;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class KeycloakInitializer {
    private final Keycloak keycloakInstance;
    private final String realm;

    @Autowired
    public KeycloakInitializer(
            @Value("${keycloak.auth-server-url}") String keycloakServerUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.resource}") String resource,
            @Value("${keycloak.credentials.secret}") String secret
    ) {
        this.realm = realm;
        keycloakInstance = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .username("admin")
                .password("passwd")
                .realm("master")
                .clientId("admin-cli")
                .clientSecret(secret)
                .build();
    }

    public RealmResource realm() {
        return keycloakInstance.realm(realm);
    }
}
