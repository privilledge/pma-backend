package com.privilledge.pma.controller;

import com.privilledge.pma.model.Project;
import com.privilledge.pma.repository.ProjectsRepo;
import com.privilledge.pma.service.ProjectsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    public ProjectController(ProjectsService projectsService, ProjectsRepo projectsRepo) {
        this.projectsService = projectsService;
        this.projectsRepo = projectsRepo;
    }

    private ProjectsService projectsService;
    private ProjectsRepo projectsRepo;

    @PostMapping("/addProject")
    public String saveProject(@RequestBody Project project){
        projectsService.addProject(project);
        return "Project added";
    }

    @GetMapping("/getProjects")
    public List<Project> fetchProjects(){
        return projectsService.getProjects();
    }

    @GetMapping("/getProjectById/{id}")
    public Optional<Project> getProjectById(@PathVariable Long id ){
      return projectsService.getProjectById(id);
    }

    @PutMapping("/editProject/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody Project project,@PathVariable Long id){
        Optional<Project> findProject=projectsRepo.findById(id);
        if (findProject.isPresent()){
            Project newProject=findProject.get();
            newProject.setProjectName(project.getProjectName());
            newProject.setAddedDate(project.getAddedDate());
            newProject.setDescription(project.getDescription());
            newProject.setStatus(project.getStatus());
            newProject.setSummary(project.getSummary());
            newProject.setDueDate(project.getDueDate());
            newProject.setNotes(project.getNotes());
            newProject.setProgress(project.getProgress());


            Project updatedProject=projectsRepo.save(newProject);
            return ResponseEntity.ok(updatedProject);
        }
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id){
        Optional<Project> findProject=projectsRepo.findById(id);

    if (findProject.isPresent()){
        projectsRepo.delete(findProject.get());
        return ResponseEntity.ok().build();
    }
    else return ResponseEntity.notFound().build();
    }
}
