package com.vageesh.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("name")
public class TodoController {

	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}

	private TodoService todoService;

	@GetMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUserName(model);
		List<Todo> todos = todoService.findByUsername(username);
		model.addAttribute("todos", todos);
		return "listTodos";
	}

	@GetMapping("add-todos")
	public String showNewTodoPage(ModelMap model) {
		String username = getLoggedInUserName(model);
		Todo todo = new Todo(0, username, "Nothing", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}

	@PostMapping("add-todos")
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		String username = getLoggedInUserName(model);
		todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), false);
		return "redirect:list-todos";
	}
	
	@GetMapping("delete-todo")
	public String deleteTodos(@RequestParam int id) {
		//delete todo using id
		todoService.deleteById(id);
		return "redirect:list-todos";
	}
	
	@GetMapping("update-todo")
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo= todoService.findById(id);
		model.addAttribute("todo",todo);
		return "todo";
	}
	
	@PostMapping("update-todo")
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		String username = getLoggedInUserName(model);
		todo.setUsername(username);
		todoService.updateTodo(todo);
		return "redirect:list-todos";
	}
	
	private String getLoggedInUserName(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName(); 
	}
	
}