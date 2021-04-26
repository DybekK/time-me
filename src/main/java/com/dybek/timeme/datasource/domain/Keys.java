/*
 * This file is generated by jOOQ.
 */
package com.dybek.timeme.datasource.domain;


import com.dybek.timeme.datasource.domain.tables.Task;
import com.dybek.timeme.datasource.domain.tables.Workspace;
import com.dybek.timeme.datasource.domain.tables.WorkspaceUser;
import com.dybek.timeme.datasource.domain.tables.records.TaskRecord;
import com.dybek.timeme.datasource.domain.tables.records.WorkspaceRecord;
import com.dybek.timeme.datasource.domain.tables.records.WorkspaceUserRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * the default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<TaskRecord> CONSTRAINT_363 = Internal.createUniqueKey(Task.TASK, DSL.name("CONSTRAINT_363"), new TableField[] { Task.TASK.ID }, true);
    public static final UniqueKey<WorkspaceRecord> CONSTRAINT_4 = Internal.createUniqueKey(Workspace.WORKSPACE, DSL.name("CONSTRAINT_4"), new TableField[] { Workspace.WORKSPACE.ID }, true);
    public static final UniqueKey<WorkspaceUserRecord> CONSTRAINT_2F = Internal.createUniqueKey(WorkspaceUser.WORKSPACE_USER, DSL.name("CONSTRAINT_2F"), new TableField[] { WorkspaceUser.WORKSPACE_USER.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TaskRecord, WorkspaceUserRecord> CONSTRAINT_3 = Internal.createForeignKey(Task.TASK, DSL.name("CONSTRAINT_3"), new TableField[] { Task.TASK.WORKSPACE_USER_ID }, Keys.CONSTRAINT_2F, new TableField[] { WorkspaceUser.WORKSPACE_USER.ID }, true);
    public static final ForeignKey<TaskRecord, WorkspaceRecord> CONSTRAINT_36 = Internal.createForeignKey(Task.TASK, DSL.name("CONSTRAINT_36"), new TableField[] { Task.TASK.WORKSPACE_ID }, Keys.CONSTRAINT_4, new TableField[] { Workspace.WORKSPACE.ID }, true);
    public static final ForeignKey<WorkspaceUserRecord, WorkspaceRecord> CONSTRAINT_2 = Internal.createForeignKey(WorkspaceUser.WORKSPACE_USER, DSL.name("CONSTRAINT_2"), new TableField[] { WorkspaceUser.WORKSPACE_USER.WORKSPACE_ID }, Keys.CONSTRAINT_4, new TableField[] { Workspace.WORKSPACE.ID }, true);
}
