package com.dybek.timeme.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserDTO {
    private UUID id;
    private UUID externalId;

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;
}
