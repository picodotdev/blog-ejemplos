package io.github.picodotdev.plugintapestry.services.dao;

import java.util.List;

import io.github.picodotdev.plugintapestry.misc.Pagination;

public interface GenericDAO<T> {

    T findById(Long id);

    List<T> findAll();

    List<T> findAll(Pagination pagination);

    long countAll();

    void persist(T object);

    void remove(T object);

    void removeAll();
}