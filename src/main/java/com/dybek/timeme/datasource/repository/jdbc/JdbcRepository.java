package com.dybek.timeme.datasource.repository.jdbc;

import com.dybek.timeme.datasource.entity.Model;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class JdbcRepository<T extends Model, ID> implements Repository<T, ID> {
    protected final NamedParameterJdbcTemplate jdbcTemplate;
    protected JdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
