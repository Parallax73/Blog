package br.com.blog.gateway.auth;

import br.com.blog.gateway.user.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank
  private String username;
  @NotBlank
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  private Role role;
}
