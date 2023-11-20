package br.com.blog.authentication.config.security;

import br.com.blog.authentication.entity.User;
import br.com.blog.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;



    public CustomUserDetailsService(){

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("user name not found");
        } else {
            return user;
        }
    }

}
