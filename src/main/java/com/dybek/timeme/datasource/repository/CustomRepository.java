package com.dybek.timeme.datasource.repository;

import com.dybek.timeme.datasource.entity.Model;

import java.util.List;

public interface CustomRepository<T extends Model, ID> {
    public T find(ID id);
    public List<T> findAll();
    public <S extends T> T create(S entity);
    public <S extends T> T update(S entity);
    public <S extends T> void delete(S entity);
}
