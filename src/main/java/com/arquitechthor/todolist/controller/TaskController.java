package com.arquitechthor.todolist.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.arquitechthor.todolist.model.Task;
import com.arquitechthor.todolist.model.TaskRequest;
import com.arquitechthor.todolist.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TaskController {

	 private final TaskService taskService;

	@GetMapping(value = "/tasks")
	public List<Task> getAll() {
		return taskService.getTasks();
	}
	
	@GetMapping(value = "/task/get/{id}")
	public Task find(@PathVariable Long id) {
		Optional<Task> task = taskService.findTaskById(id);
		if (task.isEmpty()) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Task not found"
					);
		}
		return task.get();
	}

	@PostMapping(value = "/task")
	public ResponseEntity<Task> save(@RequestBody TaskRequest task) {
		Task newTask = new Task();
		newTask.setCompleted(task.isCompleted());
		newTask.setDescription(task.getDescription());
		
		Task savedTask = taskService.saveTask(newTask);
		return new ResponseEntity<Task>(savedTask, HttpStatus.OK);
	}
	
	@GetMapping(value = "/task/complete/{id}")
	public ResponseEntity<Task> complete(@PathVariable Long id) {
		Optional<Task> task = taskService.findTaskById(id);
		if (task.isEmpty()) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Task not found"
					);
		}
		
		Task newTask = task.get();
		newTask.setCompleted(true);
		
		Task savedTask = taskService.saveTask(newTask);
		return new ResponseEntity<Task>(savedTask, HttpStatus.OK);
	}

	@DeleteMapping(value = "/task/{id}")
	public ResponseEntity<Task> delete(@PathVariable Long id) {
		Optional<Task> task = taskService.findTaskById(id);
		if (task.isPresent()) {
			taskService.deleteTaskById(id);
		} else {
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Task>(task.get(), HttpStatus.OK);
	}

}
