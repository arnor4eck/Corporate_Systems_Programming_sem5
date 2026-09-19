package com.arnor4eck.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> get(int id);
    Collection<T> getAll();
    void save(T value);
    void delete(int id);
}
