package br.com.blog.posts.post.repository;

import br.com.blog.posts.comment.entity.Comment;
import br.com.blog.posts.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;


public interface PostRepository extends MongoRepository<Post,String> {


    List<Post> findAllByOrderByUpvoteCountDesc();

    List<Post> findAllByOrderByDownvoteCountDesc();

    List<Post> findAllByOrderByDateTimeDesc(Pageable pageable);

    @Query("{}")
    List<Post> findPaginatedPosts(Pageable pageable);

    @Query("{'_id' :  ?0}" +
            "{'$set' : comments}")
    List<Comment> findAllCommentsByPost(String id);

    @Query("{'text': {$regex : '?0'}}" +
            "{'author': {$regex : '?0'}}")
    List<Post> findAllByAuthorOrTextOrderByDateTimeAsc(String keyword);

    List<Post> findAllByAuthorOrderByDateTimeDesc(String author);


    @Query("{'_id': ?0}")
    @Update("{'$set': {'text': ?1}}")
    void updateText(String id, String updatedText);

    @Query("{'_id': ?0}")
    @Update("{'$inc': {'upvoteCount': 1}}")
    void updateUpvote(String id);

    @Query("{'_id': ?0}")
    @Update("{'$inc': {'downvoteCount': 1}}")
    void updateDownvote(String id);
}
