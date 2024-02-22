package com.sparta.todo.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDto {

    private String username;
    private String title;
    private String content;

}
