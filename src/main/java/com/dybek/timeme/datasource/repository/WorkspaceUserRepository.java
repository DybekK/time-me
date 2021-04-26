//package com.dybek.timeme.datasource.repository;
//
//import com.dybek.timeme.datasource.domain.jooq.tables.WorkspaceUser;
//import com.dybek.timeme.datasource.repository.jdbc.JdbcRepository;
//import org.jooq.DSLContext;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//import static com.dybek.timeme.datasource.domain.jooq.Tables.*;
//import static org.jooq.impl.DSL.*;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.UUID;
//
//@Repository
//public class WorkspaceUserRepository extends JdbcRepository<WorkspaceUser, UUID> {
//    protected WorkspaceUserRepository(NamedParameterJdbcTemplate jdbcTemplate, DSLContext dsl) {
//        super(jdbcTemplate, dsl);
//    }
//
//    @Override
//    public WorkspaceUser find(UUID uuid) {
//        return null;
//    }
//
//    @Override
//    public List<WorkspaceUser> findAll() {
//        return null;
//    }
//
//    @Override
//    public WorkspaceUser create(WorkspaceUser workspaceUser) {
//        workspaceUser
//        dsl
//            .insertInto(WORKSPACE_USER, WORKSPACE_USER.WORKSPACE_ID, WORKSPACE_USER.NICKNAME, WORKSPACE_USER.WORKSPACE_ID)
//            .values()
//
//        final String sql = "INSERT INTO workspace_user (nickname, user_id, workspace_id) VALUES (:nickname, :userId, :workspaceId)";
//        KeyHolder holder = new GeneratedKeyHolder();
//        SqlParameterSource param = new MapSqlParameterSource()
//                .addValue("nickname", workspaceUser.getNickname())
//                .addValue("userId", workspaceUser.getUserId())
//                .addValue("workspaceId", workspaceUser.getWorkspaceId());
//        jdbcTemplate.update(sql, param, holder);
//        UUID id = (UUID) Objects.requireNonNull(holder.getKeys()).get("id");
//        workspaceUser.setId(id);
//        return workspaceUser;
//    }
//
//    @Override
//    public <S extends WorkspaceUser> WorkspaceUser update(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends WorkspaceUser> void delete(S entity) {
//
//    }
//}
