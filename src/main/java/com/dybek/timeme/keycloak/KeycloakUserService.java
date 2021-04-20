package com.dybek.timeme.keycloak;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.UUID;

@Service
public class KeycloakUserService {
    private final KeycloakUserRepository keycloakUserRepository;

    public KeycloakUserService(KeycloakUserRepository keycloakUserRepository) {
        this.keycloakUserRepository = keycloakUserRepository;
    }

    public UserRepresentation getUser(UUID id) {
        return keycloakUserRepository.find(id);
    }

    public Response createUser(UserRepresentation userRepresentation) {
        return keycloakUserRepository.create(userRepresentation);
    }
}
