package br.com.blog.authentication.auth.service;

/*

   Service class for the User Authentication.

 */

import br.com.blog.authentication.auth.dto.LoginDTO;
import br.com.blog.authentication.auth.dto.UserDTO;
import br.com.blog.authentication.auth.entity.User;
import br.com.blog.authentication.auth.repository.UserRepository;
import br.com.blog.authentication.email.dto.Email;
import br.com.blog.authentication.email.service.EmailService;
import br.com.blog.authentication.jwt.token.Token;
import br.com.blog.authentication.jwt.token.TokenRepository;
import br.com.blog.authentication.jwt.token.TokenType;
import br.com.blog.authentication.jwt.utils.AuthenticationResponse;
import br.com.blog.authentication.jwt.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserRepository repository;

    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService service, EmailService emailService, TokenRepository tokenRepository, AuthenticationManager authenticationManager) {
        this.jwtService = service;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;

        this.authenticationManager = authenticationManager;
    }


    public ResponseEntity<?> createNewUser(UserDTO dto){
        try {
            var user = repository.findByUsername(dto.username());
            if (user.isPresent()){
                log.info("user already exists");
                return ResponseEntity.badRequest().build();
            } else {
                log.info("Account created!!!");
                var newUser = new User(dto);
                repository.save(newUser);
                emailService.sendEmail(
                        new Email(
                        newUser.getEmail(),
                        "Welcome to the blog!!!",
                        "Hey "+ newUser.getUsername()+ " thanks for creating an account!!"));
                return ResponseEntity.ok().build();
            }
        } catch (Exception e){
            log.info("Some error: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    public ResponseEntity<?> loginUser(LoginDTO dto){
        try {
           var user = repository.findByUsername(dto.username()).get();
           if (user.passDecode(dto.password())){
               log.info("{} User logged with success",user.getUsername());
               var jwtToken = jwtService.generateToken(user);
               var refreshedToken = jwtService.generateRefreshToken(user);
                saveUserToken(user,jwtToken);
               return ResponseEntity.ok().build();
           } else {
               log.info("Tried to login with a wrong password");
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
           }
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    public AuthenticationResponse authenticate(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var user = repository.findByUsername(request.username())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }



    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.repository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}


