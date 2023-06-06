package com.beecoders.ras.security.jwt.filter;

import com.beecoders.ras.model.entity.auth.AuthorizedAccount;
import com.beecoders.ras.repository.AuthorizedAccountRepository;
import com.beecoders.ras.security.jwt.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.beecoders.ras.security.jwt.constant.JwtTokenConstant.TOKEN_HEADER;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizedAccountRepository authorizedAccountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_HEADER)) {

            String jwtToken = authorizationHeader.substring(TOKEN_HEADER.length());
            String username = jwtTokenProvider.getSubject(jwtToken);


            if (jwtTokenProvider.isTokenValid(username, jwtToken) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                Optional<AuthorizedAccount> authorizedAccount = authorizedAccountRepository.findByUsername(username);

                if (authorizedAccount.isPresent() &&
                        authorizedAccount.get().getJwtToken().equals(jwtToken)) {
                    GrantedAuthority authority = jwtTokenProvider.getAuthority(jwtToken);
                    Authentication authentication = jwtTokenProvider.getAuthentication(username, authority, request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
