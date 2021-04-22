package com.dybek.timeme.controller;

import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.KeycloakUserCreationFailedException;
import com.dybek.timeme.keycloak.KeycloakUserService;
import com.dybek.timeme.service.UserService;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final KeycloakUserService keycloakUserService;
    private final UserService userService;

    public UserController(KeycloakUserService keycloakUserService, UserService userService) {
        this.keycloakUserService = keycloakUserService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRepresentation> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.create(userDTO));
        } catch (KeycloakUserCreationFailedException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(keycloakUserService.getUser(id));
    }
}