package com.dybek.timeme.security;

import com.dybek.timeme.datasource.entity.User;
import com.dybek.timeme.datasource.repository.UserRepository;
import com.dybek.timeme.exception.SecurityContextUserNotFoundException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserSecurity {
    private final UserRepository userRepository;

    public UserSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedUser() throws SecurityContextUserNotFoundException {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .flatMap(authentication -> {
                    if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
                        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) authentication.getPrincipal();
                        Optional<AccessToken> accessToken = Optional.ofNullable(principal.getKeycloakSecurityContext().getToken());
                        return accessToken.map(token -> userRepository.findByExternalId(UUID.fromString(token.getSubject())));
                    }
                    return Optional.empty();
                })
                .orElseThrow(() -> new SecurityContextUserNotFoundException("User was not found in security context"));
    }
}
