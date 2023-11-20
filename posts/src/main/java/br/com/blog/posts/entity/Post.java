package br.com.blog.posts.entity;

import br.com.blog.posts.dto.PostDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "Posts")
@Entity(name = "Post")
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
