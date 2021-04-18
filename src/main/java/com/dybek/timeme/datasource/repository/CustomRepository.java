package com.dybek.timeme.datasource.repository;

import java.util.List;

public interface CustomRepository<T, ID> {
    public T find(ID id);
    public List<T> findAll();
    public <S extends T> T create(S entity);
    public <S extends T> T update(S entity);
    public <S extends T> void delete(S entity);
}
