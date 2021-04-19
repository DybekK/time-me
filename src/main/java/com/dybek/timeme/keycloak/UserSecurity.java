package com.dybek.timeme.keycloak;

import com.dybek.timeme.exception.SecurityContextUserNotFoundException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.account.UserRepresentation;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserSecurity {
    private final KeycloakService keycloakService;

    public UserSecurity(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    public UserRepresentation getLoggedUser() throws SecurityContextUserNotFoundException {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .flatMap(authentication -> {
                    if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
                        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) authentication.getPrincipal();
                        Optional<AccessToken> accessToken = Optional.ofNullable(principal.getKeycloakSecurityContext().getToken());
                        return accessToken.map(token -> keycloakService.getUser(UUID.fromString(token.getSubject())));
                    }
                    return Optional.empty();
                })
                .orElseThrow(() -> new SecurityContextUserNotFoundException("User was not found in security context"));
    }
}
