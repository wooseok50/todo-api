package com.sparta.todo.todo.service;

import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.entity.Comment;
import com.sparta.todo.global.exception.InvalidTodoException;
import com.sparta.todo.global.exception.InvalidUserException;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public TodoResponseDto postTodo(TodoRequestDto requestDto, User user) throws Exception {

        Todo todo = new Todo(requestDto, user);
        Todo saveTodo = todoRepository.save(todo);

        return new TodoResponseDto(saveTodo);
    }

    public List<TodoResponseDto> getTodoList() {
        return todoRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(todo -> new TodoResponseDto(todo,
                        mapToCommentResponseDtoList(todo.getComments())))
                .collect(Collectors.toList());
    }

    public TodoResponseDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new InvalidUserException("해당 게시물이 존재하지 않습니다.")
        );

        List<CommentResponseDto> commentList = mapToCommentResponseDtoList(todo.getComments());

        return new TodoResponseDto(todo, commentList);
    }

    @Transactional
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, User user) {

        Todo todo = findTodoByIdAndUser(id, user);

        todo.update(requestDto);

        List<CommentResponseDto> commentList = mapToCommentResponseDtoList(todo.getComments());

        return new TodoResponseDto(todo, commentList);
    }

    @Transactional
    public void deleteTodo(Long id, User user) {

        Todo todo = findTodoByIdAndUser(id, user);

        todoRepository.delete(todo);
    }

    @Transactional
    public TodoResponseDto completeTodo(Long id, User user) {

        Todo todo = findTodoByIdAndUser(id, user);

        todo.setCompleted(true);

        todoRepository.save(todo);

        return new TodoResponseDto(todo);
    }

    private Todo findTodoByIdAndUser(Long id, User user) {
        return todoRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new InvalidUserException("해당 사용자의 게시글이 아닙니다."));
    }

    public Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(
                () -> {
                    String message = "해당 게시글이 없습니다.";
                    return new InvalidTodoException(message);
                }
        );
    }

    // Comment 객체 리스트를 CommentResponseDto 객체 리스트로 변환
    private List<CommentResponseDto> mapToCommentResponseDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
