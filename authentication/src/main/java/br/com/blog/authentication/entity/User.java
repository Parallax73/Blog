package br.com.blog.authentication.entity;

import br.com.blog.authentication.dto.UserDTO;
import br.com.blog.authentication.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity(name = "User")
@Table(name = "Users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;
    private LocalDateTime createDate;
    private Role role;



    public User(UserDTO userDTO){
        this.email= userDTO.email();
        this.username=userDTO.username();
        this.password= hashPassword(userDTO.password());
        this.createDate = LocalDateTime.now();
        this.role=Role.USER;
    }


    public String hashPassword(String password){
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        return encoder.encode(password);
    }


}
