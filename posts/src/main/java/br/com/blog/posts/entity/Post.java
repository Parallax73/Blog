package br.com.blog.posts.entity;


import br.com.blog.posts.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private String id;
    private String text;
    private LocalDate dateTime;
    private String author;
    private Integer upvoteCount;
    private Integer downvoteCount;


    public Post(PostDTO post){
        this.text= post.text();
        this.upvoteCount=0;
        this.downvoteCount=0;
        this.dateTime = LocalDate.now();
    }


    public void setAuthor(String author) {
        this.author=author;
    }


    public String getSubText(){
        return text.substring(0,80);
    }


    public String getVoteCount(){
        if (upvoteCount>downvoteCount){
            return "+ "+upvoteCount;
        }
        return "- "+downvoteCount;

    }
}
