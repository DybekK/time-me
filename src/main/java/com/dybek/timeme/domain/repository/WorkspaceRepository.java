package com.dybek.timeme.domain.repository;

import com.dybek.timeme.domain.jooq.tables.daos.WorkspaceDao;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class WorkspaceRepository extends WorkspaceDao {
    public WorkspaceRepository(DSLContext dslContext) {
        super(dslContext.configuration());
    }
}
