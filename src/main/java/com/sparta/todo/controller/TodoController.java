package com.sparta.todo.controller;

import com.sparta.todo.dto.ResponseStatusDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.response.Status;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // Todo 작성
    @PostMapping("/todo")
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(todoService.postTodo(requestDto, userDetails.getUser()));
    }

    // todo 전체 조회
    @GetMapping("/todo")
    public ResponseEntity<List<TodoResponseDto>> getTodoList() {
        return ResponseEntity.ok(todoService.getTodoList());
    }

    // todo 선택 조회
    @GetMapping("/todo/{id}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodo(id));
    }

    // todo 수정
    @PutMapping("/todo/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(todoService.updateTodo(id, requestDto, userDetails.getUser()));
    }

    // todo 삭제
    @DeleteMapping("/todo/{id}")
    public ResponseStatusDto deleteTodo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(id, userDetails.getUser());
        return new ResponseStatusDto(Status.POST_DELETE);
    }
}
