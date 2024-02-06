package com.sparta.todo.dto;

import com.sparta.todo.response.Status;
import lombok.Getter;

@Getter
public class ResponseStatusDto {
    private String code;
    private int statusCode;

    public ResponseStatusDto(Status status){
        this.code = status.getCode();
        this.statusCode = status.getStatusCode();
    }
}