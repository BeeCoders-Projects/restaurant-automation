package com.beecoders.ras.service.auth;

import com.beecoders.ras.model.auth.request.AuthLogin;

public interface AuthService {
    String login(AuthLogin login);
}
