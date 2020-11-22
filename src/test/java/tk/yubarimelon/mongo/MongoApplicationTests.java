package tk.yubarimelon.mongo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.yubarimelon.mongo.entity.User;
import tk.yubarimelon.mongo.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
class MongoApplicationTests {
    @Autowired
    private IUserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void userTest() {
        User user = new User();
        user.setPassword("123456");
        user.setUsername("Yubari Melon");

        User.Address address = new User.Address();
        address.setCity("Yubari");
        address.setDistrict("Melon");
        user.setAddress(address);

        List<User.Order> orders = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            User.Order order = new User.Order();
            order.setName("Order " + i);
            order.setAmount(i);
            orders.add(order);
        }
        user.setOrders(orders);

        User userSaved = userRepository.save(user).block();
        log.info(userSaved);
    }

}
