package com.example.todoproject.controller;


import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.service.TodoService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController// @Controller + @ResponseBody
@RequestMapping("/todos")
public class TodoController {

    // Service Layer 접근 , 주입된 의존성을 변경할 수 없어 객체의 상태를 안전하게 유지할 수 있다.
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    /**
     * 일정 생성 API
     * @param : {@link TodoRequestDto} 일정 생성 요청 객체
     * @return : {@link ResponseEntity <TodoResponseDto>} JSON 응답
     */

    @PostMapping //요청
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto){
        // ServiceLayer 호출 및 응답
        return new ResponseEntity<>(todoService.saveTodo(dto), HttpStatus.CREATED);
    }
}
