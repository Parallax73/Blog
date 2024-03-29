package br.com.blog.posts.post.controller;


import br.com.blog.posts.post.dto.EditDTO;
import br.com.blog.posts.post.dto.PostDTO;
import br.com.blog.posts.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/post")
@Slf4j
@RequiredArgsConstructor
public class PostsController {

    final
    PostService service;

    //Thymeleaf endpoints

    @GetMapping("/home")
    public ModelAndView home(/*@PathVariable("page") int page*/){
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("post",service.listAllPosts(0));
        return mv;
    }

    @GetMapping("/home/upvote/")
    public ModelAndView sortByUpvote(){
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("post",service.listAllByUpvote());
        return mv;
    }

    @GetMapping("/home/downvote")
    public ModelAndView sortByDownvote(){
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("post",service.listAllByDownvote());
        return mv;
    }


    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView mv = new ModelAndView("post");
        mv.addObject("buttontxt", "Create");
        log.info("create method called");
        return mv;
    }

    @GetMapping("/home/{author}/")
    public ModelAndView findByAuthor(@PathVariable("author") String author){
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("post",service.listAllByAuthor(author));
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView getPost(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView("post");
        if (service.getPostById(id).isPresent()) {
            mv.addObject("postText", service.getPostById(id).get().getText());
            mv.addObject("buttontxt", "Homepage");
            mv.addObject("postIdd",service.getPostById(id).get().getId());
            mv.addObject("comment", service.listAllCommentByPost(id));
            log.info("found");
        } else {
            mv.addObject("buttontxt", "Homepage");
            mv.addObject("postText", null);
            log.info("Not found :(");
        }
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



    @GetMapping("/user/{author}")
    public ModelAndView findByUser(@PathVariable("author") String author){
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("post",service.postsByUser(author));
        return mv;
    }


    //api endpoints
    @PostMapping("/create-post")
    @Transactional
    public void createPost(@RequestBody PostDTO dto){
        service.createPost(dto);
        log.info("Created a new post");
    }
    @PatchMapping("{id}/edit-post")
    @Transactional
    public void editPost(@PathVariable("id") String id, @RequestBody EditDTO dto){
        service.editPost(id,dto);
        log.info("edit-post was called");
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public void deletePost(@PathVariable("id") String id) throws Exception {
        service.deletePost(id);
        log.info("Delete the post: {}",id);
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("keywords") String keywords) {
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("post", service.searchPosts(keywords));
        return mv;
    }

    @PatchMapping("/upvote/{id}")
    public void upvote(@PathVariable("id") String id){
        service.upvote(id);
    }

    @PatchMapping("/downvote/{id}")
    public void downvote(@PathVariable("id") String id){
        service.downvote(id);
    }

}
