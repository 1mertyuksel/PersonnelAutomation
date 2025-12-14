package com.project.service.interfaces;

import com.project.entity.BaseEntity;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Generic Service Interface
 * Business logic katmanı için generic interface
 * 
 * @param <T> Entity tipi (BaseEntity'den extend eden)
 */
public interface IService<T extends BaseEntity> {
    
    // --- Temel CRUD Operasyonları ---
    
    /**
     * Yeni entity ekler
     */
    CompletableFuture<T> addAsync(T entity);
    
    /**
     * Birden fazla entity ekler
     */
    CompletableFuture<Void> addRangeAsync(List<T> entities);
    
    /**
     * Entity günceller
     */
    CompletableFuture<T> updateAsync(T entity);
    
    /**
     * Birden fazla entity günceller
     */
    CompletableFuture<Void> updateRangeAsync(List<T> entities);
    
    /**
     * ID'ye göre entity siler
     */
    CompletableFuture<Void> removeAsync(Long id);
    
    // --- Sorgulama Metodları ---
    
    /**
     * ID'ye göre entity getirir
     */
    CompletableFuture<Optional<T>> getByIdAsync(Long id);
    
    /**
     * Tüm entity'leri getirir
     */
    CompletableFuture<List<T>> getAllAsync();
    
    /**
     * Tüm entity'leri getirir (tracking olmadan - performans için)
     */
    CompletableFuture<List<T>> getAllAsyncNoTracking();
    
    /**
     * Tüm entity'leri getirir (sync)
     */
    List<T> findAll();
    
    /**
     * Tüm entity'leri getirir (tracking olmadan - sync)
     */
    List<T> findAllNoTracking();
}

