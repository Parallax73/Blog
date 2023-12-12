package br.com.blog.posts.repository;

import br.com.blog.posts.entity.Post;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;


public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findAllByOrderByDateTimeAsc();
    Post findAllByAuthorOrTextOrTags(String user);

    @Query("{'_id': ?0}")
    @Update("{'$set': {'text': ?1}}")
    void updateText(String id, String updatedText);
}
