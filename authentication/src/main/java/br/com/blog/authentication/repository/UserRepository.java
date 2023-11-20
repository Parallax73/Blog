package br.com.blog.authentication.repository;

import br.com.blog.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);

}
