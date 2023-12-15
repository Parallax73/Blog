package br.com.blog.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Pattern(regexp = "([a-zA-Z]\\s){4,15}")
        String username,

        @Pattern(regexp = "([a-zA-Z]\\s){3,20}")
        String password
) {
}
