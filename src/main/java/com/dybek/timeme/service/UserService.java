package com.dybek.timeme.service;

import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.KeycloakUserCreationFailedException;
import com.dybek.timeme.keycloak.KeycloakUserService;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collections;

@Service
public class UserService {
    private final KeycloakUserService keycloakUserService;

    public UserService(KeycloakUserService keycloakUserService) {
        this.keycloakUserService = keycloakUserService;
    }

    public UserRepresentation create(UserDTO userDTO) throws KeycloakUserCreationFailedException {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.getPassword());
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloakUserService.createUser(user);

        if(!HttpStatus.valueOf(response.getStatus()).is2xxSuccessful()) {
            throw new KeycloakUserCreationFailedException(response.readEntity(String.class).replace("\"", ""));
        }

        return user;
    }

}
