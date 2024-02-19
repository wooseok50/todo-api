package com.sparta.todo.todo.controller;

import com.sparta.todo.exception.ResponseStatusDto;
import com.sparta.todo.exception.Status;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.service.TodoService;
import com.sparta.todo.util.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // Todo 작성
    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(todoService.postTodo(requestDto, userDetails.getUser()));
    }

    // todo 전체 조회
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getTodoList() {
        return ResponseEntity.ok(todoService.getTodoList());
    }

    // todo 선택 조회
    @GetMapping("{id}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long id) {
        return ResponseEntity.ok((todoService.getTodo(id)));
    }

    // todo 수정
    @PutMapping("{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
            @RequestBody TodoRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(todoService.updateTodo(id, requestDto, userDetails.getUser()));
    }

    // todo 삭제
    @DeleteMapping("{id}")
    public ResponseStatusDto deleteTodo(@PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(id, userDetails.getUser());
        return new ResponseStatusDto(Status.POST_DELETE);
    }

    // todo 완료 기능 API
    @PutMapping("/{id}/complete")
    public ResponseEntity<TodoResponseDto> completeTodo(@PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(todoService.completeTodo(id, userDetails.getUser()));
    }
}
