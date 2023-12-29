package br.com.blog.gateway.config;

import br.com.blog.gateway.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LogoutService implements ServerLogoutHandler {

  private final TokenRepository tokenRepository;



  @Override
  public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
    final String authHeader = exchange.getExchange().getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    final String jwt;
    if (authHeader == null || !authHeader.startsWith("Bearer ")){
      return Mono.empty();
    }
    jwt = authHeader.substring(7);
    var storedToken = tokenRepository.findByToken(jwt)
            .orElse(null);
    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);
      tokenRepository.save(storedToken);
      SecurityContextHolder.clearContext();
    }
    return Mono.empty();
  }
}
