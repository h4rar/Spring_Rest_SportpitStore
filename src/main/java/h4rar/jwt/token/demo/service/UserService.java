package h4rar.jwt.token.demo.service;

import h4rar.jwt.token.demo.dto.user.request.UserUpdateRequestDto;
import h4rar.jwt.token.demo.dto.user.response.UserResponseDto;
import h4rar.jwt.token.demo.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    UserResponseDto register(User user);

    UserResponseDto getCurrentUser(HttpServletRequest req);

    UserResponseDto updateUser(HttpServletRequest req, UserUpdateRequestDto updateRequestDto);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);

//    User getUserFromHttpServletRequest (HttpServletRequest req);
}
