package com.dybek.timeme.keycloak;

import org.springframework.stereotype.Repository;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.util.*;

@Repository
public class KeycloakUserRepository {
    private final KeycloakGenerator keycloakGenerator;
    public KeycloakUserRepository(KeycloakGenerator keycloakGenerator) {
        this.keycloakGenerator = keycloakGenerator;
    }

    public UserRepresentation find(UUID id) {
        return keycloakGenerator
                .build()
                .realm(keycloakGenerator.getRealm())
                .users()
                .get(id.toString())
                .toRepresentation();
    }

    public Response create(UserRepresentation user) {
        return keycloakGenerator
                .build()
                .realm(keycloakGenerator.getRealm())
                .users()
                .create(user);
    }
}
