package br.com.blog.gateway.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @GetMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView registerPage(){
        return new ModelAndView("register");
    }

    @PostMapping("/register-user")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login-user")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/get-claims/{token}")
    @ResponseBody
    public ResponseEntity<ClaimResponse> teste(@PathVariable("token") String token) {
            return ResponseEntity.ok(service.getClaims(token));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
