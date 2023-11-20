package br.com.blog.authentication.service;


import br.com.blog.authentication.config.security.TokenDTO;
import br.com.blog.authentication.config.security.TokenService;
import br.com.blog.authentication.dto.UserDTO;
import br.com.blog.authentication.entity.User;
import br.com.blog.authentication.enums.Status;
import br.com.blog.authentication.repository.UserRepository;
import br.com.blog.authentication.utils.sender.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    final
    EmailSender sender;

    final
    UserRepository userRepository;

    final
    TokenService service;
    final AuthenticationManager manager;


    TokenDTO token;

    public UserService(EmailSender sender, UserRepository userRepository, TokenService service, AuthenticationManager manager) {
        this.sender = sender;
        this.userRepository = userRepository;
        this.service = service;
        this.manager = manager;
    }


    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        var user = new User(userDTO);
        try {
            userRepository.save(user);
            log.info("New user {} created {} ", user.getEmail(), user.getCreateDate());
            sender.sendEmail(user.getEmail());
            return ResponseEntity.ok().build();
        } catch (UnexpectedRollbackException e) {
            log.error("Tried to create an user with an already used email");
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<?> loginUser(UserDTO user) {
        if (userRepository.existsByEmail(user.email())) {
            Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(user.email()));
            if (optionalUser.isPresent()) {
                var userH = optionalUser.get();
                if (userH.checkPassword(user.password())) {
                    var authToken = new UsernamePasswordAuthenticationToken(user.email(), user.password());
                    var auth = manager.authenticate(authToken);
                    var jwt = service.generateToken((User) auth.getPrincipal());
                    log.info("LOGGED WITH SUCCESS");
                    var token = new TokenDTO(jwt);
                    log.info(">>>>>>>subject"+service.getSubject(token.token()));
                    service.setToken(token);
                    return ResponseEntity.ok().body(token);
                }
            }
            log.info("Tried to log in with an incorrect password");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info("The email isn't in the db");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> setToken (TokenDTO tokenDTO){
        if (tokenDTO.token()==null){
            log.error("Cannot get the login token");
            return ResponseEntity.internalServerError().build();
        } else {
            log.info("Got the token");
            log.info(">"+tokenDTO.token()+"<");
            service.setToken(tokenDTO);
            return ResponseEntity.ok().build();
        }
    }

    public Optional<String> getLoggedUser(){
        if (service.getToken()!=null){
            return service.getSubject(token.token()).describeConstable();
        }
        return Optional.empty();
    }

    public ResponseEntity<?> logout(){
        if (service.getToken()!=null){
            service.setToken(null);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> deactivateUser(UserDTO user) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(user.email()));

        if (optionalUser.isPresent()) {
            User userH = optionalUser.get();

            if (!userH.checkPassword(user.password())) {
                log.info("Tried to deactivate with wrong password");
                return ResponseEntity.badRequest().build();
            }

            if (Objects.equals(userH.getStatus(), Status.UNACTIVE.toString())) {
                log.info("Tried to deactivate an account that's already deactivated");
                return ResponseEntity.badRequest().build();
            }

            userH.deactivateUser();
            log.info("User {} deactivated with success", userH.getEmail());
            return ResponseEntity.ok().build();
        }

        log.info("No account linked with the email tried to deactivate");
        return ResponseEntity.badRequest().build();
    }
}