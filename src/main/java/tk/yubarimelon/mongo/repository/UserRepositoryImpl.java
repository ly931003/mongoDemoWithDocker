package tk.yubarimelon.mongo.repository;

import org.springframework.stereotype.Repository;
import tk.yubarimelon.mongo.entity.User;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements IUserRepository {
}
