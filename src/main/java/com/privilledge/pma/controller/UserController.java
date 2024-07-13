package com.privilledge.pma.controller;

import com.privilledge.pma.model.User;
import com.privilledge.pma.repository.UserRepo;

import com.privilledge.pma.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepo userRepo;
    private UserService userService;
    public UserController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

@PostMapping("/addUser")
public String addUser(@RequestBody User user){
        userRepo.save(user);
        return "User Added";
}

@GetMapping("/userById/{id}")
public Optional<User> getUserById(@PathVariable Long id){
        return userRepo.findById(id);
}
@GetMapping("/allUsers")
public List<User> getUsers(){
       return userRepo.findAll();
}

@PostMapping(value="/login",consumes = "application/x-www-form-urlencoded")
    public String userLogin(@RequestParam String email,@RequestParam String password){
    return userService.login(email,password);
}



}