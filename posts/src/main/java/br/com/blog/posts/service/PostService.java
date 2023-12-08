package br.com.blog.posts.service;


import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.entity.Post;
import br.com.blog.posts.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostService {

    @Autowired
    PostRepository repository;

    public ResponseEntity<?> createPost(PostDTO dto){
        var post = new Post(dto);
        log.info("Tried to create a post");
        post.setAuthor("dasilva@gmail.com");
        repository.insert(post);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> editPost(String id, EditDTO editDTO){
        try {
            var post = repository.findById(id).get();
            post.editPost(editDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    public List<Post> listAllPosts(){
        return repository.findAllByOrderByDateTimeAsc();
    }

    public ResponseEntity<?> deletePost(String id){
        log.info("delete called");
        try {
            var post = repository.findById(id);
            if (post.isPresent()){
                repository.deleteById(id);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    public Optional<Post> getPostById(String id){
        try{
            var post = repository.findById(id);
            if (post.isPresent()){
                return post;
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }
}
