package com.dybek.timeme.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class TaskDTO {
    @NotNull
    private String title;

    @NotNull
    private UUID workspaceId;

    private UUID workspaceUserId;
}
