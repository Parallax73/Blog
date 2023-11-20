package br.com.blog.authentication.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("admin"),
    USER ("user");

    Role(String admin) {
    }
}
