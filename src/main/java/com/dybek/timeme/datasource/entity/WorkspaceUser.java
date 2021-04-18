package com.dybek.timeme.datasource.entity;

import com.dybek.timeme.security.WorkspaceRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
public class WorkspaceUser {
    private UUID id;
    private Set<WorkspaceRole> roles;

    public void addRole(WorkspaceRole role) {
        roles.add(role);
    }

    public void removeRole(WorkspaceRole role) {
        roles.remove(role);
    }
}
