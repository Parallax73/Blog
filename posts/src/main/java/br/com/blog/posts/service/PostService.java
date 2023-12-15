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

    public void createPost(PostDTO dto){
        var post = new Post(dto);
        log.info("Tried to create a post");
        post.setAuthor("parallax");
        repository.insert(post);
    }


    public void deletePost(String id) throws Exception {
        log.info("delete called");
        try {
            var post = repository.findById(id);
            if (post.isPresent()){
                repository.deleteById(id);
            }
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public ResponseEntity<?> editPost(String id,EditDTO editDTO){
        try {
            repository.updateText(id,editDTO.text());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }



    public List<Post> listAllPosts(){
        return repository.findAllByOrderByDateTimeAsc();
    }



    public Optional<Post> getPostById(String id) {
        return repository.findById(id);
    }


    public List<Post> searchPosts(String searchCon) {
        try {
            var posts=repository.findAllByAuthorOrTextOrderByDateTimeAsc(searchCon);
            if (posts==null||posts.size()==0){
                return null;
            }
            log.info("searching for> {} ", searchCon);
            return repository.findAllByAuthorOrTextOrderByDateTimeAsc(searchCon);
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public void upvote(String id){
        repository.updateUpvote(id);
        log.info("upvote post with id {}",id);
    }


}
