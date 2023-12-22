package br.com.blog.authentication.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @NotBlank
        @Email
                @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,

        @NotBlank
        @Pattern(regexp = "^{3,15}")
        String username,

        @NotBlank
        @Pattern(regexp = "^{3,100}")
        String password
) {
}
