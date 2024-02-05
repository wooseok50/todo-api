package com.sparta.todo.dto;

import com.sparta.todo.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TodoResponseDto {
    private Long id;
    private String title;
    private String content;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.createdAt = todo.getCreatedAt();
        this.isCompleted = todo.isCompleted();
    }

    // 댓글 출력 기능 추가
    public TodoResponseDto(Todo todo, List<CommentResponseDto> commentList) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.createdAt = todo.getCreatedAt();
        this.commentList = commentList;
        this.isCompleted = todo.isCompleted();

    }
}
