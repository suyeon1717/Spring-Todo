package com.example.todoproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class Todo {
    private Long todoId;
    private String contents;
    private String userName;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 생성자
    public Todo(String contents, String userName, String password) {
        this.contents = contents;
        this.userName = userName;
        this.password = password;

        // 작성일, 수정일 yyyy-MM-dd HH:mm:ss 형식으로 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = createdDate.now().format(formatter);
        this.createdDate = LocalDateTime.parse(formatted, formatter);
        this.lastModifiedDate = this.createdDate;
    }

}
