package com.privilledge.pma.service;

import com.privilledge.pma.controller.ProjectController;
import com.privilledge.pma.model.Task;
import com.privilledge.pma.model.User;
import com.privilledge.pma.repository.TaskRepository;
import com.privilledge.pma.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public class TaskService {



    public TaskService(TaskRepository taskRepository, UserRepo userRepo, ProjectController projectController) {
        this.taskRepository = taskRepository;
        this.userRepo = userRepo;
        this.projectController = projectController;
    }

    private TaskRepository taskRepository;
    private UserRepo userRepo;
    private ProjectController projectController;

    public Long getIdFromToken(String token){
        return projectController.getUserIdFromToken(token);
    }

    public void addTask(@RequestBody Task task, @RequestHeader("Authorization") String token){
        Long user_id=getIdFromToken(token);
        User user=userRepo.findById(user_id).orElseThrow(()->new RuntimeException("Not found"));
        task.setUser(user);
        taskRepository.save(task);

    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getTaskByUser(User user){
        return taskRepository.findByUser(user);
    }
}
