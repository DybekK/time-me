/*
 * This file is generated by jOOQ.
 */
package com.dybek.timeme.datasource.domain.tables;


import com.dybek.timeme.datasource.domain.DefaultSchema;
import com.dybek.timeme.datasource.domain.Keys;
import com.dybek.timeme.datasource.domain.tables.records.WorkspaceRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row1;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Workspace extends TableImpl<WorkspaceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>workspace</code>
     */
    public static final Workspace WORKSPACE = new Workspace();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WorkspaceRecord> getRecordType() {
        return WorkspaceRecord.class;
    }

    /**
     * The column <code>workspace.id</code>.
     */
    public final TableField<WorkspaceRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    private Workspace(Name alias, Table<WorkspaceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Workspace(Name alias, Table<WorkspaceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>workspace</code> table reference
     */
    public Workspace(String alias) {
        this(DSL.name(alias), WORKSPACE);
    }

    /**
     * Create an aliased <code>workspace</code> table reference
     */
    public Workspace(Name alias) {
        this(alias, WORKSPACE);
    }

    /**
     * Create a <code>workspace</code> table reference
     */
    public Workspace() {
        this(DSL.name("workspace"), null);
    }

    public <O extends Record> Workspace(Table<O> child, ForeignKey<O, WorkspaceRecord> key) {
        super(child, key, WORKSPACE);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<WorkspaceRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_4;
    }

    @Override
    public List<UniqueKey<WorkspaceRecord>> getKeys() {
        return Arrays.<UniqueKey<WorkspaceRecord>>asList(Keys.CONSTRAINT_4);
    }

    @Override
    public Workspace as(String alias) {
        return new Workspace(DSL.name(alias), this);
    }

    @Override
    public Workspace as(Name alias) {
        return new Workspace(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Workspace rename(String name) {
        return new Workspace(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Workspace rename(Name name) {
        return new Workspace(name, null);
    }

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row1<UUID> fieldsRow() {
        return (Row1) super.fieldsRow();
    }
}
