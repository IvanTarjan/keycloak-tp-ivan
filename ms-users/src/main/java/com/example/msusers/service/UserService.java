package com.example.msusers.service;

import com.example.msusers.domain.Bill;
import com.example.msusers.domain.User;

import com.example.msusers.domain.dto.UserRegistrationDto;
import com.example.msusers.feignClients.BillsClient;
import com.example.msusers.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BillsClient billsClient;

    public UserService(UserRepository userRepository, BillsClient billsClient) {
        this.userRepository = userRepository;
        this.billsClient = billsClient;
    }

    public User getUserById(String id){
        User user = userRepository.findById(id);
        List<Bill> bills = billsClient.getByUserId(id);
        user.setBills(bills);
        return user;
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        users.forEach(user ->
                user.setBills(billsClient.getByUserId(user.getId()))
        );
        return users;
    }

    public User saveUserProvider(UserRegistrationDto user){
        return userRepository.saveUserProvider(user);
    }
}
