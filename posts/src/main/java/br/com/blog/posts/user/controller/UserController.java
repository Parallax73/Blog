package br.com.blog.posts.user.controller;

import br.com.blog.gateway.user.OptionsDTO;
import br.com.blog.posts.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService service;

    @GetMapping("/{user}")
    public ModelAndView getUser(@PathVariable("user") String user) {
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("user",service.getUser(user));
        mv.addObject("posts", service.getAllPostsByUser(user));
        mv.addObject("comments", service.getAllCommentsByUser(user));
        return mv;
    }

    @PostMapping("/{user}/customize")
    public ResponseEntity<?> setCustom(@PathVariable ("user") String user, @RequestBody OptionsDTO dto){
        return service.setUserOptions(user, dto);
    }
}
