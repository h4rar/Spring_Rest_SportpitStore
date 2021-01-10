package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.user.response.UserResponseDto;
import h4rar.jwt.token.demo.model.User;
import h4rar.jwt.token.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody User user){
        return userService.register(user);
    }
}