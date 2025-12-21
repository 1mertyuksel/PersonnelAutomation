package com.project.repository;

import com.project.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Generic Repository Implementation
 * EntityManager kullanarak CRUD işlemlerini yapar
 * Service katmanından IRepository<Entity> şeklinde inject edilir
 * 
 * @param <T> Entity tipi (BaseEntity'den extend eden)
 */
@Transactional
public class Repository<T extends BaseEntity> implements IRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private Class<T> getEntityClass() {
        return entityClass;
    }

    // --- Temel CRUD Operasyonları ---

    @Override
    public CompletableFuture<T> addAsync(T entity) {
        // Transaction içinde çalışması için sync yapıyoruz
        return CompletableFuture.completedFuture(addSync(entity));
    }
    
    private T addSync(T entity) {
        entityManager.persist(entity);
        entityManager.flush(); // Transaction'ı commit etmek için
        return entity;
    }

    @Override
    public CompletableFuture<Void> addRangeAsync(List<T> entities) {
        // Transaction içinde çalışması için sync yapıyoruz
        for (T entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<T> updateAsync(T entity) {
        // Transaction içinde çalışması için sync yapıyoruz
        return CompletableFuture.completedFuture(updateSync(entity));
    }
    
    private T updateSync(T entity) {
        T merged = entityManager.merge(entity);
        entityManager.flush(); // Transaction'ı commit etmek için
        return merged;
    }

    @Override
    public CompletableFuture<Void> updateRangeAsync(List<T> entities) {
        // Transaction içinde çalışması için sync yapıyoruz
        for (T entity : entities) {
            entityManager.merge(entity);
        }
        entityManager.flush();
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> removeAsync(Long id) {
        // Transaction içinde çalışması için sync yapıyoruz
        T entity = entityManager.find(getEntityClass(), id);
        if (entity == null) {
            throw new RuntimeException("Entity with id " + id + " not found.");
        }
        
        // Soft delete için IsDeleted property'sini kontrol et
        try {
            Method isDeletedMethod = entity.getClass().getMethod("setIsDeleted", boolean.class);
            isDeletedMethod.invoke(entity, true);
            entityManager.merge(entity);
        } catch (Exception e) {
            // IsDeleted property yoksa hard delete yap
            entityManager.remove(entity);
        }
        entityManager.flush();
        return CompletableFuture.completedFuture(null);
    }

    // --- Sorgulama Metodları ---

    @Override
    public CompletableFuture<Optional<T>> getByIdAsync(Long id) {
        // Transaction içinde çalışması için sync yapıyoruz
        return CompletableFuture.completedFuture(Optional.ofNullable(entityManager.find(getEntityClass(), id)));
    }

    @Override
    public CompletableFuture<List<T>> getAllAsync() {
        // Transaction içinde çalışması için sync yapıyoruz
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        return CompletableFuture.completedFuture(typedQuery.getResultList());
    }

    @Override
    public CompletableFuture<List<T>> getAllAsyncNoTracking() {
        // Transaction içinde çalışması için sync yapıyoruz
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        List<T> results = typedQuery.getResultList();
        results.forEach(entityManager::detach);
        return CompletableFuture.completedFuture(results);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> findAllNoTracking() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        // No tracking için detach yapıyoruz
        List<T> results = typedQuery.getResultList();
        results.forEach(entityManager::detach);
        return results;
    }
}

