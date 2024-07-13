package com.privilledge.pma.service;

import com.privilledge.pma.model.User;
import com.privilledge.pma.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public String login(String email,String password){
        User user=userRepo.findUserByEmail(email);
        if(user!=null&&password.equals(user.getPassword())){
            return "user logged in";
        }
        else return "Invalid login";
    }
}
