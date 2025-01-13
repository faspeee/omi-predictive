package com.mercant.real.estate.municipality.configuration.local;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Parameter;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.query.Order;
import org.hibernate.query.Page;
import org.hibernate.reactive.mutiny.Mutiny;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mercant.real.estate.municipality.utils.StringUtil.getEntityNextToFrom;

public class CustomSelectionQuery<T> extends AbstractVerticle implements Mutiny.SelectionQuery<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String query;
    private final Class<T> aClass;
    private int maxResult;
    private int firstResult;
    private Page page;

    public CustomSelectionQuery(String query, Class<T> aClass) {
        this.query = query;
        this.aClass = aClass;
    }

    private static String getEntity(String query) {
        return getEntityNextToFrom(query);
    }

    public Uni<Void> saveAll(Set<?> listObject) {
        return Vertx.vertx().fileSystem()
                .writeFile(query + ".txt", Buffer.buffer(listObject.stream()
                        .map(element -> {
                            try {
                                return objectMapper.writeValueAsString(element);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.joining())));
    }

    @Override
    public Mutiny.SelectionQuery<T> setMaxResults(int maxResult) {
        this.maxResult = maxResult;
        return this;
    }

    @Override
    public Mutiny.SelectionQuery<T> setFirstResult(int firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    @Override
    public Mutiny.SelectionQuery<T> setPage(Page page) {
        this.page = page;
        return this;
    }

    @Override
    public int getMaxResults() {
        return maxResult;
    }

    @Override
    public int getFirstResult() {
        return firstResult;
    }

    @Override
    public Uni<T> getSingleResult() {
        return null;
    }

    @Override
    public Uni<T> getSingleResultOrNull() {
        return null;
    }

    @Override
    public Uni<Long> getResultCount() {
        return null;
    }

    @Override
    public Uni<List<T>> getResultList() {
        String path = getEntity(query) + ".txt";
        if (new File(path).exists()) {
            return Vertx.vertx().fileSystem().readFile(path)
                    .map(this::convertToList);
        } else {
            return Uni.createFrom().item(new ArrayList<>());
        }

    }

    private List<T> convertToList(Buffer buffer) {
        return new ArrayList<>();
    }

    @Override
    public Mutiny.SelectionQuery<T> setReadOnly(boolean b) {
        return null;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public Mutiny.SelectionQuery<T> setCacheable(boolean b) {
        return null;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public Mutiny.SelectionQuery<T> setCacheRegion(String s) {
        return null;
    }

    @Override
    public String getCacheRegion() {
        return "";
    }

    @Override
    public Mutiny.SelectionQuery<T> setCacheMode(CacheMode cacheMode) {
        return null;
    }

    @Override
    public CacheStoreMode getCacheStoreMode() {
        return null;
    }

    @Override
    public CacheRetrieveMode getCacheRetrieveMode() {
        return null;
    }

    @Override
    public CacheMode getCacheMode() {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setFlushMode(FlushMode flushMode) {
        return null;
    }

    @Override
    public FlushMode getFlushMode() {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setLockMode(LockMode lockMode) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setLockMode(String s, LockMode lockMode) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setOrder(List<Order<? super T>> list) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setOrder(Order<? super T> order) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setPlan(EntityGraph<T> entityGraph) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> enableFetchProfile(String s) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setParameter(int i, Object o) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setParameter(String s, Object o) {
        return null;
    }

    @Override
    public <T1> Mutiny.SelectionQuery<T> setParameter(Parameter<T1> parameter, T1 t1) {
        return null;
    }

    @Override
    public Mutiny.SelectionQuery<T> setComment(String s) {
        return null;
    }

    @Override
    public String getComment() {
        return "";
    }
}
