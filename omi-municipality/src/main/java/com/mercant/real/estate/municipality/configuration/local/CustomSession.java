package com.mercant.real.estate.municipality.configuration.local;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Attribute;
import org.hibernate.CacheMode;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.reactive.common.AffectedEntities;
import org.hibernate.reactive.common.Identifier;
import org.hibernate.reactive.common.ResultSetMapping;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomSession extends AbstractVerticle implements Mutiny.Session {
    @Override
    public <T> Uni<T> find(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public <T> Uni<T> find(Class<T> aClass, Object o, LockMode lockMode) {
        return null;
    }

    @Override
    public <T> Uni<T> find(EntityGraph<T> entityGraph, Object o) {
        return null;
    }

    @Override
    public <T> Uni<List<T>> find(Class<T> aClass, Object... objects) {
        return null;
    }

    @Override
    public <T> Uni<T> find(Class<T> aClass, Identifier<T> identifier) {
        return null;
    }

    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public <T> T getReference(T t) {
        return null;
    }

    @Override
    public Uni<Void> persist(Object o) {
        return null;
    }

    @Override
    public Uni<Void> persistAll(Object... objects) {
        return new CustomSelectionQuery<>(Arrays.stream(objects)
                .findFirst().map(aClass -> aClass.getClass().getSimpleName())
                .orElseThrow(() -> new RuntimeException("the name cannot are blank or null"))
                .replace("[]", ""), objects.getClass())
                .saveAll(Arrays.stream(objects)
                        .flatMap(object -> Arrays.stream((Object[]) object))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Uni<Void> remove(Object o) {
        return null;
    }

    @Override
    public Uni<Void> removeAll(Object... objects) {
        return null;
    }

    @Override
    public <T> Uni<T> merge(T t) {
        return null;
    }

    @Override
    public Uni<Void> mergeAll(Object... objects) {
        return null;
    }

    @Override
    public Uni<Void> refresh(Object o) {
        return null;
    }

    @Override
    public Uni<Void> refresh(Object o, LockMode lockMode) {
        return null;
    }

    @Override
    public Uni<Void> refreshAll(Object... objects) {
        return null;
    }

    @Override
    public Uni<Void> lock(Object o, LockMode lockMode) {
        return null;
    }

    @Override
    public Uni<Void> flush() {
        return null;
    }

    @Override
    public <T> Uni<T> fetch(T t) {
        return null;
    }

    @Override
    public <E, T> Uni<T> fetch(E e, Attribute<E, T> attribute) {
        return null;
    }

    @Override
    public <T> Uni<T> unproxy(T t) {
        return null;
    }

    @Override
    public LockMode getLockMode(Object o) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createSelectionQuery(String query, Class<R> aClass) {
        return null;
    }

    @Override
    public Mutiny.MutationQuery createMutationQuery(String query) {
        return null;
    }

    @Override
    public <R> Mutiny.Query<R> createQuery(String query) {
        return null;
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createQuery(String query, Class<R> aClass) {
        return new CustomSelectionQuery<>(query, aClass);
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createQuery(CriteriaQuery<R> criteriaQuery) {
        return null;
    }

    @Override
    public <R> Mutiny.MutationQuery createQuery(CriteriaUpdate<R> criteriaUpdate) {
        return null;
    }

    @Override
    public <R> Mutiny.MutationQuery createQuery(CriteriaDelete<R> criteriaDelete) {
        return null;
    }

    @Override
    public <R> Mutiny.Query<R> createNamedQuery(String s) {
        return null;
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createNamedQuery(String s, Class<R> aClass) {
        return null;
    }

    @Override
    public <R> Mutiny.Query<R> createNativeQuery(String query) {
        return null;
    }

    @Override
    public <R> Mutiny.Query<R> createNativeQuery(String query, AffectedEntities affectedEntities) {
        return null;
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createNativeQuery(String query, Class<R> aClass) {
        return null;
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createNativeQuery(String query, Class<R> aClass, AffectedEntities affectedEntities) {
        return null;
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createNativeQuery(String query, ResultSetMapping<R> resultSetMapping) {
        return null;
    }

    @Override
    public <R> Mutiny.SelectionQuery<R> createNativeQuery(String query, ResultSetMapping<R> resultSetMapping, AffectedEntities affectedEntities) {
        return null;
    }

    @Override
    public Mutiny.Session setFlushMode(FlushMode flushMode) {
        return null;
    }

    @Override
    public FlushMode getFlushMode() {
        return null;
    }

    @Override
    public Mutiny.Session detach(Object o) {
        return null;
    }

    @Override
    public Mutiny.Session clear() {
        return null;
    }

    @Override
    public Mutiny.Session enableFetchProfile(String s) {
        return null;
    }

    @Override
    public <T> ResultSetMapping<T> getResultSetMapping(Class<T> aClass, String s) {
        return null;
    }

    @Override
    public <T> EntityGraph<T> getEntityGraph(Class<T> aClass, String s) {
        return null;
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass, String s) {
        return null;
    }

    @Override
    public Mutiny.Session disableFetchProfile(String s) {
        return null;
    }

    @Override
    public boolean isFetchProfileEnabled(String s) {
        return false;
    }

    @Override
    public Mutiny.Session setDefaultReadOnly(boolean b) {
        return null;
    }

    @Override
    public boolean isDefaultReadOnly() {
        return false;
    }

    @Override
    public Mutiny.Session setReadOnly(Object o, boolean b) {
        return null;
    }

    @Override
    public boolean isReadOnly(Object o) {
        return false;
    }

    @Override
    public Mutiny.Session setCacheMode(CacheMode cacheMode) {
        return null;
    }

    @Override
    public CacheMode getCacheMode() {
        return null;
    }

    @Override
    public Mutiny.Session setBatchSize(Integer integer) {
        return null;
    }

    @Override
    public Integer getBatchSize() {
        return 0;
    }

    @Override
    public Filter enableFilter(String s) {
        return null;
    }

    @Override
    public void disableFilter(String s) {

    }

    @Override
    public Filter getEnabledFilter(String s) {
        return null;
    }

    @Override
    public int getFetchBatchSize() {
        return 0;
    }

    @Override
    public Mutiny.Session setFetchBatchSize(int i) {
        return null;
    }

    @Override
    public boolean isSubselectFetchingEnabled() {
        return false;
    }

    @Override
    public Mutiny.Session setSubselectFetchingEnabled(boolean b) {
        return null;
    }

    @Override
    public <T> Uni<T> withTransaction(Function<Mutiny.Transaction, Uni<T>> function) {
        return null;
    }

    @Override
    public Mutiny.Transaction currentTransaction() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public Mutiny.SessionFactory getFactory() {
        return null;
    }

    @Override
    public Uni<Void> close() {
        return null;
    }
}
