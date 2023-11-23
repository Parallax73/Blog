package br.com.blog.posts.controller;

import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/posts")
public class PostsController {

    final
    PostService service;


    public PostsController(PostService service) {
        this.service = service;
    }

    @PostMapping("/create-post")
    @Transactional
    public void createPost(@RequestBody PostDTO dto){
        service.createPost(dto);
    }

    @GetMapping("/teste")
    public void teste(){
        log.info("funcionou");
    }

}
