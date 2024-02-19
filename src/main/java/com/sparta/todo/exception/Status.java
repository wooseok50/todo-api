package com.sparta.todo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Status {

    SIGNUP_SUCCESS(200, "회원가입 성공"),
    SIGNUP_FAILURE(400, "회원가입 실패"),
    LOGIN_SUCCESS(200, "로그인 성공"),
    LOGIN_FAILURE(400, "로그인 실패"),
    POST_DELETE(200, "게시글 삭제 완료"),
    COMMENT_DELETE(200, "댓글 삭제 완료"),
    ;

    int statusCode;
    String code;

    Status(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
