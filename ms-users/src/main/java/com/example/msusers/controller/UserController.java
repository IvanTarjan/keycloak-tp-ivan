package com.example.msusers.controller;

import com.example.msusers.domain.User;
import com.example.msusers.domain.dto.UserRegistrationDto;
import com.example.msusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/new/provider")
    public ResponseEntity<User> saveUserProvider(@RequestBody UserRegistrationDto user){
        return ResponseEntity.ok(userService.saveUserProvider(user));
    }
}
