package com.example.todoproject.repository;

import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository {

    TodoResponseDto saveTodo(Todo todo); //todo는 id가 없는 상태로 Repository에 전달됨

    List<TodoResponseDto> findTodos(String lastModifiedDate, String userName);

    List<TodoResponseDto> findAllTodos();

    Todo findTodoByIdOrElseThrow(Long todoId);

    Boolean pwCheck(Long todoId, String password);

    int updateContents(Long todoId, String contents, LocalDateTime modifiedDateTime);

    int updateUserName(Long todoId, String userName, LocalDateTime modifiedDateTime);

    int deleteTodo(Long todoId);
}
