package com.beecoders.ras.auth.service;

import com.beecoders.ras.auth.model.request.AuthLogin;

public interface AuthService {
    String login(AuthLogin login);
}
