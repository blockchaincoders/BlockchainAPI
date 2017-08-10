package com.abs.dao;

import java.util.List;
import java.util.Optional;

import com.abs.entity.CustomerEntity;

public interface CustomerDaoApi
{
    void addCustomer(CustomerEntity customer);
    List<CustomerEntity> getAllCustomers();
    void deleteCustomer(Integer customerId);
    List<CustomerEntity> fetchCustomerByUsernamePassword(String username , String password);
    CustomerEntity fetchCustomerById(Integer customerId);
}