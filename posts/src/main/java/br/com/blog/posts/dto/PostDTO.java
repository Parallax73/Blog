package br.com.blog.posts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PostDTO(

        @NotBlank
                @Pattern(regexp = "^{1,500}")
        String text) {
}
