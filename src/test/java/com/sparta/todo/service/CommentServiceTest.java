package com.sparta.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.entity.Comment;
import com.sparta.todo.comment.repository.CommentRepository;
import com.sparta.todo.comment.sevice.CommentService;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.service.TodoService;
import com.sparta.todo.user.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private TodoService todoService;

    @Mock
    private CommentRepository commentRepository;

    private User user;
    private Todo testTodo;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        user = new User("blue", "blue1234");
        testTodo = new Todo(new TodoRequestDto("blue", "Todo Title", "Todo Content"), user);
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        testComment = new Comment(commentRequestDto, user, testTodo);
    }

    @Test
    @DisplayName("Comment 생성 - 성공")
    void createComment_Success() {
        // Given
        Long todoId = 1L;
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");

        // When
        commentService.todoComment(todoId, commentRequestDto, user);

        // Then
        assertThat(commentRequestDto.getComment()).isEqualTo("댓글");
    }

    @Test
    @DisplayName("Comment 업데이트 - 성공")
    void updateComment() {

        // Given
        Long todoId = 1L;
        Long commentId = 1L;
        given(commentRepository.findById(commentId)).willReturn(Optional.of(testComment));
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글 수정");

        // When
        commentService.updateComment(todoId, commentId, commentRequestDto, user);

        // Then
        assertThat(commentRequestDto.getComment()).isEqualTo(testComment.getComment());
    }

    @Test
    @DisplayName("Comment 삭제 - 성공")
    void deleteComment() {
        // Given
        Long todoId = 1L;
        Long commentId = 1L;
        given(commentRepository.findById(commentId)).willReturn(Optional.of(testComment));

        // When
        commentService.deleteComment(todoId, commentId, user);

        // Then
        verify(commentRepository, times(1))
                .delete(ArgumentMatchers.any(Comment.class));
    }
}
