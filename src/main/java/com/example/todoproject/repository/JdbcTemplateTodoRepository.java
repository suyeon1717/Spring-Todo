package com.example.todoproject.repository;

import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        parameters.put("userName", todo.getUserName());
        parameters.put("password", todo.getPassword());
        parameters.put("createdDate", todo.getCreatedDate());
        parameters.put("lastModifiedDate", todo.getLastModifiedDate());

        // 저장 후 생성된 key값을 Number 타입으로 변환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(
                key.longValue(), todo.getContents(), todo.getUserName(),
                todo.getCreatedDate(), todo.getLastModifiedDate()); //@AllArgsConstructor

    }

    @Override
    public List<TodoResponseDto> findTodos(LocalDateTime lastModifiedDate, String userName) {
        String sql = "SELECT * FROM todo WHERE 1=1";  // 기본 조건 (무조건 true)
        List<Object> params = new ArrayList<>();

        if (lastModifiedDate != null ) {
            sql += " AND lastModifiedDate = ?";
            params.add(lastModifiedDate);
        }

        if (userName != null) {
            sql += " AND userName = ?";
            params.add(userName);
        }
        return jdbcTemplate.query(sql, params.toArray(), todoRowMapper());
    }


    @Override
    public List<TodoResponseDto> findAllTodos() {
        return jdbcTemplate.query("select * from todo", todoRowMapper());
    }

    // todoRowMapper() : RowMapper<TodoResponseDto>
    private RowMapper<TodoResponseDto> todoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                Timestamp createdDateTimestamp = rs.getTimestamp("createdDate");
                LocalDateTime createdDate = createdDateTimestamp.toLocalDateTime();

                Timestamp modifiedDateTimestamp = rs.getTimestamp("lastModifiedDate");
                LocalDateTime modifiedDate = modifiedDateTimestamp.toLocalDateTime();

                return new TodoResponseDto(
                        rs.getLong("todoId"),
                        rs.getString("contents"),
                        rs.getString("userName"),
                        createdDate,
                        modifiedDate
                );
            }
        };
    }

    @Override
    public Todo findTodoByIdOrElseThrow(Long todoId) {

        List<Todo> result = jdbcTemplate.query("select * from todo where todoId = ?", todoRowMapperV2(), todoId);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + todoId));

    }

    // todoRowMapperV2() : RowMapper<Todo>
    private RowMapper<Todo> todoRowMapperV2() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                Timestamp createdDateTimestamp = rs.getTimestamp("createdDate");
                LocalDateTime createdDate = createdDateTimestamp.toLocalDateTime();

                Timestamp modifiedDateTimestamp = rs.getTimestamp("lastModifiedDate");
                LocalDateTime modifiedDate = modifiedDateTimestamp.toLocalDateTime();

                return new Todo(
                        rs.getLong("todoId"),
                        rs.getString("contents"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        createdDate,
                        modifiedDate
                );
            }
        };
    }

    @Override
    public Boolean pwCheck(Long todoId, String password) {
        String truePw = jdbcTemplate.queryForObject("select password from todo where todoId = ?", String.class, todoId);
        // 입력한 pw와 비교
        return truePw.equals(password);
    }

    @Override
    public int updateContents(Long todoId, String contents, LocalDateTime modifiedDateTime) {
        return jdbcTemplate.update("update todo set contents = ?, lastModifiedDate = ? where todoId = ?", contents, modifiedDateTime, todoId);
    }

    @Override
    public int updateUserName(Long todoId, String userName, LocalDateTime modifiedDateTime) {
        return jdbcTemplate.update("update todo set userName = ?, lastModifiedDate = ? where todoId = ?", userName, modifiedDateTime, todoId);
    }

    @Override
    public int deleteTodo(Long todoId) {
        return jdbcTemplate.update("delete from todo where todoId =?", todoId);
    }


}
