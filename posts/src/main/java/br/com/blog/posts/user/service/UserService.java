package br.com.blog.posts.user.service;

import br.com.blog.gateway.user.OptionsDTO;
import br.com.blog.gateway.user.User;
import br.com.blog.gateway.user.UserOptions;
import br.com.blog.gateway.user.UserRepository;
import br.com.blog.posts.comment.entity.Comment;
import br.com.blog.posts.comment.repository.CommentRepository;
import br.com.blog.posts.post.entity.Post;
import br.com.blog.posts.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    PostRepository postRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;


    public List<Post> getAllPostsByUser(String user) {
        return postRepository.findAllByAuthorOrderByDateTimeDesc(user);
    }

    public List<Comment> getAllCommentsByUser(String user) {
        return commentRepository.findAllByAuthor(user);
    }

    public User getUser(String username) {
        try {
            var user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                return user.get();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ResponseEntity<?> setUserOptions(String username, OptionsDTO dto) {
        try {
            var userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {

                var user = userOptional.get();
                user.setUserOptions(new UserOptions(dto));
                user.setBio(dto.bio());
                userRepository.save(user);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
