package com.revinder.playgroundblog.model;

import lombok.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class JwtToken {

    @Getter
    private final String token;
}
