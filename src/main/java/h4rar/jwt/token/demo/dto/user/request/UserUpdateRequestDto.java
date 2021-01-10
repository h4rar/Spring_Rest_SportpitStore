package h4rar.jwt.token.demo.dto.user.request;

import lombok.Data;

@Data
public class UserUpdateRequestDto {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
