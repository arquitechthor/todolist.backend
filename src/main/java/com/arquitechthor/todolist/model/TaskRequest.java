package com.arquitechthor.todolist.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TaskRequest {

	private Long id;
	
	@NotBlank
	private String description;
	
	@NotNull
	private boolean completed;
	
}
