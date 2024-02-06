package com.sparta.todo.service;

import com.sparta.todo.dto.CommentRequestDto;
import com.sparta.todo.dto.CommentResponseDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.CommentRepository;
import com.sparta.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    // Comment 작성
    public CommentResponseDto todoComment(Long todoId, CommentRequestDto requestDto, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("Todo가 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto, user, todo);
        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }

    // Comment 수정
    public CommentResponseDto updateComment(Long todoId, Long id, CommentRequestDto requestDto, User user) {

        // todo 존재하는지 확인
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("Todo가 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow (
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    // Comment 삭제
    public void deleteComment(Long todoId, Long id, User user) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("Todo가 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow (
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        commentRepository.delete(comment);
    }
}
