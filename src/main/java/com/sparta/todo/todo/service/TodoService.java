package com.sparta.todo.todo.service;

import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.entity.Comment;
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

    // Todo 작성
    @Transactional
    public TodoResponseDto postTodo(TodoRequestDto requestDto, User user) {

        Todo todo = new Todo(requestDto, user);
        Todo saveTodo = todoRepository.save(todo);

        return new TodoResponseDto(saveTodo);
    }

    // Todo 전제 조회
    public List<TodoResponseDto> getTodoList() {
        return todoRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(todo -> new TodoResponseDto(todo,
                        mapToCommentResponseDtoList(todo.getComments())))
                .collect(Collectors.toList());
    }

    // Todo 선택 조회
    public TodoResponseDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );

        List<CommentResponseDto> commentList = mapToCommentResponseDtoList(todo.getComments());

        return new TodoResponseDto(todo, commentList);
    }

    // Todo 수정
    @Transactional
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, User user) {

        Todo todo = todoRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자의 게시글을 찾을 수 없습니다.")
        );

        todo.update(requestDto);

        List<CommentResponseDto> commentList = mapToCommentResponseDtoList(todo.getComments());

        return new TodoResponseDto(todo, commentList);
    }

    // Todo 삭제
    @Transactional
    public void deleteTodo(Long id, User user) {

        Todo todo = todoRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자의 게시글을 찾을 수 없습니다.")
        );

        todoRepository.delete(todo);
    }

    // Comment 객체 리스트를 CommentResponseDto 객체 리스트로 변환
    private List<CommentResponseDto> mapToCommentResponseDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoResponseDto completeTodo(Long id, User user) {

        Todo todo = todoRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자의 게시글을 찾을 수 없습니다.")
        );

        todo.setCompleted(true);

        todoRepository.save(todo);

        return new TodoResponseDto(todo);
    }
}
