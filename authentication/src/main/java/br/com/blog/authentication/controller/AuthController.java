package br.com.blog.authentication.controller;



import br.com.blog.authentication.dto.UserDTO;
import br.com.blog.authentication.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/auth")
public class AuthController {

    final
    UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register-user")
    @Transactional
    public void register(@RequestBody UserDTO userDTO){
        service.registerUser(userDTO);
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("register");
    }

    @PostMapping("/token")
    public void token(){
    }



}
