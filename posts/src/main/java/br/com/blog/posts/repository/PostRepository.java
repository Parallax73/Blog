package br.com.blog.posts.repository;

import br.com.blog.posts.entity.Post;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;


public interface PostRepository extends MongoRepository<Post,String> {

    List<Post> findAllByOrderByDateTimeAsc();

    @Query("{'text': {$regex : '?0'}}" +
            "{'author': {$regex : '?0'}}")
    List<Post> findAllByAuthorOrTextOrderByDateTimeAsc(String keyword);


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
