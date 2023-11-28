package br.com.blog.posts.controller;

import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.service.PostService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/post")
public class PostsController {

    final
    PostService service;


    public PostsController(PostService service) {
        this.service = service;
    }

    @GetMapping("profile")
    public ModelAndView profile(){
        return new ModelAndView("profile");
    }



    @GetMapping("/teste")
    public void teste(){
        System.out.println("SDIFKHJAOPSDIGHJ");
    }




}
