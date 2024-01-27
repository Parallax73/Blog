package br.com.blog.gateway.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OptionsDTO(
        @Pattern(regexp = "^{1,100}")
        String bio,
        @NotBlank
        String backgroundImg,
        @NotBlank
        String profileImg,
        Color fontColor
) {
}
