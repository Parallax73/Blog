package br.com.authentication.email;


public record Email(
        String to,
        String subject,
        String body
) {
}
