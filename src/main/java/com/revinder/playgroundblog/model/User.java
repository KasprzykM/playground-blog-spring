package com.revinder.playgroundblog.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String login;

    private String password;

    @Indexed(unique = true)
    private String email;
}
