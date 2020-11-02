package tk.yubarimelon.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("user")
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}

