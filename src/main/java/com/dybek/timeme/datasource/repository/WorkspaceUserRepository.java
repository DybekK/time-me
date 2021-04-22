package com.dybek.timeme.datasource.repository;

import com.dybek.timeme.datasource.entity.WorkspaceUser;
import com.dybek.timeme.datasource.repository.jdbc.JdbcRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class WorkspaceUserRepository extends JdbcRepository<WorkspaceUser, UUID> {
    protected WorkspaceUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
        final String sql = "INSERT INTO workspace_user (nickname, user_id, workspace_id) VALUES (:nickname, :userId, :workspaceId)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("nickname", workspaceUser.getNickname())
                .addValue("userId", workspaceUser.getUserId())
                .addValue("workspaceId", workspaceUser.getWorkspaceId());
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
