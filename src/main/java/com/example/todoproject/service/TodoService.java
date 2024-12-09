package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;

public interface TodoService {

    TodoResponseDto saveTodo(TodoRequestDto dto); // 일정 생성
}
