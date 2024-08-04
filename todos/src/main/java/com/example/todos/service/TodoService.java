package com.example.todos.service;

import com.example.todos.domain.model.Todo;
import com.example.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public List<Todo> getTodosByUserId(Integer userId) {
        return todoRepository.findByUserId(userId);
    }

    public void save(Todo todos) {
        todoRepository.save(todos);
    }
}
