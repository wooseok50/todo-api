package com.sparta.todo.user.controller;


import com.sparta.todo.exception.ResponseStatusDto;
import com.sparta.todo.exception.Status;
import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseStatusDto signup(@RequestBody @Valid SignupRequestDto requestDto,
            BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return new ResponseStatusDto(Status.SIGNUP_FAILURE);
        }
        userService.signup(requestDto);

        return new ResponseStatusDto(Status.SIGNUP_SUCCESS);

    }
}
