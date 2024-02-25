package com.sparta.todo.user.service;

import com.sparta.todo.global.exception.InvalidInputException;
import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 username 입니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new InvalidInputException("해당 User는 존재하지 않습니다.")
        );
    }
}
