package br.com.blog.posts.service;

import br.com.blog.posts.dto.CommentDTO;
import br.com.blog.posts.entity.Comment;
import br.com.blog.posts.repository.CommentRepository;
import br.com.blog.posts.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;


    public void newComment(String id,CommentDTO commentDTO){
        var comment = new Comment(commentDTO);
        comment.setPostId(id);
        var post =postRepository.findById(id).get();
        post.addComment(comment);
        log.info("Tried to create a comment for post {}",post.getId());
        commentRepository.save(comment);
        postRepository.save(post);
    }
}
