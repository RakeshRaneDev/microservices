package com.artrist.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {
    @NotEmpty(message = "Account number can not be null")
    @Pattern(regexp = "($|[0-9]{10})", message = "account number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "accountType number can not be null")
    private String accountType;
    @NotEmpty(message = "branchAddress number can not be null")
    private String branchAddress;
}
