package com.sparta.todo.repository;

import com.sparta.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByOrderByCreatedAtDesc();
    Optional<Todo> findByIdAndUserId(Long id, Long userId);
}
