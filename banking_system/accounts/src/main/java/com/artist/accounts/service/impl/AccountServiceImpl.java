package com.artist.accounts.service.impl;

import com.artist.accounts.constants.AccountsConstants;
import com.artist.accounts.entity.Customer;
import com.artist.accounts.exception.CustomerAlreadyExistsExceptions;
import com.artist.accounts.exception.ResourceNotFoundException;
import com.artist.accounts.mapper.AccountMapper;
import com.artist.accounts.mapper.CustomerMapper;
import com.artist.accounts.dto.AccountsDto;
import com.artist.accounts.dto.CustomerDto;
import com.artist.accounts.entity.Accounts;
import com.artist.accounts.repository.AccountRepository;
import com.artist.accounts.repository.CustomerRepository;
import com.artist.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsExceptions(" Customer already present with given mobile number"
                    + customerDto.getMobileNumber());
        }
        Customer saveCustomer = customerRepository.save(customer);
        Accounts accounts = createAccount(saveCustomer);
        accountRepository.save(accounts);
    }

    /**
     *
     */
    private Accounts createAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 10000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "CustomerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean updated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber:", accountsDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            updated = true;
        }
        return updated;

    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
