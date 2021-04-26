package com.dybek.timeme.datasource.repository.jdbc;

import org.jooq.Record;
import org.jooq.impl.TableImpl;

import java.util.List;

public interface Repository<T extends TableImpl<?>, ID> {
    public T find(ID id);
    public List<T> findAll();
    public <S extends T> T create(S entity);
    public <S extends T> T update(S entity);
    public <S extends T> void delete(S entity);
}
