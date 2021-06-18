package com.dybek.timeme.domain.repository;

import com.dybek.timeme.domain.jooq.tables.daos.WorkspaceUserDao;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class WorkspaceUserRepository extends WorkspaceUserDao {
    public WorkspaceUserRepository(DSLContext dslContext) {
        super(dslContext.configuration());
    }
}
