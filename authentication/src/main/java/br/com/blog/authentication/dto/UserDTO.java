package br.com.blog.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @Email
        String email,

        @Pattern(regexp = "([a-zA-Z]\\s){3,20}")
        String password
) {
}
