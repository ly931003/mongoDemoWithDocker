package tk.yubarimelon.mongo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class ReactiveRabbitMQ {
    @Bean
    public Queue myQueue() {
        return new Queue("myqueue");
    }
}
