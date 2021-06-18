package com.dybek.timeme.service;

import com.dybek.timeme.domain.tables.records.WorkspaceRecord;
import com.dybek.timeme.domain.tables.records.WorkspaceUserRecord;
import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.KeycloakUserCreationFailedException;
import org.jooq.DSLContext;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.UUID;

import static com.dybek.timeme.domain.Tables.*;

@Service
public class UserService {
    private final KeycloakUserService keycloakUserService;
    private final DSLContext dsl;

    public UserService(KeycloakUserService keycloakUserService, DSLContext dsl) {
        this.keycloakUserService = keycloakUserService;
        this.dsl = dsl;
    }

    private UUID getUserIdFromResponse(String uri) throws URISyntaxException {
        URI _uri = new URI(uri);
        String path = _uri.getPath();
        return UUID.fromString(path.substring(path.lastIndexOf('/') + 1));
    }

    @Transactional
    public String create(UserDTO userDTO) throws KeycloakUserCreationFailedException, URISyntaxException {
        // creates object for credentials of Keycloak user
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.getPassword());
        credential.setTemporary(false);

        // creates Keycloak user object and sets its credentials
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credential));

        // sends request to Keycloak API to create new user
        Response response = keycloakUserService.createUser(user);

        if(!HttpStatus.valueOf(response.getStatus()).is2xxSuccessful()) {
            // throws exception if error occurred, also removing \" from API response body
            throw new KeycloakUserCreationFailedException(response.readEntity(String.class).replace("\"", ""));
        }

        // gets id from newly created user, id is placed in headers
        UUID userId = getUserIdFromResponse(response.getStringHeaders().getFirst("Location"));

        WorkspaceRecord workspace = dsl.newRecord(WORKSPACE);
        workspace.store();

        WorkspaceUserRecord workspaceUser = dsl.newRecord(WORKSPACE_USER);
        workspaceUser.setUserId(userId);
        workspaceUser.setWorkspaceId(workspace.getId());
        workspaceUser.setNickname(userDTO.getUsername());
        workspaceUser.store();

        return "User has been created";
    }

}
