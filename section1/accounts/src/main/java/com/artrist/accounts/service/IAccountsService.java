package com.artrist.accounts.service;

import com.artrist.accounts.dto.CustomerDto;

/**
 * This class is demostarte
 * @author Rakesh Rane
 * @version 0.1
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/index.html" target = "_blank">java Docs</a>
 */
public interface IAccountsService {

    /**
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     */
    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber - customer mobile number
     * @return boolean a corrounding record delete or not
     */
    boolean deleteAccount(String mobileNumber);
}
