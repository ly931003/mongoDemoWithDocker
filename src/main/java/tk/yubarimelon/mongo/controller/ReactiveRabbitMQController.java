package tk.yubarimelon.mongo.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tk.yubarimelon.mongo.entity.User;
import tk.yubarimelon.mongo.repository.IUserRepository;
import tk.yubarimelon.mongo.response.Response;

@RestController
public class ReactiveRabbitMQController extends AbstractBaseController {
    private final IUserRepository userRepository;
    private final RabbitTemplate amqpTemplate;

    public ReactiveRabbitMQController(IUserRepository userRepository, RabbitTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
        this.userRepository = userRepository;
    }

    @PostMapping("/mqTest/createUser")
    public Mono<ResponseEntity<Response<String>>> createUser(@RequestBody User user) {
        amqpTemplate.convertAndSend("myqueue", user);
        return returnEntity(Mono.just("OK"));
    }

    @RabbitListener(queues = "myqueue")
    public void listenTest1(User user) {
        userRepository.save(user).block();
    }

    @RabbitListener(queues = "Test-2")
    public void listenTest2(String in) {
        System.out.println(in);
    }

}
