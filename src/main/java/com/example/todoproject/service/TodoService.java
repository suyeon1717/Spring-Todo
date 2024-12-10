package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoService {

    TodoResponseDto saveTodo(TodoRequestDto dto); // 일정 생성

    List<TodoResponseDto> findTodos(String date, String username); // 일정 조회

//    List<TodoResponseDto> findAllTodos(); // 파라미터 둘 다 충족 x 전체 목록 반환

    TodoResponseDto findTodoById(Long todoId); // 선택 일정 조회 (id로 조회)

    TodoResponseDto updateTodo(Long todoId, String contents, String userName, String password); // 선택 일정 수정 (내용, 작성자명만)

    void deleteTodo(Long todoId, String password); // 선택 일정 삭제

}
