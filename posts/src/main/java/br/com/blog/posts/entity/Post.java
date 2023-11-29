package br.com.blog.posts.entity;

import br.com.blog.posts.dto.EditDTO;
import br.com.blog.posts.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private String id;
    private String text;
    private LocalDateTime dateTime;
    private String author;
    private List<String> tags;


    public Post(PostDTO post){
        this.text= post.text();
        this.dateTime= LocalDateTime.now();
    }

    public void editPost(EditDTO dto){
        this.text = dto.text();
        this.dateTime  = LocalDateTime.now();
    }

    public void setAuthor(String author){
        this.author=author;
    }
}
