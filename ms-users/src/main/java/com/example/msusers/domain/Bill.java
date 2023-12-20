package com.example.msusers.domain;

import lombok.*;

@AllArgsConstructor
@Data
public class Bill {
    private String idBill;
    private String customerBill;
    private String productBill;
    private Double totalPrice;

    public Bill cloneNoCustomer(String customerBill){
        return new Bill(null, customerBill, this.productBill, this.totalPrice);
    }
}
