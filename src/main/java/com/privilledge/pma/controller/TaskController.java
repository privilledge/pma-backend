package com.privilledge.pma.controller;

import com.privilledge.pma.model.Task;
import com.privilledge.pma.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private TaskService taskService;

    @PostMapping("/addTask")
    public String addTask(@RequestBody Task task){
        taskService.addTask(task);
        return "Task added";
    }

    @GetMapping("/getTasks")
    public List<Task> getAllTasks(){
        return taskService.getTasks();
    }
}
