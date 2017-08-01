package com.abs.service;

import com.abs.entity.CustomerEntity;

import java.util.List;

public interface ManagerApi {
	public void addCustomer(CustomerEntity Customer);
    public List<CustomerEntity> getAllCustomers();
    public void deleteCustomer(Integer CustomerId);
    List<CustomerEntity> verifyCustomer(String username, String password);
}
