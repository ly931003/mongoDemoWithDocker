package tk.yubarimelon.mongo.repository;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Mono;
import tk.yubarimelon.mongo.entity.User;

public interface IUserRepository extends IBaseRepository<User> {
    Mono<UpdateResult> incrOrder(String id, String name);

    Mono<UpdateResult> incrAllOrder(String id);
}
