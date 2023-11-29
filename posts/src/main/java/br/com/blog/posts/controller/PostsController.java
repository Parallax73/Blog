package br.com.blog.posts.controller;


import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostsController {

    final
    PostService service;


    public PostsController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView main(){
        return new ModelAndView("index");
    }

    @GetMapping("/profile")
    public ModelAndView profile(){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("post",service.listAllPosts());
        return mv;
    }



    @PostMapping("/teste")
    @Transactional
    public void teste(){
        service.createPost();
        log.info("CRIADO COM SUCESSO");
    }

    @PatchMapping
    @Transactional
    public void editPost(String id, EditDTO dto){
        service.editPost(id,dto);
    }
}
