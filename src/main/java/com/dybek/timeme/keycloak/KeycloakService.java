package com.dybek.timeme.keycloak;

import com.dybek.timeme.datasource.entity.User;
import com.dybek.timeme.datasource.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringSubstitutor;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.representations.account.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;

@Service
public class KeycloakService {
    private final KeycloakRestTemplate keycloakRestTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakService(KeycloakRestTemplate keycloakRestTemplate, UserRepository userRepository) {
        this.keycloakRestTemplate = keycloakRestTemplate;
        this.userRepository = userRepository;
    }

    private String emptyUrl() {
        Map<String, String> stringValues = new HashMap<>();
        stringValues.put("keycloakServerUrl", keycloakServerUrl);
        stringValues.put("realm", realm);
        return new StringSubstitutor(stringValues).replace("${keycloakServerUrl}/admin/realms/${realm}/users/");
    }

    public UserRepresentation getUser(UUID id) {
        return keycloakRestTemplate
                .getForEntity(URI.create(emptyUrl() + id.toString()), UserRepresentation.class)
                .getBody();
    }

    public List<UserRepresentation> getAllUsers() {
        UserRepresentation[] userRepresentations = keycloakRestTemplate
                .getForEntity(URI.create(emptyUrl()), UserRepresentation[].class)
                .getBody();
        assert userRepresentations != null;
        return Arrays.asList(userRepresentations);
    }

    public UserRepresentation createUser(UserRepresentation userRepresentation) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = objectMapper.writeValueAsString(userRepresentation);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        keycloakRestTemplate.postForObject(URI.create(emptyUrl()), request, String.class);
        return userRepresentation;
    }

    public <S extends UserRepresentation> UserRepresentation update(S entity) {
        return null;
    }

    public <S extends UserRepresentation> void delete(S entity) {

    }
}
