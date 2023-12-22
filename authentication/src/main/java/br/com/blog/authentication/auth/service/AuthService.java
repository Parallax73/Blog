package br.com.blog.authentication.auth.service;

/*

   Service class for the User Authentication.

 */

import br.com.blog.authentication.auth.dto.LoginDTO;
import br.com.blog.authentication.auth.dto.UserDTO;
import br.com.blog.authentication.auth.repository.UserRepository;
import br.com.blog.authentication.auth.entity.User;
import br.com.blog.authentication.email.dto.Email;
import br.com.blog.authentication.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserRepository repository;


    private final EmailService emailService;

    public AuthService(EmailService emailService) {
        this.emailService = emailService;
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
               return ResponseEntity.ok().build();
               // Generates JWT
           } else {
               log.info("Tried to login with a wrong password");
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
           }
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}


