package com.arnor4eck.repository;

import java.util.Collection;

public interface Repository<T> {
    T get(int id);
    Collection<T> getAll();
    void save(T value);
    void delete(int id);
}
