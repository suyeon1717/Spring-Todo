package com.example.todoproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoRequestDto {

    private String contents;
    private String userName;
    private String password;
}
