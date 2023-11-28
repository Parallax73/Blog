package br.com.blog.posts.repository;

import br.com.blog.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;


public interface PostRepository extends MongoRepository<Post,Long> {

}
