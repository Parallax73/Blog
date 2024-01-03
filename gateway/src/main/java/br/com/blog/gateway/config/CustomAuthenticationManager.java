package br.com.blog.gateway.config;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();


        boolean isAuthenticated = authenticateUser(username, password);

        if (isAuthenticated) {

            Authentication authenticatedToken = new UsernamePasswordAuthenticationToken(username, password);
            return Mono.just(authenticatedToken);
        } else {

            return Mono.empty();
        }
    }

    private boolean authenticateUser(String username, String password) {
        return "user".equals(username) && "password".equals(password);
    }
}
