package com.example.todoproject.controller;


import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.service.TodoService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 일정 전체 조회 API (수정일, 작성자명 중 한 가지 이상을 충족하거나 둘 다 충족하지 않을 수도 있음)
     *
     */

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findTodos(
            @RequestParam(required = false) LocalDateTime lastModifiedDate,
            @RequestParam(required = false) String userName
            ){

        List<TodoResponseDto> dto;

        if(lastModifiedDate == null && userName == null) {
            dto = todoService.findAllTodos();
        }
        else{
            dto = todoService.findTodos(lastModifiedDate, userName);
        }
        // 조회 요청이 들어오면 todoService에 넘겨줌
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * 선택 일정 조회 API (id로 조회)
     *
     */
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long todoId) {
        return new ResponseEntity<>(todoService.findTodoById(todoId), HttpStatus.OK);
    }

    /**
     * 선택 일정 수정 API (id로 조회하여 비밀번호가 일치할 경우 일정 내용, 작성자명만 수정 가능)
     *
     */
    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto dto
    ) {
        return new ResponseEntity<>(todoService.updateTodo(todoId, dto.getContents(), dto.getUserName(), dto.getPassword()), HttpStatus.OK);
    }

    /**
     * 선택 일정 삭제 API (id로 조회하여 비밀번호가 일치할 경우 삭제)
     *
     */

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto dto
    ) {
        todoService.deleteTodo(todoId, dto.getPassword());
        // 성공한 경우
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
