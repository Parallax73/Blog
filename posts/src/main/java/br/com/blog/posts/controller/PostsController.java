package br.com.blog.posts.controller;



import br.com.blog.authentication.config.security.TokenDTO;
import br.com.blog.posts.dto.PostDTO;
import br.com.blog.posts.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {

    final
    PostService service;


    public PostsController(PostService service) {
        this.service = service;
    }



    @PostMapping("/create-post")
    public void createPost(PostDTO dto){
        service.createPost(dto);
    }




    /*@PostMapping("/teste")
    public ResponseEntity<?> getToken(){
        return ResponseEntity.ok().body(service.getAuthor("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBdXRoQmxvZyIsInN1YiI6ImdhYnJpZWxzYW50b3MwMjEwQGdtYWlsLmNvbSIsImV4cCI6MTY5OTkxODY3Nn0._ByqbWuQ_TvFqDBf-OKo8pAjv3FPBZhF2fyGJ1R3AZg"));
    }*/
}
