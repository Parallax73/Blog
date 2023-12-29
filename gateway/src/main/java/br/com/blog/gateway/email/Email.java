package br.com.blog.gateway.email;


public record Email(
        String to,
        String subject,
        String body
) {
}
