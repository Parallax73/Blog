package br.com.blog.authentication.controller;


import br.com.blog.authentication.config.security.TokenDTO;
import br.com.blog.authentication.dto.UserDTO;
import br.com.blog.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AuthController {

    final
    UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public ModelAndView homePage(){
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView registerPage(){
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        return service.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        return service.loginUser(userDTO);
    }

    @PostMapping ("/token")
    public ResponseEntity<?> getToken(@RequestBody TokenDTO tokenDTO){
        return service.setToken(tokenDTO);
    }

    @PatchMapping("/deactivate")
    @Transactional
    public ResponseEntity<?> deactivate(UserDTO userDTO){
        return service.deactivateUser(userDTO);
    }

    @GetMapping("/returnToken")
    public ResponseEntity<?> returnToken(){
        return ResponseEntity.ok().body(service.getLoggedUser());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return service.logout();
    }

}
