package com.example.todoproject.repository;

import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository {

    TodoResponseDto saveTodo(Todo todo); //todo는 id가 없는 상태로 Repository에 전달됨

    List<TodoResponseDto> findTodos(LocalDateTime lastModifiedDate, String userName);
    List<TodoResponseDto> findAllTodos();
}
