package br.com.blog.posts.comment.controller;

import br.com.blog.posts.comment.dto.CommentDTO;
import br.com.blog.posts.comment.service.CommentService;
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

    @GetMapping("comment/{id}")
    public ModelAndView getComment(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView("post");
        if (service.getComment(id).isPresent()) {
            mv.addObject("postText", service.getComment(id).get().getText());
            mv.addObject("buttontxt", "Homepage");
            mv.addObject("postIdd",service.getComment(id).get().getId());
            mv.addObject("comment", service.getComment(id));
            log.info("found");
        } else {
            mv.addObject("buttontxt", "Homepage");
            mv.addObject("postText", null);
            log.info("Not found :(");
        }
        return mv;
    }

    @PostMapping("{id}/create-comment/new-comment")
    @Transactional
    public void createComment(@PathVariable("id") String id,@RequestBody CommentDTO dto){
        log.info("post method called");
        service.newComment(id,dto);
    }
}
