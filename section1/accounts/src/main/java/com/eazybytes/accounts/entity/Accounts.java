package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Accounts  extends BaseEntity{

    private Long customerId;

    @Id
    private Long accountNumber;

    private String accountType;
    private String email;
    @Column(name = "mobile_number")
    private String branchAddress;
}
