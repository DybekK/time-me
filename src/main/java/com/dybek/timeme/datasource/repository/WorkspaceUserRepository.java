package com.dybek.timeme.datasource.repository;

import com.dybek.timeme.datasource.entity.WorkspaceUser;
import com.dybek.timeme.datasource.helper.ArraySqlValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WorkspaceUserRepository implements CustomRepository<WorkspaceUser, UUID> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WorkspaceUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public WorkspaceUser find(UUID uuid) {
        return null;
    }

    @Override
    public List<WorkspaceUser> findAll() {
        return null;
    }

    @Override
    public WorkspaceUser create(WorkspaceUser workspaceUser) {
        final String sql = "INSERT INTO workspace_user (nickname, roles) VALUES (:nickname, :roles)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("nickname", workspaceUser.getNickname())
                .addValue("roles", ArraySqlValue.create(workspaceUser.getRoles().toArray()));
        jdbcTemplate.update(sql, param, holder);
        UUID id = (UUID) Objects.requireNonNull(holder.getKeys()).get("id");
        workspaceUser.setId(id);
        return workspaceUser;
    }

    @Override
    public <S extends WorkspaceUser> WorkspaceUser update(S entity) {
        return null;
    }

    @Override
    public <S extends WorkspaceUser> void delete(S entity) {

    }
}