package tk.yubarimelon.mongo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("user")
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private Address address;
    private List<Order> orders = new ArrayList<>();

    @Getter
    @Setter
    @ToString
    public static class Address {
        private String city;
        private String district;
    }

    @Getter
    @Setter
    @ToString
    public static class Order {
        private String name;
        private Integer amount;
    }

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}

