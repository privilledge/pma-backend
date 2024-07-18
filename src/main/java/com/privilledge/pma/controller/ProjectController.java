package com.privilledge.pma.controller;

import com.privilledge.pma.model.Project;
import com.privilledge.pma.model.User;
import com.privilledge.pma.repository.ProjectsRepo;
import com.privilledge.pma.repository.UserRepo;
import com.privilledge.pma.service.ProjectsService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final String secretKey = "privilledge_mashegede_project_management_app_2024_secret_key.privilledge_mashegede_project_management_app_2024_secret_key.privilledge_mashegede_project_management_app_2024_secret_key.";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
//    private final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public Long getUserIdFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        return claims.get("user_id", Long.class);
//        return Long.parseLong(claims.getId()); // Assuming the user ID is stored as the subject
    }

    public ProjectController(ProjectsService projectsService, ProjectsRepo projectsRepo, UserRepo userRepo) {
        this.projectsService = projectsService;
        this.projectsRepo = projectsRepo;
        this.userRepo = userRepo;
    }

    private ProjectsService projectsService;
    private ProjectsRepo projectsRepo;
    private UserRepo userRepo;

    @PostMapping("/addProject")
    public String saveProject(@RequestBody Project project,@RequestHeader ("Authorization") String token){
        Long user_id=getUserIdFromToken(token);
        User user = userRepo.findById(user_id).orElseThrow(() -> new RuntimeException("User not found"));
        project.setUser(user); // Set the user for the project if needed
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id){
        Optional<Project> findProject=projectsRepo.findById(id);

    if (findProject.isPresent()){
        projectsRepo.delete(findProject.get());
        return ResponseEntity.ok().build();
    }
    else return ResponseEntity.notFound().build();
    }


    @GetMapping("/getProjectByUser")
    public List<Project> getProjectsByUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepo.findUserByEmail(username); // Assuming you have a method to find User by username
        if (user == null) {
            throw new RuntimeException("User not found for username: " + username);
        }
        return projectsService.getByUser(user);
    }


}
