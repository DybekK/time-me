package com.dybek.timeme.datasource.entity;

import com.dybek.timeme.keycloak.WorkspaceRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
//workspace_user
public class WorkspaceUser implements Model {
    private UUID id;
    private String nickname;
    private Set<WorkspaceRole> roles = new HashSet<>();
    private UUID userId;
    private UUID workspaceId;

    public void addRole(WorkspaceRole role) {
        roles.add(role);
    }

    public void removeRole(WorkspaceRole role) {
        roles.remove(role);
    }
}
