package com.sparta.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.entity.Comment;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.todo.service.TodoService;
import com.sparta.todo.user.entity.User;
import java.util.ArrayList;
import java.util.List;
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
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;

    private User user;
    private Todo testTodo;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        user = new User("blue", "blue1234");
        testTodo = new Todo(new TodoRequestDto("blue", "Todo Title", "Todo Content"), user);
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        testComment = new Comment(commentRequestDto, user, testTodo);
        testTodo.getComments().add(testComment);
    }

    @Test
    @DisplayName("todo 생성 - 성공")
    void postTodo_Success() {

        // Given
        given(todoRepository.save(any(Todo.class))).willReturn(testTodo);
        TodoRequestDto todoRequestDto = new TodoRequestDto("blue", "Todo Title", "Todo Content");

        // When
        TodoResponseDto responseDto = todoService.postTodo(todoRequestDto, user);

        // Then
        assertThat(responseDto.getTitle()).isEqualTo(todoRequestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(todoRequestDto.getContent());
    }

    @Test
    @DisplayName("Todo getTest - 성공")
    void getTodo_Success() {
        // Given
        Long todoId = 1L;
        given(todoRepository.findById(todoId)).willReturn(Optional.of(testTodo));

        // When
        TodoResponseDto result = todoService.getTodo(todoId);

        // Then
        assertThat(result.getTitle()).isEqualTo(testTodo.getTitle());
        assertThat(result.getContent()).isEqualTo(testTodo.getContent());

        for (int i = 0; i < result.getCommentList().size(); i++) {
            assertEquals(result.getCommentList().get(i).getComment(),
                    testTodo.getComments().get(i).getComment());
        }
    }

    @Test
    public void testGetTodoList() {
        // Given
        List<Todo> todos = new ArrayList<>();
        todos.add(testTodo);

        given(todoRepository.findAllByOrderByCreatedAtDesc()).willReturn(todos);

        // When
        List<TodoResponseDto> todoResponseDtoList = todoService.getTodoList();

        // Then
        for (int i = 0; i < todoResponseDtoList.size(); i++) {
            TodoResponseDto todoResponseDto = todoResponseDtoList.get(i);
            Todo todo = todos.get(i);

            assertEquals(todoResponseDto.getTitle(), todo.getTitle());
            assertEquals(todoResponseDto.getContent(), todo.getContent());

            for (int j = 0; j < todoResponseDto.getCommentList().size(); j++) {
                CommentResponseDto commentResponseDto1 = todoResponseDto.getCommentList().get(j);
                Comment comment = todo.getComments().get(j);

                assertEquals(commentResponseDto1.getUsername(), comment.getUsername());
                assertEquals(commentResponseDto1.getComment(), comment.getComment());
            }
        }
    }

    @Test
    @DisplayName("todo 업데이트 성공")
    void testUpdateTodo() {
        // Given
        Long todoId = 1L;
        given(todoRepository.findByIdAndUserId(todoId, user.getId())).willReturn(
                Optional.of(testTodo));

        // When
        TodoRequestDto requestDto = new TodoRequestDto(user.getUsername(), "updateTitle",
                "updateContent");

        TodoResponseDto actual = todoService.updateTodo(todoId, requestDto, user);

        // Then
        assertThat(actual.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(actual.getContent()).isEqualTo(requestDto.getContent());
    }

    @Test
    @DisplayName("todo 삭제 성공")
    void testDeleteTodo() {
        // Given
        Long todoId = 1L;
        given(todoRepository.findByIdAndUserId(todoId, user.getId())).willReturn(
                Optional.of(testTodo));

        // When
        todoService.deleteTodo(todoId, user);

        // Then
        then(todoRepository).should(times(1))
                .delete(ArgumentMatchers.any(Todo.class));
    }

    @Test
    @DisplayName("completeTodo 삭제 성공")
    void testCompleteTodoTodo() {
        // Given
        Long todoId = 1L;
        given(todoRepository.findByIdAndUserId(todoId, user.getId())).willReturn(
                Optional.of(testTodo));
        // When
        todoService.completeTodo(todoId, user);

        // Then
        assertTrue(testTodo.isCompleted());
    }
}
