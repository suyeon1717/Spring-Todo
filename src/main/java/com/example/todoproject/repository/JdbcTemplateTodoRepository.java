package com.example.todoproject.repository;

import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateTodoRepository implements TodoRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TodoResponseDto saveTodo(Todo todo) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("todoId");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("contents", todo.getContents());
        parameters.put("password", todo.getPassword());
        parameters.put("userName", todo.getUserName());
        parameters.put("createdDate", todo.getCreatedDate());
        parameters.put("lastModifiedDate", todo.getLastModifiedDate());

        // 저장 후 생성된 key값을 Number 타입으로 변환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(
                key.longValue(), todo.getContents(), todo.getUserName(),
                todo.getCreatedDate(), todo.getLastModifiedDate()); //@AllArgsConstructor

    }
}
