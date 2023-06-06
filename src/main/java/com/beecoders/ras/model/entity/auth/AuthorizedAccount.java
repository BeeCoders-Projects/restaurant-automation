package com.beecoders.ras.model.entity.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "authorized_account", timeToLive = 3600)
public class AuthorizedAccount {
    @Id
    @Indexed
    private String username;
    private String jwtToken;
}
