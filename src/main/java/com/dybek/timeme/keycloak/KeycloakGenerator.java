package com.dybek.timeme.keycloak;

import org.apache.commons.text.StringSubstitutor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakGenerator {
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String resource;

    @Value("${keycloak.credentials.secret}")
    private String secret;

    public String getRealm() {
        return realm;
    }

    public String getResource() {
        return resource;
    }

    public String getSecret() {
        return secret;
    }

    public Keycloak build() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .username("spring-admin")
                .password("passwd")
                .realm(realm)
                .clientId(resource)
                .clientSecret(secret)
                .build();
    }

    public String users() {
        Map<String, String> stringValues = new HashMap<>();
        stringValues.put("keycloakServerUrl", keycloakServerUrl);
        stringValues.put("realm", realm);
        return new StringSubstitutor(stringValues).replace("${keycloakServerUrl}/admin/realms/${realm}/users/");
    }
}
