package com.project.service.services;

import com.project.entity.BaseEntity;
import com.project.repository.IRepository;
import com.project.service.interfaces.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Generic Service Implementation
 * Repository katmanını kullanarak business logic işlemlerini yapar
 * 
 * @param <T> Entity tipi (BaseEntity'den extend eden)
 */
@Transactional
public class Service<T extends BaseEntity> implements IService<T> {

    protected IRepository<T> repository;

    public Service(IRepository<T> repository) {
        this.repository = repository;
    }

    // --- Temel CRUD Operasyonları ---

    @Override
    public CompletableFuture<T> addAsync(T entity) {
        return repository.addAsync(entity);
    }

    @Override
    public CompletableFuture<Void> addRangeAsync(List<T> entities) {
        return repository.addRangeAsync(entities);
    }

    @Override
    public CompletableFuture<T> updateAsync(T entity) {
        return repository.updateAsync(entity);
    }

    @Override
    public CompletableFuture<Void> updateRangeAsync(List<T> entities) {
        return repository.updateRangeAsync(entities);
    }

    @Override
    public CompletableFuture<Void> removeAsync(Long id) {
        return repository.removeAsync(id);
    }

    // --- Sorgulama Metodları ---

    @Override
    public CompletableFuture<Optional<T>> getByIdAsync(Long id) {
        return repository.getByIdAsync(id);
    }

    @Override
    public CompletableFuture<List<T>> getAllAsync() {
        return repository.getAllAsync();
    }

    @Override
    public CompletableFuture<List<T>> getAllAsyncNoTracking() {
        return repository.getAllAsyncNoTracking();
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAllNoTracking() {
        return repository.findAllNoTracking();
    }
}

