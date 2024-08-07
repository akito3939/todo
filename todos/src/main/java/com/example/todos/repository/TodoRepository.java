package com.example.todos.repository;

import com.example.todos.domain.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByUserId(Integer userId);

    Optional<Todo> findByIdAndUserId(Integer id, Integer userId);
}
