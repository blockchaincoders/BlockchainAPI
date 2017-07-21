package com.abs.service;

import java.util.List;

import com.abs.entity.CustomerEntity;

public interface ManagerApi {
	public void addCustomer(CustomerEntity Customer);
    public List<CustomerEntity> getAllCustomers();
    public void deleteCustomer(Integer CustomerId);
}
