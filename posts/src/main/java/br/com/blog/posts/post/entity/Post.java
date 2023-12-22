package br.com.blog.posts.post.entity;


import br.com.blog.posts.comment.entity.Comment;
import br.com.blog.posts.post.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    private List<Comment> comments;


    public Post(PostDTO post){
        this.text= post.text();
        this.upvoteCount=0;
        this.downvoteCount=0;
        this.dateTime = LocalDate.now();
        comments = new ArrayList<>();
    }


    public void setAuthor(String author) {
        this.author=author;
    }


    public String getSubText(){
        if (text.length()>80){
            return text.substring(0,80);
        }
        return text;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public String getVoteCount(){
        if (upvoteCount>downvoteCount){
            return "+ "+upvoteCount;
        } else if (upvoteCount==0&&downvoteCount==0){
            return "0";
        }
        return "- "+downvoteCount;

    }
}
