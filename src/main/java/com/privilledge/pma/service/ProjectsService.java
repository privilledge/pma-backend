package com.privilledge.pma.service;

import com.privilledge.pma.model.Project;
import com.privilledge.pma.model.User;
import com.privilledge.pma.repository.ProjectsRepo;
import com.privilledge.pma.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class ProjectsService {

    public ProjectsService(ProjectsRepo projectsRepo, UserRepo userRepo) {
        this.projectsRepo = projectsRepo;
        this.userRepo = userRepo;
    }

    @Autowired
    private ProjectsRepo projectsRepo;
    @Autowired
    private UserRepo userRepo;


    public String addProject(@RequestBody Project project){
//        User user1=userRepo.findById(user_id).orElseThrow(()->new RuntimeException("Not found"));
//        project.setUser(user1);
    projectsRepo.save(project);
    return "Project saved";
    }
    public List<Project> getProjects(){
        return projectsRepo.findAll();
    }

    public Optional<Project> getProjectById(@PathVariable Long id){
        return projectsRepo.findById(id);
    }

    public List<Project> getByUser( User user){
        return projectsRepo.findByUser(user);
    }


}
