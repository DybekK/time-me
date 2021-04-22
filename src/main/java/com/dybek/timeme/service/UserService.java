package com.dybek.timeme.service;

import com.dybek.timeme.datasource.entity.Workspace;
import com.dybek.timeme.datasource.entity.WorkspaceUser;
import com.dybek.timeme.datasource.repository.WorkspaceRepository;
import com.dybek.timeme.datasource.repository.WorkspaceUserRepository;
import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.KeycloakUserCreationFailedException;
import com.dybek.timeme.keycloak.KeycloakUserService;
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

@Service
public class UserService {
    private final KeycloakUserService keycloakUserService;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkspaceRepository workspaceRepository;

    public UserService(KeycloakUserService keycloakUserService, WorkspaceUserRepository workspaceUserRepository, WorkspaceRepository workspaceRepository) {
        this.keycloakUserService = keycloakUserService;
        this.workspaceUserRepository = workspaceUserRepository;
        this.workspaceRepository = workspaceRepository;
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

        Workspace workspace = new Workspace();
        workspaceRepository.create(workspace);

        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setWorkspaceId(workspace.getId());
        workspaceUser.setUserId(userId);
        workspaceUser.setNickname(user.getUsername());
        workspaceUserRepository.create(workspaceUser);

        return "User has been created";
    }

}
