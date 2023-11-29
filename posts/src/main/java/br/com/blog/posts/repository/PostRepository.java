package br.com.blog.posts.repository;

import br.com.blog.posts.entity.Post;

import org.springframework.data.mongodb.repository.MongoRepository;




public interface PostRepository extends MongoRepository<Post,String> {

}
