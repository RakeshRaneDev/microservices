package com.artist.accounts.controller;

import com.artist.accounts.constants.AccountsConstants;
import com.artist.accounts.dto.AccountsContactInfoDto;
import com.artist.accounts.dto.CustomerDto;
import com.artist.accounts.dto.ResponseDto;
import com.artist.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST Api for Artist " +
                "Accounts",
        description = "CRUD REST api related to accounts"
)
public class AccountController {

    @Autowired
    private  IAccountsService accountsService;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Operation(
            summary = "Create account REST Api",
            description = "Account Creation"
    )
    @ApiResponse(
            responseCode = "201",
            description = " Account has been created"
    )
    @PostMapping(value = "create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                           @RequestParam String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                            @RequestParam String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.MESSAGE_500, AccountsConstants.MESSAGE_500));
        }
    }


    @Operation(
            summary = "Create account REST Api",
            description = "get build information"
    )
    @ApiResponse(
            responseCode = "200",
            description = " Http status ok"
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuild(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "get java information"
    )
    @ApiResponse(
            responseCode = "200",
            description = " Http status ok"
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){

        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("java.home"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Get Contact info"
    )
    @ApiResponse(
            responseCode = "200",
            description = " Http status ok"
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }



}
