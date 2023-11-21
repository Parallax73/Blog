package br.com.blog.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorService {

    private String author;

    public String getAuthor(){
        if (author!=null) {
            log.info("author is not null");
            return author;
        }
        log.info("Author is null");
        return null;
    }

    public void setAuthor(String s){
        if (author==null){
            author=s;
            log.info("Author settled correctly {}", author);

        }
    }

    public void logout(){
        if (author!=null) {
            log.info("Logout correctly");
            author = null;
        }
        log.info("Not logged");
    }
}
