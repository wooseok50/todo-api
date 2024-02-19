package com.sparta.todo.comment.controller;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.sevice.CommentService;
import com.sparta.todo.global.exception.ResponseStatusDto;
import com.sparta.todo.global.exception.Status;
import com.sparta.todo.global.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo/comment")
public class CommentController {

    private final CommentService commentService;

    // Comment 작성
    @PostMapping("/{todoId}")
    public CommentResponseDto todoComment(@PathVariable Long todoId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.todoComment(todoId, requestDto, userDetails.getUser());
    }

    // Comment 수정
    @PutMapping("/{todoId}/{id}")
    public CommentResponseDto updateComment(@PathVariable Long todoId, @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(todoId, id, requestDto, userDetails.getUser());
    }

    // Comment 삭제
    @DeleteMapping("/{todoId}/{id}")
    public ResponseStatusDto deleteComment(@PathVariable Long todoId, @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(todoId, id, userDetails.getUser());
        return new ResponseStatusDto(Status.COMMENT_DELETE);
    }
}
