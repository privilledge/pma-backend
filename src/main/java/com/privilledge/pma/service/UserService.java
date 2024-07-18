package com.privilledge.pma.service;

import com.privilledge.pma.model.User;
import com.privilledge.pma.repository.UserRepo;
import com.privilledge.pma.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User saveUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return  userRepo.save(user);
    }

    public String login(String email,String password){
        User user=userRepo.findUserByEmail(email);
        if(user!=null&&password.equals(user.getPassword())){
            return "User logged in";
        }
        else return "Invalid login";


    }
//    public String authenticateUser(String email, String password) {
//        User user = userRepo.findUserByEmail(email);
//        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
//
//            return jwtUtil.generateToken(user.getEmail());
//        }
//        return null; // Return null if authentication fails
//    }

    public Map<String, String> authenticateUser(String email, String password) {
        User user = userRepo.findUserByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail(),user.getId());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            return response;
        }
        return null; // Return null if authentication fails
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
