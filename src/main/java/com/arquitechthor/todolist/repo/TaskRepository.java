package com.arquitechthor.todolist.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arquitechthor.todolist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	Optional<Task> findById(Long id);

}
