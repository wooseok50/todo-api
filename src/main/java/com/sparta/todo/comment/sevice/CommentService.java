package com.sparta.todo.comment.sevice;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.entity.Comment;
import com.sparta.todo.comment.repository.CommentRepository;
import com.sparta.todo.global.exception.AuthenticationException;
import com.sparta.todo.global.exception.InvalidInputException;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.service.TodoService;
import com.sparta.todo.user.entity.User;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoService todoService;

    public void todoComment(Long todoId, CommentRequestDto commentRequestDto, User user) {
        Todo todo = findTodo(todoId);

        Comment comment = new Comment(commentRequestDto, user, todo);

        commentRepository.save(comment);
    }

    public List<CommentResponseDto> getComments(Long todoId) {
        findTodo(todoId);
        List<Comment> comments = commentRepository.findAllByTodoId(todoId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    public void updateComment(Long todoId, Long commentId, CommentRequestDto requestDto,
            User user) {
        findTodo(todoId);

        Comment comment = findComment(commentId);

        comment.update(requestDto);
    }

    public void deleteComment(Long todoId, Long commentId, User user) {

        findTodo(todoId);

        Comment comment = findComment(commentId);

        validate(comment.getUser().getId(), user.getId());

        commentRepository.delete(comment);
    }

    private Todo findTodo(Long todoId) {
        return todoService.findTodo(todoId);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new InvalidInputException("해당하는 댓글이 없습니다.")
        );
    }

    private void validate(Long originId, Long inputId) {
        if (!Objects.equals(originId, inputId)) {
            throw new AuthenticationException("해당 댓글의 작성자가 아닙니다.");
        }
    }
}
