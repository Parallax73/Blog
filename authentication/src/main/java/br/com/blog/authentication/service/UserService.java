package br.com.blog.authentication.service;


import br.com.blog.authentication.dto.LoginDTO;
import br.com.blog.authentication.dto.UserDTO;
import br.com.blog.authentication.entity.User;
import br.com.blog.authentication.repository.UserRepository;
import br.com.blog.authentication.utils.sender.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class UserService {

    final
    EmailSender sender;

    final
    UserRepository userRepository;

    public UserService(EmailSender sender, UserRepository userRepository) {
        this.sender = sender;
        this.userRepository = userRepository;
    }


    public ResponseEntity<String> token(@RequestBody LoginDTO dto){

        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", dto.clientId());
        formData.add("username", dto.user());
        formData.add("password", dto.password());
        formData.add("grantType", dto.grantType());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(formData,headers);


        var result = rt.postForEntity("http://localhost:8181/realms/master/protocol/openid-connect/token",entity, String.class);

        return result;
    }

    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        log.info("Register was called");
        var user = new User(userDTO);
        try {
            userRepository.save(user);
            log.info("New user {} created {} ", user.getEmail(), user.getCreateDate());
            sender.sendEmail(user.getEmail());
            return ResponseEntity.ok().build();
        } catch (UnexpectedRollbackException e) {
            log.error("Tried to create an user with an already used email");
            return ResponseEntity.internalServerError().build();
        }
    }




}