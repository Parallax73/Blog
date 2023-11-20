package br.com.blog.posts.repository;

import br.com.blog.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    Post findByAuthor(String author);

}
