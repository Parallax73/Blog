package br.com.blog.authentication.entity;

import br.com.blog.authentication.dto.UserDTO;
import br.com.blog.authentication.enums.Role;
import br.com.blog.authentication.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "Users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createDate;
    private String status;
    private Role role;



    public User(UserDTO userDTO){
        this.email= userDTO.email();
        this.password= hashPassword(userDTO.password());
        this.createDate = LocalDateTime.now();
        this.status=Status.ACTIVE.toString();
        this.role=Role.USER;
    }

    public void deactivateUser(){
        this.status=Status.UNACTIVE.toString();
    }

    public String hashPassword(String password){
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public Boolean checkPassword(String pass){
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        return encoder.matches(pass,password);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
