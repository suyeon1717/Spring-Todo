package com.example.todoproject.repository;

import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;

public interface TodoRepository {

    TodoResponseDto saveTodo(Todo todo); //todo는 id가 없는 상태로 Repository에 전달됨
}
