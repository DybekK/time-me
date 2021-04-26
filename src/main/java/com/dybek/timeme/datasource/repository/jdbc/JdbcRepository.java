package com.dybek.timeme.datasource.repository.jdbc;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class JdbcRepository<T extends TableImpl<?>, ID> implements Repository<T, ID> {
    protected final NamedParameterJdbcTemplate jdbcTemplate;
    protected final DSLContext dsl;
    protected JdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, DSLContext dsl) {
        this.jdbcTemplate = jdbcTemplate;
        this.dsl = dsl;
    }
}
