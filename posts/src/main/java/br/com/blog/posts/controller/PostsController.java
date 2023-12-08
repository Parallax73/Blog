package br.com.blog.posts.controller;


import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@RestController
@RequestMapping("/post")
@Slf4j
public class PostsController {

    final
    PostService service;


    public PostsController(PostService service) {
        this.service = service;
    }



    @GetMapping("/home")
    public ModelAndView profile(){
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("post",service.listAllPosts());
        return mv;
    }



    @PostMapping("/create-post")
    @Transactional
    public void createPost(@RequestBody PostDTO dto){
        service.createPost(dto);
    }

    @PatchMapping
    @Transactional
    public void editPost(String id, EditDTO dto){
        service.editPost(id,dto);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deletePost(@PathVariable("id") String id){
        return service.deletePost(id);
    }

    @GetMapping("/{id}")
    public ModelAndView getPost(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView("post");
        if (service.getPostById(id) != null) {
            mv.addObject("postText", service.getPostById(id).get().getText());
            mv.addObject("buttontxt", "Homepage");
            log.info("found");
            return mv;
        } else {
            log.info("Not found :(");
            return null;
        }
    }

    /*@GetMapping("/{user}/posts")
    public ModelAndView profile(@PathVariable("user") String user){
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("post",service.getPostByUser(user));
        return mv;
    }*/

    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView mv = new ModelAndView("post");
        mv.addObject("msg", "Create post");
        return mv;
    }

    /*@GetMapping("/edit")
    public ModelAndView edit(){
        ModelAndView mv = new ModelAndView("post");
        mv.addObject("post",service.)
        mv.addObject("msg", "Edit post");
        return mv;
    }*/




}
