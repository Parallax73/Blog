package br.com.blog.posts.controller;


import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.service.PostService;
import jakarta.ws.rs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@RestController
@RequestMapping("/post")
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
    public ModelAndView getPost(@PathVariable String id){
        ModelAndView mv = new ModelAndView("post");
        mv.addObject("post",service.getPostById(id));
        return mv;
    }

    @GetMapping("/{user}")
    public ModelAndView profile(@PathVariable("user") String user){
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("post",service.getPostByUser(user));
        return mv;
    }


}
