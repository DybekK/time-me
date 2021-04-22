package com.dybek.timeme.datasource.repository;

import com.dybek.timeme.datasource.entity.Workspace;
import com.dybek.timeme.datasource.helper.ArraySqlValue;
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
public class WorkspaceRepository extends JdbcRepository<Workspace, UUID> {
    public WorkspaceRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Workspace find(UUID uuid) {
        return null;
    }

    @Override
    public List<Workspace> findAll() {
        return null;
    }

    @Override
    public Workspace create(Workspace workspace) {
        final String sql = "INSERT INTO workspace DEFAULT VALUES";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource();
        jdbcTemplate.update(sql, param, holder);
        UUID id = (UUID) Objects.requireNonNull(holder.getKeys()).get("id");
        workspace.setId(id);
        return workspace;
    }

    @Override
    public <S extends Workspace> Workspace update(S entity) {
        return null;
    }

    @Override
    public <S extends Workspace> void delete(S entity) {

    }
}
