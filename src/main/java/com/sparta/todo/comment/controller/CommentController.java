package com.sparta.todo.comment.controller;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.sevice.CommentService;
import com.sparta.todo.global.response.CommonResponse;
import com.sparta.todo.global.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todo/{todoId}")
    public ResponseEntity<CommonResponse<Void>> todoComment(@PathVariable Long todoId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.todoComment(todoId, commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(CommonResponse.<Void>builder().message("댓글 생성 완료").build());
    }

    @PutMapping("/todo/{todoId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> updateComment(@PathVariable Long todoId,
            @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(todoId, commentId, commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Void>builder().message("댓글 수정 완료").build());
    }

    @DeleteMapping("/todo/{todoId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(todoId, commentId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                CommonResponse.<Void>builder().message("댓글 삭제 완료").build());
    }
}
