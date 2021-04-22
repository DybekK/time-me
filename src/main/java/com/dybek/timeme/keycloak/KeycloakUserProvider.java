package com.dybek.timeme.keycloak;

import org.springframework.stereotype.Repository;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.util.*;

@Repository
public class KeycloakUserProvider {
    private final KeycloakCustomBuilder keycloakBuilder;
    public KeycloakUserProvider(KeycloakCustomBuilder keycloakBuilder) {
        this.keycloakBuilder = keycloakBuilder;
    }

    public UserRepresentation find(UUID id) {
        return keycloakBuilder
                .realm()
                .users()
                .get(id.toString())
                .toRepresentation();
    }

    public Response create(UserRepresentation user) {
        return keycloakBuilder
                .realm()
                .users()
                .create(user);
    }
}
