package br.com.blog.authentication.auth.entity;

import br.com.blog.authentication.auth.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    private String username;
    private String password;
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Role role;



    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public User (UserDTO dto){
        this.email = dto.email();
        this.username=dto.username();
        this.password=encoder.encode(dto.password());
        this.creationDate = LocalDate.now();
    }

    public Boolean passDecode(String tryPass){
        return encoder.matches(tryPass, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
