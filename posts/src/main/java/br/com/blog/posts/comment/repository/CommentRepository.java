package br.com.blog.posts.comment.repository;

import br.com.blog.posts.comment.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {

    List<Comment> findAllByPostId(String id);

    List<Comment> findAllByAuthor(String author);
}
