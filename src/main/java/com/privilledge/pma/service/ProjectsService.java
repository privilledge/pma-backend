package com.privilledge.pma.service;

import com.privilledge.pma.model.Project;
import com.privilledge.pma.repository.ProjectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class ProjectsService {

    public ProjectsService(ProjectsRepo projectsRepo) {
        this.projectsRepo = projectsRepo;
    }

    @Autowired
    private ProjectsRepo projectsRepo;


    public String addProject(@RequestBody Project project){
    projectsRepo.save(project);
    return "Project saved";
    }
    public List<Project> getProjects(){
        return projectsRepo.findAll();
    }

    public Optional<Project> getProjectById(@PathVariable Long id){
        return projectsRepo.findById(id);
    }

    public List<Project> getByUserId( Long id){
        return projectsRepo.findByUserId(id);
    }

}
