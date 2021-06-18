package com.dybek.timeme.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class KeycloakUserProvider {
    private final KeycloakInitializer keycloakInitializer;
    public KeycloakUserProvider(KeycloakInitializer keycloakBuilder) {
        this.keycloakInitializer = keycloakBuilder;
    }

    public UserRepresentation find(UUID id) {
        return keycloakInitializer
                .realm()
                .users()
                .get(id.toString())
                .toRepresentation();
    }

    public Response create(UserRepresentation user) {
        return keycloakInitializer
                .realm()
                .users()
                .create(user);
    }
}
