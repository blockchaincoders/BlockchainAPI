package com.abs.service;

import com.abs.entity.CustomerEntity;

import java.util.List;

public interface CustomerServiceApi {
	void addCustomer(CustomerEntity Customer);
    List<CustomerEntity> getAllCustomers();
    void deleteCustomer(Integer CustomerId);
    List<CustomerEntity> verifyCustomer(String username, String password);
}
