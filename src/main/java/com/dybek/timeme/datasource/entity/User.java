package com.dybek.timeme.datasource.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class User {
    private UUID id;
    private UUID externalId;
    private String username;
    private String email;
}
