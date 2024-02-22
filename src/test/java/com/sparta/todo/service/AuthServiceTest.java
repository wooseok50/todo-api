package com.sparta.todo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.RefreshTokenRepository;
import com.sparta.todo.user.repository.UserRepository;
import com.sparta.todo.user.service.AuthService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    private User user;

    @Test
    @DisplayName("회원가입 test - 성공")
    void signupTest() {

        // Given
        String username = "name";
        String password = "name1234";

        SignupRequestDto requestDto = new SignupRequestDto(username, password);
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        // When
        authService.signup(requestDto);

        // Then
        assertDoesNotThrow(() -> authService.signup(requestDto));
    }

    @Test
    @DisplayName("회원가입 username 중복 test")
    void existUsernameTest() {

        // Given
        String username = "name";
        String password = "name1234";

        SignupRequestDto requestDto = new SignupRequestDto(username, password);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(new User()));

        // Then
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
            authService.signup(requestDto);
        });
        assertEquals("400 BAD_REQUEST \"중복된 username 입니다.\"", e.getMessage());
    }
}
