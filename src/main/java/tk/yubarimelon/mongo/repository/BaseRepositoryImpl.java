package tk.yubarimelon.mongo.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

@Repository
@Primary
public class BaseRepositoryImpl<T> implements IBaseRepository<T> {
    @Autowired
    protected ReactiveMongoOperations mongoOperations;
    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl() {
        Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType genericSuperclass = (ParameterizedType) superclass;
            Type actualTypeArgument = genericSuperclass.getActualTypeArguments()[0];
            if (actualTypeArgument instanceof Class) {
                entityClass = (Class<T>) actualTypeArgument;
            }
        }
    }

    @Override
    public Mono<T> insert(T entity) {
        return mongoOperations.insert(entity);
    }

    @Override
    public Flux<T> insertAll(Collection<T> entities) {
        return mongoOperations.insertAll(entities);
    }

    @Override
    public Mono<T> save(T entity) {
        return mongoOperations.save(entity);
    }

    @Override
    public Mono<DeleteResult> delete(T entity) {
        return mongoOperations.remove(entity);
    }

    @Override
    public Mono<T> deleteById(String id) {
        return mongoOperations.findAndRemove(new Query(Criteria.where("id").is(id)), entityClass);
    }

    @Override
    public Flux<T> deleteByIds(Collection<?> ids) {
        return mongoOperations.findAllAndRemove(new Query(Criteria.where("id").in(ids)), entityClass);
    }

    @Override
    public Mono<DeleteResult> deleteByQuery(Query query) {
        return mongoOperations.remove(query, entityClass);
    }

    @Override
    public Mono<T> findAndRemove(Query query) {
        return mongoOperations.findAndRemove(query, entityClass);
    }

    @Override
    public Flux<T> findAllAndRemove(Query query) {
        return mongoOperations.findAllAndRemove(query, entityClass);
    }

    @Override
    public Flux<T> findAll() {
        return mongoOperations.findAll(entityClass);
    }

    @Override
    public Flux<T> saveOrUpdateAll(Collection<T> entities) {
        return Flux.fromIterable(entities)
                .flatMap(mongoOperations::save);
    }

    @Override
    public Mono<T> findById(String id) {
        return mongoOperations.findById(id, entityClass);
    }

    @Override
    public Flux<T> findByIds(Collection<?> ids) {
        return mongoOperations.find(new Query(Criteria.where("id").in(ids)), entityClass);
    }

    @Override
    public Flux<T> findByIdsWithOrder(Collection<?> ids, String property) {
        return mongoOperations.find(new Query(Criteria.where("id").in(ids)).with(Sort.by(property).ascending()), entityClass);
    }

    @Override
    public Flux<T> findByIdsWithOrder(Collection<?> ids, String property, boolean reverse) {
        Sort order = Sort.by(property);
        if (reverse) {
            order = order.descending();
        }
        return mongoOperations.find(new Query(Criteria.where("id").in(ids)).with(order), entityClass);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public Flux<T> findByQuery(Query query) {
        return mongoOperations.find(query, entityClass);
    }

    @Override
    public Mono<T> findOneByQuery(Query query) {
        return mongoOperations.findOne(query, entityClass);
    }

    @Override
    public Mono<List<T>> findPageContent(Query query, int page, int size) {
        if (page <= 0 && size <= 0) {
            return mongoOperations.find(query, entityClass).collectList();
        }
        Query queryWithPage = Query.of(query);
        return mongoOperations.find(queryWithPage.with(PageRequest.of(page - 1, size)), entityClass).collectList();
    }

    @Override
    public Mono<Long> findPageCount(Query query) {
        return mongoOperations.count(query, entityClass);
    }

    @Override
    public Mono<UpdateResult> updateFirst(Query query, UpdateDefinition update) {
        return mongoOperations.updateFirst(query, update, entityClass);
    }

    @Override
    public Mono<UpdateResult> updateMulti(Query query, UpdateDefinition update) {
        return mongoOperations.updateMulti(query, update, entityClass);
    }

    @Override
    public Mono<T> findAndModify(Query query, UpdateDefinition update) {
        return mongoOperations.findAndModify(query, update, entityClass);
    }

    @Override
    public Mono<T> findAndModify(Query query, UpdateDefinition update, FindAndModifyOptions options) {
        return mongoOperations.findAndModify(query, update, options, entityClass);
    }

    @Override
    public Flux<T> aggregateItself(Aggregation aggregation) {
        return mongoOperations.aggregate(aggregation, entityClass, entityClass);
    }

    @Override
    public <O> Flux<O> aggregate(Aggregation aggregation, Class<O> outputClass) {
        return mongoOperations.aggregate(aggregation, entityClass, outputClass);
    }

    @Override
    public Mono<Long> count(Query query) {
        return mongoOperations.count(query, entityClass);
    }

    @Override
    public Mono<T> getLast() {
        return mongoOperations.findOne(Query.query(new Criteria()).with(Sort.by("id").descending()).limit(1), entityClass);
    }
}
