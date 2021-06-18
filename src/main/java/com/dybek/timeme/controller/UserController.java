package com.dybek.timeme.controller;

import com.dybek.timeme.domain.jooq.tables.pojos.WorkspaceUser;
import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.KeycloakUserCreationFailedException;
import com.dybek.timeme.exception.SecurityContextUserNotFoundException;
import com.dybek.timeme.service.user.KeycloakUserService;
import com.dybek.timeme.service.user.UserService;
import org.jooq.DSLContext;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URISyntaxException;
import static com.dybek.timeme.domain.jooq.Tables.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final KeycloakUserService keycloakUserService;
    private final UserService userService;
    @Autowired
    private DSLContext dsl;

    public UserController(KeycloakUserService keycloakUserService, UserService userService) {
        this.keycloakUserService = keycloakUserService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRepresentation> registerUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        try {
            return ResponseEntity.ok(userService.create(userDTO));
        } catch (KeycloakUserCreationFailedException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<WorkspaceUser>> getAll() {
        var workspaceUsers = dsl.select().from(WORKSPACE_USER).fetchInto(WorkspaceUser.class);
        return ResponseEntity.ok(workspaceUsers);
    }

    @GetMapping("/get-current")
    public ResponseEntity<UserRepresentation> getCurrentUser() throws SecurityContextUserNotFoundException {
        return ResponseEntity.ok(keycloakUserService.getLoggedUser());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(keycloakUserService.getUser(id));
    }
}