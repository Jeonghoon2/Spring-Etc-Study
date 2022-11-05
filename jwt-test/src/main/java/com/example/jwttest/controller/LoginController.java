package com.example.jwttest.controller;

import com.example.jwttest.dto.User;
import com.example.jwttest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.status(HttpStatus.OK).body("hello");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        ResponseEntity<String> r = userService.register(user);
        return ResponseEntity.status(r.getStatusCode()).body(r.getBody());
    }
}
