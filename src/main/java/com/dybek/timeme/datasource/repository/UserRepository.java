package com.dybek.timeme.datasource.repository;

import com.dybek.timeme.datasource.entity.User;
import com.dybek.timeme.datasource.mapper.UserRowMapper;
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
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User find(UUID id) {
        final String sql = "SELECT * FROM user_account WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(sql, param, new UserRowMapper());
    }

    public User findByExternalId(UUID id) {
        final String sql = "SELECT * FROM user_account WHERE external_id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(sql, param, new UserRowMapper());
    }

    public List<User> findAll() {
        final String sql = "SELECT * FROM user_account";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User create(User user) {
        final String sql = "INSERT INTO user_account (external_id, username) VALUES (:externalId, :username)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("externalId", user.getExternalId())
                .addValue("username", user.getUsername());
        jdbcTemplate.update(sql, param, holder);
        UUID id = (UUID) Objects.requireNonNull(holder.getKeys()).get("id");
        user.setId(id);
        return user;
    }
}