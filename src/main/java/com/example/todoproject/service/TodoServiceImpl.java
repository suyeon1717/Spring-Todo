package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;
import com.example.todoproject.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    // Repository Layer 접근
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponseDto saveTodo(TodoRequestDto dto) {

        // 요청받은 데이터로 id가 없는 Todo 객체 생성
        Todo todo = new Todo(dto.getContents(), dto.getUserName(), dto.getPassword());

        // DB 저장 (Repository)
        return todoRepository.saveTodo(todo);
    }
}
