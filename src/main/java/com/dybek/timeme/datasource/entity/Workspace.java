package com.dybek.timeme.datasource.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
//workspace
public class Workspace implements Model {
    private UUID id;
}
