package br.com.blog.posts.service;


import br.com.blog.authentication.config.security.TokenService;
import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.entity.Post;
import br.com.blog.posts.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Import(br.com.blog.authentication.config.security.TokenService.class)
public class PostService {

/*
    1- Criar post (id, texto, data criação e autor)
    2- listar posts
    3- editar posts
    4- apagar posts
  */

    @Autowired
    TokenService service;

    @Autowired
    PostRepository repository;



    public ResponseEntity<?> createPost(PostDTO dto){
        if (service.getToken().length()<15 || service.getToken()==null){
            return ResponseEntity.badRequest().build();
        }
        try {
            var post = new Post(dto);
            post.setAuthor(service.getSubject(service.getToken()));
            repository.save(post);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
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

    public void token(){
        service.getToken();
    }
}
