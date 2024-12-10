package com.example.todoproject.dto;

import com.example.todoproject.entity.Todo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long todoId;
    private String contents;
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    public TodoResponseDto(Todo todo){
        this.todoId = todo.getTodoId();
        this.contents = todo.getContents();
        this.userName = todo.getUserName();
//        this.password = todo.getPassword();
        this.createdDate = todo.getCreatedDate();
        this.lastModifiedDate = todo.getLastModifiedDate();
    }

}
