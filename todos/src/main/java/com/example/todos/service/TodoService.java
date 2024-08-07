package com.example.todos.service;

import com.example.todos.domain.model.Todo;
import com.example.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public List<Todo> getTodosByUserId(Integer userId) {
        return todoRepository.findByUserId(userId);
    }

    public void save(Todo todos) {
        todoRepository.save(todos);
    }


    public Optional<Todo> findByIdAndUserId(Integer id, Integer userId) {
        return todoRepository.findByIdAndUserId(id, userId);
    }
}
