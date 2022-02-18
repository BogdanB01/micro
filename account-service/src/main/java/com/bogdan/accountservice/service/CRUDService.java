package com.bogdan.accountservice.service;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    T add(T t);

    T update(T t);

    void delete(T t);
}
