package tk.yubarimelon.mongo.repository;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tk.yubarimelon.mongo.entity.User;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements IUserRepository {
    @Override
    public Mono<UpdateResult> incrOrder(String id, String name) {
        Query query = Query.query(Criteria.where("id").is(id).and("orders.name").is(name));
        Update update = new Update();
        update.inc("orders.$.amount");
        return updateFirst(query, update);
    }

    @Override
    public Mono<UpdateResult> incrAllOrder(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update();
        update.inc("orders.$[elem].amount");
        update.currentDate("updatedAt");
        update.filterArray(Criteria.where("elem.amount").gte(2));
        return updateFirst(query, update);
    }
}
