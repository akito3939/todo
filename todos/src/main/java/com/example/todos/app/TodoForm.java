package com.example.todos.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoForm {
    @NotBlank
    @Size(max=100)
    private String title;

    private String description;

    private LocalDateTime dueDate;
}
