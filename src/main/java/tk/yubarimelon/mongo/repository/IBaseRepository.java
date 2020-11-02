package tk.yubarimelon.mongo.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

public interface IBaseRepository<T> {
    Mono<T> insert(T entity);

    Flux<T> insertAll(Collection<T> entities);

    Mono<T> save(T entity);

    Mono<DeleteResult> delete(T entity);

    Mono<T> deleteById(String id);

    Flux<T> deleteByIds(Collection<?> ids);

    Mono<DeleteResult> deleteByQuery(Query query);

    Mono<T> findAndRemove(Query query);

    Flux<T> findAllAndRemove(Query query);

    Flux<T> findAll();

    Flux<T> saveOrUpdateAll(Collection<T> entities);

    Mono<T> findById(String id);

    Flux<T> findByIds(Collection<?> ids);

    Flux<T> findByIdsWithOrder(Collection<?> ids, String property);

    Flux<T> findByIdsWithOrder(Collection<?> ids, String property, boolean reverse);

    Class<T> getEntityClass();

    Flux<T> findByQuery(Query query);

    Mono<T> findOneByQuery(Query query);

    Mono<List<T>> findPageContent(Query query, int page, int size);

    Mono<Long> findPageCount(Query query);

    Mono<UpdateResult> updateFirst(Query query, UpdateDefinition update);

    Mono<UpdateResult> updateMulti(Query query, UpdateDefinition update);

    Mono<T> findAndModify(Query query, UpdateDefinition update);

    Mono<T> findAndModify(Query query, UpdateDefinition update, FindAndModifyOptions options);

    Flux<T> aggregateItself(Aggregation aggregation);

    <O> Flux<O> aggregate(Aggregation aggregation, Class<O> outputClass);

    Mono<Long> count(Query query);

    Mono<T> getLast();
}
