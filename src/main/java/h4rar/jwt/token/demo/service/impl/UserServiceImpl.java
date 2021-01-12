package h4rar.jwt.token.demo.service.impl;

import h4rar.jwt.token.demo.dto.user.request.UserUpdateRequestDto;
import h4rar.jwt.token.demo.dto.user.response.UserResponseDto;
import h4rar.jwt.token.demo.exception.BadRequestException;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import h4rar.jwt.token.demo.repository.*;
import h4rar.jwt.token.demo.security.jwt.JwtTokenProvider;
import h4rar.jwt.token.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserResponseDto register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setBasicStatus(BasicStatus.ACTIVE);
        user.setPhone(user.getPhone());
        userRepository.save(user);
        return UserResponseDto.fromUser(user);
    }

    @Override
    public UserResponseDto getCurrentUser(HttpServletRequest req) {
        User user = jwtTokenProvider.getUserFromHttpServletRequest(req);
        return UserResponseDto.fromUser(user);
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("Пользователя с именем " + username + "не существует"));
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

//    @Override
//    public User getUserFromHttpServletRequest(HttpServletRequest req) {
//        String token = jwtTokenProvider.resolveToken(req);
//        return jwtTokenProvider.getUser(token);
//    }

    @Override
    public UserResponseDto updateUser(HttpServletRequest req, UserUpdateRequestDto updateRequestDto) {
        User user = jwtTokenProvider.getUserFromHttpServletRequest(req);

        if (!StringUtils.isBlank(updateRequestDto.getEmail())
        ) {
            user.setEmail(updateRequestDto.getEmail());
        }
        if (!StringUtils.isBlank(updateRequestDto.getFirstName())
        ) {
            user.setFirstName(updateRequestDto.getFirstName());
        }
        if (!StringUtils.isBlank(updateRequestDto.getLastName())
        ) {
            user.setLastName(updateRequestDto.getLastName());
        }
        if (!StringUtils.isBlank(updateRequestDto.getPhone())
        ) {
            user.setPhone(updateRequestDto.getPhone());
        }
        if (!StringUtils.isBlank(updateRequestDto.getUsername())
        ) {
            user.setUsername(updateRequestDto.getUsername());
        }
        userRepository.save(user);
        return UserResponseDto.fromUser(user);
    }
}
