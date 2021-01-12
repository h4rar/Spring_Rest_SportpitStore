package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.user.request.UserUpdateRequestDto;
import h4rar.jwt.token.demo.dto.user.response.UserResponseDto;
import h4rar.jwt.token.demo.exception.BadRequestException;
import h4rar.jwt.token.demo.model.User;
import h4rar.jwt.token.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/users/")
public class UserRestControllerV1 {

    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public UserResponseDto getUserById(
            @PathVariable(name = "id") Long id
    ) {
        User user = userService.findById(id);
        if (user == null) {
            throw new BadRequestException("Пользователя с таким id не сущствует");
        }
        UserResponseDto result = UserResponseDto.fromUser(user);
        return result;
    }

    @PatchMapping()
    public UserResponseDto updateUser(
            HttpServletRequest req,
            @RequestBody UserUpdateRequestDto updateRequestDto
    ) {
        return userService.updateUser(req, updateRequestDto);
    }

    @GetMapping("lk")
    public UserResponseDto getCurrentUser(
            HttpServletRequest req
    ) {
        return userService.getCurrentUser(req);
    }
}
