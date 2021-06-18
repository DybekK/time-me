package com.dybek.timeme.service.user;

import com.dybek.timeme.domain.jooq.tables.pojos.Workspace;
import com.dybek.timeme.domain.jooq.tables.pojos.WorkspaceUser;
import com.dybek.timeme.domain.repository.WorkspaceRepository;
import com.dybek.timeme.domain.repository.WorkspaceUserRepository;
import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.KeycloakUserCreationFailedException;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;


@Service
public class UserService {
    private final KeycloakUserService keycloakUserService;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkspaceRepository workspaceRepository;

    public UserService
    (
        KeycloakUserService keycloakUserService,
        WorkspaceRepository workspaceRepository,
        WorkspaceUserRepository workspaceUserRepository
    )
    {
        this.keycloakUserService = keycloakUserService;
        this.workspaceRepository = workspaceRepository;
        this.workspaceUserRepository = workspaceUserRepository;
    }

    private UUID getUserIdFromResponse(String uri) throws URISyntaxException {
        URI _uri = new URI(uri);
        String path = _uri.getPath();
        return UUID.fromString(path.substring(path.lastIndexOf('/') + 1));
    }

    @Transactional
    public UserRepresentation create(UserDTO userDTO) throws KeycloakUserCreationFailedException, URISyntaxException {
        // creates object for credentials of Keycloak user
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.getPassword());
        credential.setTemporary(false);

        // creates Keycloak user object and sets its credentials
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
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
        workspaceRepository.insert(workspace);

        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setUserId(userId);
        workspaceUser.setWorkspaceId(workspace.getId());
        workspaceUser.setNickname(userDTO.getUsername());
        workspaceUser.setRoles(new String[]{});
        workspaceUserRepository.insert(workspaceUser);

        return keycloakUserService.getUser(userId);
    }

}
