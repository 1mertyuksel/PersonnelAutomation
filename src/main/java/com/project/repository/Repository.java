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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
@org.springframework.stereotype.Repository
@Transactional
public class Repository<T extends BaseEntity> implements IRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        if (entityClass == null) {
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length > 0 && actualTypeArguments[0] instanceof Class) {
                    entityClass = (Class<T>) actualTypeArguments[0];
                }
            }
            // Eğer generic type bulunamazsa, reflection ile bulmaya çalış
            if (entityClass == null) {
                throw new IllegalStateException("Generic type T could not be determined. " +
                    "Service katmanında IRepository<Entity> şeklinde kullanın.");
            }
        }
        return entityClass;
    }

    // --- Temel CRUD Operasyonları ---

    @Override
    public CompletableFuture<T> addAsync(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            entityManager.persist(entity);
            return entity;
        });
    }

    @Override
    public CompletableFuture<Void> addRangeAsync(List<T> entities) {
        return CompletableFuture.runAsync(() -> {
            for (T entity : entities) {
                entityManager.persist(entity);
            }
        });
    }

    @Override
    public CompletableFuture<T> updateAsync(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            return entityManager.merge(entity);
        });
    }

    @Override
    public CompletableFuture<Void> updateRangeAsync(List<T> entities) {
        return CompletableFuture.runAsync(() -> {
            for (T entity : entities) {
                entityManager.merge(entity);
            }
        });
    }

    @Override
    public CompletableFuture<Void> removeAsync(Long id) {
        return CompletableFuture.runAsync(() -> {
            Optional<T> entityOpt = getByIdAsync(id).join();
            if (entityOpt.isEmpty()) {
                throw new RuntimeException("Entity with id " + id + " not found.");
            }
            
            T entity = entityOpt.get();
            
            // Soft delete için IsDeleted property'sini kontrol et
            try {
                Method isDeletedMethod = entity.getClass().getMethod("setIsDeleted", boolean.class);
                isDeletedMethod.invoke(entity, true);
                entityManager.merge(entity);
            } catch (Exception e) {
                // IsDeleted property yoksa hard delete yap
                entityManager.remove(entity);
            }
        });
    }

    // --- Sorgulama Metodları ---

    @Override
    public CompletableFuture<Optional<T>> getByIdAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            T entity = entityManager.find(getEntityClass(), id);
            return Optional.ofNullable(entity);
        });
    }

    @Override
    public CompletableFuture<List<T>> getAllAsync() {
        return CompletableFuture.supplyAsync(() -> {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(getEntityClass());
            Root<T> root = query.from(getEntityClass());
            query.select(root);
            TypedQuery<T> typedQuery = entityManager.createQuery(query);
            return typedQuery.getResultList();
        });
    }

    @Override
    public CompletableFuture<List<T>> getAllAsyncNoTracking() {
        return CompletableFuture.supplyAsync(() -> {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(getEntityClass());
            Root<T> root = query.from(getEntityClass());
            query.select(root);
            TypedQuery<T> typedQuery = entityManager.createQuery(query);
            return typedQuery.getResultList();
        });
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

