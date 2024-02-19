package com.sparta.todo.user.controller;

import com.sparta.todo.exception.ErrorResponse;
import com.sparta.todo.response.CommonResponse;
import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.service.AuthService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<List<ErrorResponse>>> signup(
            @RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {

        // Validation 예외처리
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<ErrorResponse> ErrorResponseList = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                ErrorResponse exceptionResponse = new ErrorResponse(fieldError.getDefaultMessage());
                ErrorResponseList.add(exceptionResponse);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                    CommonResponse.<List<ErrorResponse>>builder().message("회원가입 실패")
                            .data(ErrorResponseList).build());
        }

        authService.signup(signupRequestDto);

        return ResponseEntity.status(HttpStatus.OK.value()).body(
                CommonResponse.<List<ErrorResponse>>builder().message("회원가입 성공").build()
        );
    }
}
