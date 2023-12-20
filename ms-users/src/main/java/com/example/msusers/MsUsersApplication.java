package com.example.msusers;

import com.example.msusers.domain.Bill;
import com.example.msusers.domain.User;
import com.example.msusers.domain.dto.UserRegistrationDto;
import com.example.msusers.feignClients.BillsClient;
import com.example.msusers.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsersApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDemoUsers(UserService userService, BillsClient billsClient){
        return args -> {
            Bill demobill = new Bill(null, null, "Demoproduct", 45.0);
            try {
                UserRegistrationDto user1dto = new UserRegistrationDto("IvanTarjan", "ivantarjan@gmail.com", "Ivan", "asdf");
                User user1 = userService.saveUserProvider(user1dto);
                billsClient.newBill(demobill.cloneNoCustomer(user1.getId()));
                billsClient.newBill(demobill.cloneNoCustomer(user1.getId()));
                billsClient.newBill(demobill.cloneNoCustomer(user1.getId()));

                UserRegistrationDto user2dto = new UserRegistrationDto("user", "user@gmail.com", "user", "user");
                User user2 = userService.saveUserProvider(user2dto);
                billsClient.newBill(demobill.cloneNoCustomer(user2.getId()));
                billsClient.newBill(demobill.cloneNoCustomer(user2.getId()));
                billsClient.newBill(demobill.cloneNoCustomer(user2.getId()));

                UserRegistrationDto user3dto = new UserRegistrationDto("demo", "demo@gmail.com", "demo", "demo");
                User user3 = userService.saveUserProvider(user3dto);
                billsClient.newBill(demobill.cloneNoCustomer(user3.getId()));
                billsClient.newBill(demobill.cloneNoCustomer(user3.getId()));
                billsClient.newBill(demobill.cloneNoCustomer(user3.getId()));
            } catch (Exception e){
                System.out.println("The users already exist");
            }



        };
    }

}
