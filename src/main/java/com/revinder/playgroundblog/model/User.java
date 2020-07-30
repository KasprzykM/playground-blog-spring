package com.revinder.playgroundblog.model;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

    @Id
    private String id;

    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    private String email;
}
