package br.com.blog.posts.entity;

import br.com.blog.posts.dto.PostDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private String author;
    private String tags;


    public Post(PostDTO post){
        this.text= post.text();
        this.dateTime= LocalDateTime.now();
    }

    public void editPost(String text){
        this.text = text;
        this.dateTime  = LocalDateTime.now();
    }

    public void setAuthor(String author){
        this.author=author;
    }
}
