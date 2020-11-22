package tk.yubarimelon.mongo.controller;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tk.yubarimelon.mongo.entity.User;
import tk.yubarimelon.mongo.repository.IUserRepository;
import tk.yubarimelon.mongo.response.Response;

import java.util.List;

@RestController()
@RequestMapping("/api")
@Log4j2
public class UserController extends AbstractBaseController {
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/user")
    public Mono<ResponseEntity<Response<User>>> createUser(@RequestBody User user) {
        return returnEntity(userRepository.insert(user), Response.MSG_CREATE);
    }

    @GetMapping("/user/{id}")
    public Mono<ResponseEntity<Response<User>>> findUserById(@PathVariable String id) {
        return returnEntity(userRepository.findById(id));
    }

    @PostMapping("/user/findAll")
    public Mono<ResponseEntity<Response<List<User>>>> findAllUsers() {
        return returnEntity(userRepository.findAll().collectList());
    }

    @PutMapping("/user/{id}")
    public Mono<ResponseEntity<Response<User>>> updateUserById(@PathVariable String id,
                                                               @RequestBody User user) {
        return returnEntity(userRepository.save(user), Response.MSG_UPDATE);
    }

    @DeleteMapping("/user/{id}")
    public Mono<ResponseEntity<Response<User>>> deleteUserById(@PathVariable String id) {
        return returnEntity(userRepository.deleteById(id), Response.MSG_DELETE);
    }

    @PutMapping("/user/{id}/incrOrder/{name}")
    public Mono<ResponseEntity<Response<UpdateResult>>> incrOrder(@PathVariable String id,
                                                                  @PathVariable String name) {
        return returnEntity(userRepository.incrOrder(id, name), Response.MSG_UPDATE);
    }

    @PutMapping("/user/{id}/incrAllOrder")
    public Mono<ResponseEntity<Response<UpdateResult>>> incrAllOrder(@PathVariable String id) {
        return returnEntity(userRepository.incrAllOrder(id), Response.MSG_UPDATE);
    }
}
