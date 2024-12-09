package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;
import com.example.todoproject.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

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

    @Override
    public List<TodoResponseDto> findTodos(LocalDateTime lastModifiedDate, String userName) {

        return todoRepository.findTodos(lastModifiedDate, userName);
    }

    @Override
    public List<TodoResponseDto> findAllTodos() {

        // 전체 조회 -> memoRepository 호출
        List<TodoResponseDto> allTodos = todoRepository.findAllTodos();

        return allTodos;
    }

    @Override
    public TodoResponseDto findTodoById(Long todoId) {
        Todo todo = todoRepository.findTodoByIdOrElseThrow(todoId);
        return new TodoResponseDto(todo);
    }

    @Override
    public TodoResponseDto updateTodo(Long todoId, String contents, String userName, String password) {

        int updatedRow = 0;
        LocalDateTime modifiedDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = modifiedDateTime.format(formatter);
        modifiedDateTime = LocalDateTime.parse(formatted, formatter);

        System.out.println("1");
        // 비밀번호 True
        if(todoRepository.pwCheck(todoId, password)){

            // 필수값 검증
            if(contents == null && userName == null) { //일정 내용, 작성자명 수정 값 없을 때
                System.out.println("2");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The content or userName are required values.");
            }
            else if(contents != null && userName == null) { // 일정 내용만 수정
                System.out.println("3");
                updatedRow += todoRepository.updateContents(todoId, contents, modifiedDateTime);
            }
            else if(contents == null && userName != null) { // 작성자명만 수정
                System.out.println("4");
                updatedRow += todoRepository.updateUserName(todoId, userName, modifiedDateTime);
            }
            else{ //일정 내용, 작성자명 수정
                System.out.println("5");
                updatedRow += todoRepository.updateContents(todoId, contents, modifiedDateTime);
                updatedRow += todoRepository.updateUserName(todoId, userName, modifiedDateTime);
            }

            // 수정된 row가 0개라면 -> id 값으로 조회된 memo가 없을 때
            if(updatedRow == 0) {
                System.out.println("6");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
            }

            // 수정된 일정 조회
            Todo todo = todoRepository.findTodoByIdOrElseThrow(todoId);
            return new TodoResponseDto(todo);

        }

        // 비밀번호 False
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");

    }


}
