package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "schema hold customer informations"
)
public class CustomerDto {

    @Schema(

            description = "name of customer",
            example = "Rakesh Rane"
    )
    @NotEmpty(message = "Name can not be empty")
    @Size(min = 5, max = 30, message = "The length of customer name should be between 5 and 30")
    private String name;

    @Schema(

            description = "email of customer",
            example = "rakesh@1992"
    )
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "email address should valid")
    private String email;

    @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digit")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
