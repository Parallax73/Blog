package br.com.blog.posts.service;


import br.com.blog.core.service.AuthorService;
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
@Import(br.com.blog.core.service.AuthorService.class)
public class PostService {


    final
    PostRepository repository;

    final AuthorService authorService;

    public PostService(PostRepository repository, AuthorService authorService) {
        this.repository = repository;
        this.authorService = authorService;
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

    public ResponseEntity<?> editPost(EditDTO dto , Long id) {
        try {
            Optional<Post> post = repository.findById(id);
            if (post.isPresent()) {
                post.get().editPost(dto.text());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> deletePost(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> listAllPosts(){
        try {
            return ResponseEntity.ok().body(repository.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> listByUser(String author){
        try {
            return ResponseEntity.ok().body(repository.findByAuthor(author));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
