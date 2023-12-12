package br.com.blog.posts.controller;


import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.service.PostService;
import jakarta.ws.rs.Path;
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


    @PatchMapping("{id}/edit-post")
    @Transactional
    public void editPost(@PathVariable("id") String id, @RequestBody EditDTO dto){
        service.editPost(id,dto);
        log.info("edit-post was called");
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deletePost(@PathVariable("id") String id){
        return service.deletePost(id);
    }

    @GetMapping("/{id}")
    public ModelAndView getPost(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView("post");
        if (service.getPostById(id).isPresent()) {
            mv.addObject("postText", service.getPostById(id).get().getText());
            mv.addObject("buttontxt", "Homepage");
            log.info("found");
            return mv;
        } else {
            mv.addObject("buttontxt", "Homepage");
            mv.addObject("postText", null);
            log.info("Not found :(");
            return mv;
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
        mv.addObject("buttontxt", "Create");
        log.info("create method called");
        return mv;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") String id){
        ModelAndView mv = new ModelAndView("post");
        if (service.getPostById(id).isPresent()) {
            var post = service.getPostById(id);
            mv.addObject("postText", post.get().getText());
            mv.addObject("postId", post.get().getId());
            mv.addObject("buttontxt", "Edit");
            return mv;
        } else {
            mv.addObject("buttontxt", "Edit");
            mv.addObject("postText", null);
            return mv;
        }
    }




}
