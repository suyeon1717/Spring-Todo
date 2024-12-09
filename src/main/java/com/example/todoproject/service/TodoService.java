package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoService {

    TodoResponseDto saveTodo(TodoRequestDto dto); // 일정 생성

    List<TodoResponseDto> findTodos(LocalDateTime lastModifiedDate, String userName); // 일정 조회

    List<TodoResponseDto> findAllTodos(); // 파라미터 둘 다 충족 x 전체 목록 반환

}
