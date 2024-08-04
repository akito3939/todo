package com.example.todos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Boolean completed;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }
}
