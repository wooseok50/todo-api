package com.sparta.todo.todo.repository;

import com.sparta.todo.todo.entity.Todo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByOrderByCreatedAtDesc();

    Optional<Todo> findByIdAndUserId(Long id, Long userId);

    List<Todo> findByUserId(Long id);
}
