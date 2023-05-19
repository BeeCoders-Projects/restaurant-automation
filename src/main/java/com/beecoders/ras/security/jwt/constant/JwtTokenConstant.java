package com.beecoders.ras.security.jwt.constant;

public class JwtTokenConstant {
    public static final long EXPIRATION_TIME = 3_600 * 1_000; // 1-hour
    public static final String TOKEN_HEADER = "Bearer ";
    public static final String JWT_ACCESS_TOKEN = "Access token";
    public static final String TOKEN_ISSUE = "RAS";
    public static final String TOKEN_NOT_VERIFIED_MESSAGE = "The token cannot be verified";
    public static final String ROLE_CLAIM = "role";
    public static final String NAME_CLAIM = "name";
    public static final String ADMIN_NAME = "Admin";
}
