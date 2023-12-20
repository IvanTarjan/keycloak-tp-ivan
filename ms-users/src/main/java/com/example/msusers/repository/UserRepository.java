package com.example.msusers.repository;

import com.example.msusers.domain.User;
import com.example.msusers.domain.dto.UserRegistrationDto;

import java.util.List;

public interface UserRepository {

    public User findById(String id);

    public List<User> findAll();

    public User saveUserProvider(UserRegistrationDto user);

}
