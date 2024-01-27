package br.com.blog.posts.comment.entity;


import br.com.blog.posts.comment.dto.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;
    private String author;
    private String text;
    private LocalDate creationDate;
    private String postId;


    public Comment (CommentDTO dto){
        this.text=dto.comment();
        this.creationDate = LocalDate.now();
    }

    public String getSubText(){
        if (text.length()>80){
            return text.substring(0,80);
        }
        return text;
    }

    public String getAuthor(){
        return author;
    }
}
