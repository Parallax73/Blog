package br.com.blog.authentication.email.dto;

public record Email(
        String to,
        String subject,
        String body
) {
}
