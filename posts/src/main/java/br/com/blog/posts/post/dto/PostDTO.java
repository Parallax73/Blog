package br.com.blog.posts.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PostDTO(

        @NotBlank
                @Pattern(regexp = "^{1,500}")
        String text,
        @NotBlank
        @NotNull
        String author) {
}
