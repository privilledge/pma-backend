package com.privilledge.pma.controller;

import com.privilledge.pma.model.Task;
import com.privilledge.pma.model.User;
import com.privilledge.pma.repository.ProjectsRepo;
import com.privilledge.pma.repository.TaskRepository;
import com.privilledge.pma.repository.UserRepo;
import com.privilledge.pma.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    public TaskController(TaskService taskService, TaskRepository taskRepository, ProjectsRepo projectsRepo, UserRepo userRepo) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.projectsRepo = projectsRepo;
        this.userRepo = userRepo;
    }

    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectsRepo projectsRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody Task task,@RequestHeader("Authorization")String token) {
        if (task.getProject() == null || task.getProject().getId() == null) {
            return ResponseEntity.badRequest().body("Project is not specified");
        }
        taskService.addTask(task,token);
        return ResponseEntity.ok("Task added successfully");
    }


    @GetMapping("/getTasks")
    public List<Task> getAllTasks(){
        return taskService.getTasks();
    }

    @GetMapping("/taskById/{id}")
    public Optional<Task> getTaskById(@PathVariable Long id){
        return taskRepository.findById(id);
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id){
        Optional<Task> findTaskById=taskRepository.findById(id);
        if(findTaskById.isPresent()){
            taskRepository.delete(findTaskById.get());
            return  ResponseEntity.ok().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }

    }
@PutMapping("/editTask/{id}")
    public String editTask(@RequestBody Task task,@PathVariable Long id){
        Optional<Task> findTaskId=taskRepository.findById(id);
        if(findTaskId.isPresent()){
            Task newTask=findTaskId.get();
           newTask.setTaskName(task.getTaskName());
           newTask.setTaskType(task.getTaskType());
           newTask.setStatus(task.getStatus());
           newTask.setPriority(task.getPriority());
           newTask.setProject(task.getProject());
           taskRepository.save(task);

           return "Task updated";
        }
        else return( "Error");
    }

    @PutMapping("/updateTask/{id}")
    public String updateTaskStatus(@PathVariable Long id, @RequestBody Task task){
        Optional<Task> findTask=taskRepository.findById(id);
        if(findTask.isPresent()){
            Task editTask= findTask.get();
            editTask.setStatus(task.getStatus());
            editTask.setPriority(task.getPriority());
            taskRepository.save(task);
            return "Task status updated";
        }
        else return "Failed to update status";
    }

    @GetMapping("/getTasksByUser")
    public List<Task> getTasksByUser(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        String username=userDetails.getUsername();
        User user=userRepo.findUserByEmail(username);

        return taskService.getTaskByUser(user);

    }
}
