package com.privilledge.pma.service;

import com.privilledge.pma.model.Task;
import com.privilledge.pma.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TaskService {
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private TaskRepository taskRepository;

    public void addTask(@RequestBody Task task){
        taskRepository.save(task);

    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }
}
