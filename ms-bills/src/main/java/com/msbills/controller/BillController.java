package com.msbills.controller;

import com.msbills.models.Bill;
import com.msbills.service.BillService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Bill>> getAll() {
        return ResponseEntity.ok().body(service.getAllBill());
    }

//    @GetMapping("/all2")
//    @PreAuthorize("hasAnyAuthority('GROUP_PROVIDERS')")
//    public ResponseEntity<List<Bill>> getAll2() {
//        return ResponseEntity.ok().body(service.getAllBill());
//    }


    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('GROUP_PROVIDERS')")
    public ResponseEntity<Bill> postBill(@RequestBody Bill bill){
        return ResponseEntity.ok(service.postBill(bill));
    }

    @PostMapping("/nogroup/new")
    public ResponseEntity<Bill> postBillNoGroup(@RequestBody Bill bill){
        return ResponseEntity.ok(service.postBill(bill));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Bill>> getBillsByCustomerId(@PathVariable String id){
        return ResponseEntity.ok(service.getBillsByCustomerId(id));
    }


}
