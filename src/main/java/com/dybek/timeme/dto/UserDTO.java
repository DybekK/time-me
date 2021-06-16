package com.dybek.timeme.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserDTO {
    @NotNull
    private String username;

//    @NotNull
//    private String firstname;
//
//    @NotNull
//    private String lastname;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private String password;
}
