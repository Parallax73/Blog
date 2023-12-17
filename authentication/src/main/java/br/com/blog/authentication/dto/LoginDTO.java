package br.com.blog.authentication.dto;

public record LoginDTO(String password,String clientId, String grantType, String user) {
}
