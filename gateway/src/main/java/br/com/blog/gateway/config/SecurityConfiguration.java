package br.com.blog.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static br.com.blog.gateway.user.Permission.*;
import static br.com.blog.gateway.user.Role.ADMIN;
import static br.com.blog.gateway.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.*;


/*@Configuration*/
@EnableWebSecurity
@RequiredArgsConstructor
/*@EnableMethodSecurity*/
@Configuration
@EnableWebFluxSecurity
    public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/teste",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    private final JwtWebFilter jwtAuthFilter;
    private final CustomAuthenticationManager manager;
    private final LogoutService logoutHandler;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.csrf().disable()
                .authorizeExchange(req -> req
                        .pathMatchers(WHITE_LIST_URL).permitAll()
                        .pathMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .pathMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .pathMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                        .pathMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                        .pathMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                        .anyExchange().authenticated())
                .authenticationManager(manager)
                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .logout(logout -> logout.logoutHandler(logoutHandler)
                        .logoutSuccessHandler((request,response) -> {
                            SecurityContextHolder.clearContext();
                            return null;
                        }));


        return http.build();
    }
}