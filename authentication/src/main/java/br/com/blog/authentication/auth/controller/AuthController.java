package br.com.blog.authentication.auth.controller;

import br.com.blog.authentication.auth.dto.LoginDTO;
import br.com.blog.authentication.auth.dto.UserDTO;
import br.com.blog.authentication.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthService service;


    //Thymeleaf endpoints

    @GetMapping("/login")
    public ModelAndView login(){
        var mv = new ModelAndView("login");
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        var mv = new ModelAndView("register");
        return mv;
    }








    @PostMapping("/register-user")
    @Transactional
    public void createUser(@RequestBody UserDTO dto){
        service.createNewUser(dto);
    }

    @PostMapping("login/login-user")
    public void loginUser(@RequestBody LoginDTO dto) {
        service.loginUser(dto);
    }

}
