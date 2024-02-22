package com.sparta.todo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private Todo todo;
    private User user;

    @Test
    @DisplayName("findByIdAndUserId 테스트")
    void findByIdAndUserId() {

        // Given
        Long userId = 1L;
        Long todoId = 1L;
        user = new User("blue", "blue1234");
        todo = new Todo(new TodoRequestDto("blue", "Todo Title", "Todo Content"), user);
        userRepository.save(user);
        todoRepository.save(todo);

        // When
        Optional<Todo> findTodo = todoRepository.findByIdAndUserId(todoId, userId);

        // Then
        assertEquals(todo.getTitle(), findTodo.get().getTitle());
        assertEquals(Optional.of(todo), findTodo);
    }
}
