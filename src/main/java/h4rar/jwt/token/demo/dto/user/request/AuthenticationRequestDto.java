package h4rar.jwt.token.demo.dto.user.request;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}