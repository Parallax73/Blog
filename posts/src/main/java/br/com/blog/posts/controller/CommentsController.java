package br.com.blog.posts.controller;

import br.com.blog.posts.dto.CommentDTO;
import br.com.blog.posts.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/post")
@Slf4j
public class CommentsController {

    @Autowired
    CommentService service;

    @GetMapping("{id}/create-comment")
    public ModelAndView create(@PathVariable("id") String id){
        ModelAndView mv = new ModelAndView("post");
        mv.addObject("buttontxt", "Create comment");
        mv.addObject("postIdd", id);
        log.info("get method called");
        return mv;
    }


    @PostMapping("{id}/create-comment/new-comment")
    @Transactional
    public void createComment(@PathVariable("id") String id,@RequestBody CommentDTO dto){
        log.info("post method called");
        service.newComment(id,dto);
    }
}
