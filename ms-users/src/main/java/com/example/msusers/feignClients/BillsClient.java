package com.example.msusers.feignClients;

import com.example.msusers.domain.Bill;
import com.example.msusers.feignClients.config.OAuthFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-bill", configuration = OAuthFeignConfig.class)
public interface BillsClient {

    @GetMapping("/bills/{id}")
    List<Bill> getByUserId(@PathVariable String id);

    @PostMapping("/bills/nogroup/new")
    Bill newBill(@RequestBody Bill bill);

}
