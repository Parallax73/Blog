package br.com.blog.posts.repository;

import br.com.blog.posts.entity.Post;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findAllByOrderByDateTimeAsc();
}
