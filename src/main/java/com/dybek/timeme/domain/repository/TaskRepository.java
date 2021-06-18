package com.dybek.timeme.domain.repository;

import com.dybek.timeme.domain.jooq.tables.daos.TaskDao;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository extends TaskDao {
    public TaskRepository(DSLContext dslContext) {
        super(dslContext.configuration());
    }
}
