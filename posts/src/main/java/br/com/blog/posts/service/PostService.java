package br.com.blog.posts.service;


import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.entity.Post;
import br.com.blog.posts.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PostService {


    final
    PostRepository repository;



    public PostService(PostRepository repository) {
        this.repository = repository;

    }


    public ResponseEntity<?> createPost(PostDTO dto){
        try {
            var post = new Post(dto);
            post.setAuthor("dasilva@gmail.com");
            repository.save(new Post(dto));
            return ResponseEntity.ok().build();
         } catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e);
        }
    }

}
