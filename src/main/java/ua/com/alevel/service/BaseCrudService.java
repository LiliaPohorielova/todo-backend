package ua.com.alevel.service;

import ua.com.alevel.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseCrudService<E extends BaseEntity> {

    void create(E entity);
    void update(E entity);
    void delete(Long id);
    Optional<E> findById(Long id);
    List<E> findAll();
}
