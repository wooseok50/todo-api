package com.sparta.todo.todo.controller;

import com.sparta.todo.global.response.CommonResponse;
import com.sparta.todo.global.util.UserDetailsImpl;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> postTodo(
            @RequestBody TodoRequestDto todoRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        todoService.postTodo(todoRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
                CommonResponse.<Void>builder().message("todo 생성 완료").build()
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TodoResponseDto>>> getTodoList() {
        List<TodoResponseDto> todoResponseDto = todoService.getTodoList();
        return ResponseEntity.ok()
                .body(CommonResponse.<List<TodoResponseDto>>builder()
                        .data(todoResponseDto).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<CommonResponse<TodoResponseDto>> getTodo(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(CommonResponse.<TodoResponseDto>builder()
                        .data(todoService.getTodo(id)).build());
    }

    @PutMapping("{id}")
    public ResponseEntity<CommonResponse<TodoResponseDto>> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoRequestDto todoRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
                .body(CommonResponse.<TodoResponseDto>builder()
                        .data(todoService.updateTodo(id, todoRequestDto, userDetails.getUser()))
                        .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                CommonResponse.<Void>builder().message("todo 삭제 완료").build());
    }


    @PutMapping("/{id}/complete")
    public ResponseEntity<CommonResponse<TodoResponseDto>> completeTodo(@PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
                .body(CommonResponse.<TodoResponseDto>builder()
                        .data(todoService.completeTodo(id, userDetails.getUser()))
                        .build());
    }
}
